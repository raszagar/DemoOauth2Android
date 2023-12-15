package es.jose.emptyapp.saludo

import es.jose.emptyapp.constantes.Constantes
import es.jose.emptyapp.login.AuthenticationInterceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiSaludoService {
    companion object {
        var client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(Constantes.TOKEN_SCOPE_API))
            .build()

        val instance = Retrofit.Builder()
            .baseUrl("http://10.22.179.106:8080/demo-oauth2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ApiSaludoService::class.java)
    }

    @GET("ws/test")
    fun test(): Call<String>

    @GET("ws/saludo")
    fun getSaludo(): Call<String>

}