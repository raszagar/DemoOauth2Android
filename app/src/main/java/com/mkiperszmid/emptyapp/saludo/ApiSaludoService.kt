package com.mkiperszmid.emptyapp.saludo

import com.mkiperszmid.emptyapp.login.AuthenticationInterceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val TOKEN_SCOPE_API = "api://f92863b8-16ec-45b6-851f-**********/ApiPrueba"

interface ApiSaludoService {
    companion object {
        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor(TOKEN_SCOPE_API)).build()

        val instance = Retrofit.Builder()
            .baseUrl("http://10.22.179.117:8080/demo-oauth2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(ApiSaludoService::class.java)
    }

    @GET("ws/test")
    suspend fun test(): String

    @GET("ws/saludo")
    suspend fun getSaludo(): String

}