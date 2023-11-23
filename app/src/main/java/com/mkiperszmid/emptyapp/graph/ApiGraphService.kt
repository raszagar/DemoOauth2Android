package com.mkiperszmid.emptyapp.graph

import com.mkiperszmid.emptyapp.constantes.Constantes
import com.mkiperszmid.emptyapp.login.AuthenticationInterceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiGraphService {
    companion object {
        var client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(Constantes.TOKEN_SCOPE_GRAPH))
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()

        val instance = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://graph.microsoft.com/v1.0/")
            .client(client)
            .build().create(ApiGraphService::class.java)
    }

    @GET("me")
    fun datos(): Call<Datos>

    @GET("me/photo")
    fun datosFoto(): Call<ResponseBody>

    @GET("me/photo/\$value")
    fun verFoto(): Call<ResponseBody>

}