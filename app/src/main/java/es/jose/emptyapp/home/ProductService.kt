package es.jose.emptyapp.home

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    companion object {
        //Coger el id de la api en la pagina https://crudcrud.com/
        val instance = Retrofit.Builder().baseUrl("https://crudcrud.com/api/5ec7c8dbaa1f45cdaac3424911c56be6/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build().create(ProductService::class.java)
    }

    @GET("products")
    suspend fun getProducts(): List<Product>

    @POST("products")
    suspend fun insertProduct(@Body product: ProductDto): Product

    @PUT("products/{id}")
    suspend fun updateProduct(@Body product: ProductDto, @Path("id") id: String)

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: String)

}