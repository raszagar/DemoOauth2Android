package com.mkiperszmid.emptyapp.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mkiperszmid.emptyapp.home.HomeScreen
import com.mkiperszmid.emptyapp.home.HomeViewModel
import com.mkiperszmid.emptyapp.home.ProductService
import com.mkiperszmid.emptyapp.login.LoginScreen
import com.mkiperszmid.emptyapp.login.LoginViewModel
import com.mkiperszmid.emptyapp.saludo.ApiSaludoService
import com.mkiperszmid.emptyapp.saludo.SaludoScreen
import com.mkiperszmid.emptyapp.saludo.SaludoViewModel

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
    }
}