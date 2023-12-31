package es.jose.emptyapp.login

import android.app.Activity
import android.content.Context
import com.microsoft.identity.client.AcquireTokenSilentParameters
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAccount
import com.microsoft.identity.client.ISingleAccountPublicClientApplication
import com.microsoft.identity.client.ISingleAccountPublicClientApplication.SignOutCallback
import com.microsoft.identity.client.PublicClientApplication
import com.microsoft.identity.client.SignInParameters
import com.microsoft.identity.client.SilentAuthenticationCallback
import es.jose.emptyapp.R

class MSALAuth {
    companion object {
        private lateinit var singleAccountApp: ISingleAccountPublicClientApplication
        var account: IAccount? = null

        fun checkLogin(
            activity: Activity,
            callback: AuthenticationCallback,
            actuallyLogin: Boolean,
            tokenScope: String
        ): Boolean {
            println("usando scope: " + tokenScope)
            singleAccountApp = PublicClientApplication.createSingleAccountPublicClientApplication(
                activity,
                R.raw.auth_config_single_account
            )
            val acctResult = singleAccountApp.currentAccount
            account = acctResult?.currentAccount
            return if (account == null) {
                if (actuallyLogin) {
                    val params = SignInParameters.builder().withActivity(activity).withScope(
                        tokenScope
                    ).withCallback(callback).build()
                    singleAccountApp.signIn(params)
                }
                false
            } else {
                true
            }
        }

        //callback: SilentAuthenticationCallback
        fun getToken(context: Context, tokenScope: String): String {
            try {
                println("Getting Token from MSAL")
                println("Usando scope: " + tokenScope)
                if (account == null) {
                    singleAccountApp =
                        PublicClientApplication.createSingleAccountPublicClientApplication(
                            context,
                            R.raw.auth_config_single_account
                        )
                    val acctResult = singleAccountApp.currentAccount
                    account = acctResult?.currentAccount
                }
                println("About to get token silently.")
                val authResult = singleAccountApp.acquireTokenSilent(
                    getTokenParams(tokenScope)
                )!!
                println("MSAL Token retrieved.")
                println("Token: " + authResult.accessToken)
                return authResult.accessToken
            } catch (exception: Exception) {
                println(exception.localizedMessage)
                exception.printStackTrace()
                return ""
            }
        }

        private fun getTokenParams(
            scope: String,
            callback: SilentAuthenticationCallback? = null
        ): AcquireTokenSilentParameters {
            val tokenParamBuilder = AcquireTokenSilentParameters.Builder()
            tokenParamBuilder.fromAuthority(account!!.authority)
            tokenParamBuilder.withScopes(listOf(scope))
            tokenParamBuilder.forAccount(account)
            if (callback != null) {
                tokenParamBuilder.withCallback(callback)
            }
            return AcquireTokenSilentParameters(tokenParamBuilder)
        }

        fun logout(callback: SignOutCallback): Boolean {
            try {
                println("Haciendo logout")
                if (singleAccountApp != null) {
                    singleAccountApp.signOut(callback)
                }
                println("MSAL Logout terminado.")
                return true
            } catch (exception: Exception) {
                println(exception.localizedMessage)
                exception.printStackTrace()
                return false
            }
        }
    }
}