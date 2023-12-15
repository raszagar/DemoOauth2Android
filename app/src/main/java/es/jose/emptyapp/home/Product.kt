package es.jose.emptyapp.home

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val price: Double
)

data class ProductDto(
    val name: String,
    val price: Double
)
