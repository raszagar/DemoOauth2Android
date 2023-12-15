package es.jose.emptyapp.login

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAccount
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.ISingleAccountPublicClientApplication.SignOutCallback
import com.microsoft.identity.client.exception.MsalException
import es.jose.emptyapp.constantes.Constantes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var authenticated: IAccount? = null

    fun authenticate(activity: Activity?) {
        if (activity != null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val alreadyLoggedIn =
                        MSALAuth.checkLogin(activity, getAuthInteractiveCallback(), true,
                            tokenScope = Constantes.TOKEN_SCOPE_API)
                    if (alreadyLoggedIn) {
                        println("username: ${MSALAuth.account!!.username}")
                        println("idToken: ${MSALAuth.account!!.idToken}")
                        authenticated = MSALAuth.account
                    }
                }
            }
        }
    }

    fun checkAuthentication(activity: Activity?) {
        if (activity != null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    var authBool =
                        MSALAuth.checkLogin(activity, getAuthInteractiveCallback(), false,
                            tokenScope = Constantes.TOKEN_SCOPE_API)

                    println("authBool: $authBool")
                    //println("username: ${MSALAuth.account!!.username}")
                }
            }
        }
    }

    private fun getAuthInteractiveCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                MSALAuth.account = authenticationResult.account
                println("Account Id: ${MSALAuth.account!!.id}")
                println("authority: ${MSALAuth.account!!.authority}")
                println("tenantId: ${MSALAuth.account!!.tenantId}")
                println("claims: ${MSALAuth.account!!.claims}")
                println("username: ${MSALAuth.account!!.username}")
                println("idToken: ${MSALAuth.account!!.idToken}")
                authenticated = MSALAuth.account
            }

            override fun onError(exception: MsalException) {
                println("Authentication failed: $exception")
                println(exception.localizedMessage)
                println("Type of exception is:")
            }

            override fun onCancel() {
            }
        }
    }

    fun logout(activity: Activity?) {
        if (activity != null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    MSALAuth.logout(getLogoutCallback())
                }
            }
        }
    }

    private fun getLogoutCallback(): SignOutCallback {
        return object : SignOutCallback {
            override fun onSignOut() {
                println("Hemos hecho logout")
                authenticated = null
            }

            override fun onError(e: MsalException) {
                println("Error al hacer logout: " + e.message)
                e.printStackTrace()
            }
        }
    }
}