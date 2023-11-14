package com.mkiperszmid.emptyapp.login

import com.mkiperszmid.emptyapp.EmptyApp
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class AuthenticationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        println("Estoy aki en el interceptor")
        val requestBuilder = chain.request().newBuilder()
        val context = EmptyApp.instance!!
        println("We have an OkHttp request:  ${chain.request().method} : ${chain.request().url})")
        requestBuilder.addHeader(
            "authorization", "Bearer ${MSALAuth.getToken(context)}"
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