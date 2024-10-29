package es.jose.emptyapp.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.jose.emptyapp.home.HomeScreen
import es.jose.emptyapp.home.HomeViewModel
import es.jose.emptyapp.home.ProductService
import es.jose.emptyapp.login.LoginScreen
import es.jose.emptyapp.login.LoginViewModel
import es.jose.emptyapp.graph.ApiGraphService
import es.jose.emptyapp.saludo.ApiSaludoService
import es.jose.emptyapp.graph.GraphScreen
import es.jose.emptyapp.graph.GraphViewModel
import es.jose.emptyapp.maps.MapScreen
import es.jose.emptyapp.maps.MapViewModel
import es.jose.emptyapp.openmaps.OpenMapsScreen
import es.jose.emptyapp.openmaps.OpenMapsViewModel
import es.jose.emptyapp.saludo.SaludoScreen
import es.jose.emptyapp.saludo.SaludoViewModel

@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel,
    activity: Activity?
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController, loginViewModel, activity)
        }
        composable(route = AppScreens.HomeScreen.route) {
            if(loginViewModel.authenticated == null){
                LoginScreen(navController, loginViewModel, activity)
            } else{
                HomeScreen(navController, HomeViewModel(ProductService.instance))
            }
        }
        composable(route = AppScreens.SaludoScreen.route) {
            if(loginViewModel.authenticated == null){
                LoginScreen(navController, loginViewModel, activity)
            } else {
                SaludoScreen(navController, SaludoViewModel(ApiSaludoService.instance))
            }
        }
        composable(route = AppScreens.GraphScreen.route) {
            if(loginViewModel.authenticated == null){
                LoginScreen(navController, loginViewModel, activity)
            } else {
                GraphScreen(navController, GraphViewModel(ApiGraphService.instance))
            }
        }
        composable(route = AppScreens.MapScreen.route) {
            if(loginViewModel.authenticated == null){
                LoginScreen(navController, loginViewModel, activity)
            } else {
                MapScreen(navController, MapViewModel())
            }
        }
        composable(route = AppScreens.OpenMapsScreen.route) {
            if(loginViewModel.authenticated == null){
                LoginScreen(navController, loginViewModel, activity)
            } else {
                OpenMapsScreen(navController, OpenMapsViewModel())
            }
        }
    }
}