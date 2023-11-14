package com.mkiperszmid.emptyapp.saludo

import com.mkiperszmid.emptyapp.login.AuthenticationInterceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface ApiSaludoService {
    companion object {
        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor()).build()

        val instance = Retrofit.Builder()
            .baseUrl("http://10.22.179.66:8080/demo-oauth2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(ApiSaludoService::class.java)
    }

    @GET("ws/test")
    suspend fun test(): String

    @GET("ws/saludo")
    suspend fun getSaludo(): String

}