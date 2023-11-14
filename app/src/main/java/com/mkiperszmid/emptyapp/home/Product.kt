package com.mkiperszmid.emptyapp.home

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "_id")
    val id: String,
    val name: String,
    val price: Double
)

data class ProductDto(
    val name: String,
    val price: Double
)
