package dev.engel.gitty.repository

import android.util.Log
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dev.engel.gitty.core.Skribe
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationService
import okhttp3.*

@Module
class NetworkModule {

    @Provides
    fun providesApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    fun providesOkHttpClient(authenticator: Authenticator): OkHttpClient {
        return OkHttpClient().newBuilder()
            .authenticator(authenticator)
            .build()
    }

    @Provides
    fun providesAuthenticator(authState: AuthState, authService: AuthorizationService, skribe: Skribe): Authenticator {
        skribe tag "OkHttpAuthenticator"
        return object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                val channel = Channel<Request?>()
                authState.performActionWithFreshTokens(authService) { accessToken, _, ex ->
                    if (ex != null) {
                        skribe error "Failed to authorize ${ex.error}"
                    }

                    if (response.request.header("Authorization") != null) {
                        skribe error "Authorization header is not null, we're not authenticated."
                        channel.sendBlocking(null) // not authenticated
                    }

                    val authResponse = response.request.newBuilder()
                        .header("Authorization", "Bearer $accessToken")
                        .build()

                    MainScope().launch { channel.send(authResponse) }
                }

                return runBlocking { channel.receive() }
            }
        }
    }
}
