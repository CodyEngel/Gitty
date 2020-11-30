package dev.engel.gitty.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration

@Module
@InstallIn(SingletonComponent::class)
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
    fun providesAuthService(@ApplicationContext context: Context): AuthorizationService {
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