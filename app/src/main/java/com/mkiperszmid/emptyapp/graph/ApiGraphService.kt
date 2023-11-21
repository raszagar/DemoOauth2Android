package com.mkiperszmid.emptyapp.saludo

import com.mkiperszmid.emptyapp.graph.Datos
import com.mkiperszmid.emptyapp.login.AuthenticationInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val TOKEN_SCOPE_GRAPH = "https://graph.microsoft.com/.default"

interface ApiGraphService {
    companion object {
        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor(TOKEN_SCOPE_GRAPH)).build()

        private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val instance = Retrofit.Builder()
            .baseUrl("https://graph.microsoft.com/v1.0/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build().create(ApiGraphService::class.java)
    }

    @GET("me")
    suspend fun datos(): Datos

}