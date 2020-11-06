package dev.engel.gitty.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import dev.engel.gitty.core.AppConstants.GITHUB_CLIENT_ID
import dev.engel.gitty.core.AppConstants.GITHUB_CLIENT_SECRET
import dev.engel.gitty.di.AutoInject
import dev.engel.gitty.repository.Auth
import dev.engel.gitty.repository.AuthCreateQuery
import dev.engel.gitty.repository.AuthRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import net.openid.appauth.*
import javax.inject.Inject

class AuthenticationActivity : AppCompatActivity(), AutoInject {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var serviceConfiguration: AuthorizationServiceConfiguration

    @Inject
    lateinit var authService: AuthorizationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainScope().launch {
            when (authRepository.retrieve()) {
                is Auth.LoggedOut -> doAuth()
                is Auth.LoggedIn -> startActivity(MainActivity.createIntent(this@AuthenticationActivity))
            }
        }
    }

    private fun doAuth() {
        val authRequest = AuthorizationRequest.Builder(
            serviceConfiguration,
            GITHUB_CLIENT_ID,
            ResponseTypeValues.CODE,
            "dev.engel.gitty://githubauth".toUri()
        ).setScopes(
            "repo",
            "repo_deployment",
            "security_events",
            "admin:repo_hook",
            "admin:org",
            "admin:public_key",
            "admin:org_hook",
            "gist",
            "notifications",
            "user"
        ).build()

        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        startActivityForResult(authIntent, REQUEST_CODE_OAUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_OAUTH && data != null) {
            val resp = AuthorizationResponse.fromIntent(data)
            val ex = AuthorizationException.fromIntent(data)
            if (resp != null) {
                val clientAuth = ClientSecretBasic(GITHUB_CLIENT_SECRET)
                authService.performTokenRequest(
                    resp.createTokenExchangeRequest(),
                    clientAuth
                ) { response, exception ->
                    MainScope().launch {
                        val auth = authRepository.create(AuthCreateQuery(response, exception))
                        if (auth is Auth.LoggedIn) {
                            startActivity(MainActivity.createIntent(this@AuthenticationActivity))
                        }
                    }
                }
            } else if (ex != null) {
                // authorization failed
                ex.code
                ex.error
                ex.errorDescription
                ex.errorUri
            }
        } else {
            Log.d("Cody", "uh oh")
        }
    }

    companion object {
        private const val REQUEST_CODE_OAUTH = 617
    }
}
