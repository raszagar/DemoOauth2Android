package es.jose.emptyapp.login

import android.util.Log
import es.jose.emptyapp.EmptyApp
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class AuthenticationInterceptor(
    private val tokenScope: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("Estoy en el interceptor")

        Log.d("AUTH","Interceptando peticion, agregando token")
        Log.d("AUTH","Url: "+chain.request().url.encodedPath)

        //Obtenemos el token
        val context = EmptyApp.instance!!
        val access_token : String = MSALAuth.getToken(context, tokenScope)

        //Obtenemos la request
        var request: Request = chain.request()
        var requestBuilder:Request.Builder = request.newBuilder()

        requestBuilder.addHeader(
            "Authorization", "Bearer $access_token"
        )

        return try {
            chain.proceed(requestBuilder.build())
        } catch (exception: Exception) {
            val responseBuilder = Response.Builder()
            responseBuilder.protocol(Protocol.HTTP_2)
                .body(exception.localizedMessage!!.toResponseBody())
            responseBuilder.message(exception.localizedMessage!!)
            responseBuilder.request((chain.request()))
            responseBuilder.code(500)
            responseBuilder.build()
        }
    }
}