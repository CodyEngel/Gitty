package dev.engel.gitty.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import dagger.Module
import dagger.Provides
import dev.engel.gitty.di.CoroutineIODispatcher
import dev.engel.gitty.repository.AuthModule.Companion.PREF_AUTH_STATE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.openid.appauth.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authState: AuthState,
    private val sharedPreferences: SharedPreferences,
    @CoroutineIODispatcher private val ioDispatcher: CoroutineDispatcher
) : CreateRepository<Auth, AuthCreateQuery>, RetrieveRepository<Auth, AuthRetrieveQuery> {

    @SuppressLint("ApplySharedPref")
    override suspend fun create(query: AuthCreateQuery): Auth {
        return withContext(ioDispatcher) {
            authState.update(query.response, query.exception)
            sharedPreferences.edit()
                .putString(PREF_AUTH_STATE, authState.jsonSerializeString())
                .commit()
            retrieve()
        }
    }

    fun blockingRetrieve(): Auth {
        return runBlocking { retrieve() }
    }

    suspend fun retrieve(): Auth = retrieve(AuthRetrieveQuery())

    override suspend fun retrieve(query: AuthRetrieveQuery): Auth {
        return withContext(ioDispatcher) {
            authState.accessToken?.let { Auth.LoggedIn(accessToken = it) } ?: Auth.LoggedOut
        }
    }
}

class AuthRetrieveQuery : RetrieveQuery

data class AuthCreateQuery(
    val response: TokenResponse?,
    val exception: AuthorizationException?
) : CreateQuery

sealed class Auth {
    object LoggedOut : Auth()
    data class LoggedIn(val accessToken: String) : Auth()
}

@Module
class AuthModule {
    @Provides
    fun providesAuthState(
        authServiceConfig: AuthorizationServiceConfiguration,
        sharedPreferences: SharedPreferences
    ): AuthState {
        val authStateJson = sharedPreferences.getString(PREF_AUTH_STATE, null)
        return authStateJson?.let { AuthState.jsonDeserialize(it) } ?: AuthState(authServiceConfig)
    }

    @Provides
    fun providesAuthService(context: Context): AuthorizationService {
        return AuthorizationService(context)
    }

    @Provides
    fun providesAuthServiceConfig(): AuthorizationServiceConfiguration {
        return AuthorizationServiceConfiguration(
            Uri.parse("https://github.com/login/oauth/authorize"),  // authorization endpoint
            Uri.parse("https://github.com/login/oauth/access_token") // token endpoint
        )
    }

    companion object {
        const val PREF_AUTH_STATE = "authStateJson"
    }
}
