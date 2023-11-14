package com.mkiperszmid.emptyapp.navigation

sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens("login")
    object HomeScreen: AppScreens("home")
    object SaludoScreen: AppScreens("saludo")
}
