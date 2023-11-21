package com.mkiperszmid.emptyapp.login

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mkiperszmid.emptyapp.navigation.AppScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    activity: Activity?
) {

    Scaffold(
        topBar = {
            TopAppBar() {
                Text(text = "Login Screen")
            }
        }
    ) {
        BodyContent(navController, loginViewModel, activity)
    }
}

@Composable
fun BodyContent(
    navController: NavController,
    loginViewModel: LoginViewModel,
    activity: Activity?
) {
    Column(
        //modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(textAlign = TextAlign.Center,
            text = "Pulsa el botón para hacer login")
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                loginViewModel.authenticate(activity = activity)
            }
        ) {
            Text(text = "Hacer Login")
        }

        Text(textAlign = TextAlign.Center,
            text = "Pulsa el botón para hacer logout")
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                loginViewModel.logout(activity)
            }
        ) {
            Text(text = "Hacer Logout")
        }

        Text(textAlign = TextAlign.Center,
            text = "Navega donde quieras")
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navController.navigate(AppScreens.HomeScreen.route)
            }
        ) {
            Text(text = "Home screen")
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navController.navigate(AppScreens.SaludoScreen.route)
            }
        ) {
            Text(text = "Api Saludo screen")
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navController.navigate(AppScreens.GraphScreen.route)
            }
        ) {
            Text(text = "Api Graph screen")
        }

    }

}
