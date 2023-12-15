package es.jose.emptyapp.home

data class HomeState(
    val products: List<Product> = emptyList(),
    val productName: String = "",
    val productPrice: String = "",
    val productId: String? = null
)
