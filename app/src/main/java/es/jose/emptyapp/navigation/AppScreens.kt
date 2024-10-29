package es.jose.emptyapp.navigation

sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens("login")
    object HomeScreen: AppScreens("home")
    object SaludoScreen: AppScreens("saludo")
    object GraphScreen: AppScreens("graph")
    object MapScreen: AppScreens("maps")
    object OpenMapsScreen: AppScreens("openmaps")
}
