package com.mkiperszmid.emptyapp.saludo

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mkiperszmid.emptyapp.navigation.AppScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SaludoScreen(
    navController: NavController,
    viewModel: SaludoViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar() {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Saludo Screen")
            }
        }
    ) {
       BodyContent(navController, viewModel)
    }
}

val salidaTest = mutableStateOf("....")
val salidaTestAuth = mutableStateOf("....")

@Composable
fun BodyContent(navController: NavController, viewModel: SaludoViewModel) {
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row (
            modifier = Modifier.align(Alignment.End)
        ){
            Button(
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = {
                    navController.navigate(route = AppScreens.HomeScreen.route)
                }
            ) {
                Text(text = "Ir Home")
            }
            Button(
                onClick = {
                    navController.navigate(route = AppScreens.LoginScreen.route)
                }
            ) {
                Text(text = "Ir Login")
            }
        }

        Text(text = "Prueba de la API", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        Button(onClick = {
            viewModel.test(salidaTest)
        }) {
            Text(text = "Test")
        }

        val salidaTest1 by salidaTest
        Text(salidaTest1)

        Button(onClick = {
            viewModel.getSaludo(salidaTestAuth)
        }) {
            Text(text = "Saludo")
        }

        val salidaTest2 by salidaTestAuth
        Text(salidaTest2)

        Button(onClick = {
            salidaTest.value = "...."
            salidaTestAuth.value = "...."
        }) {
            Text(text = "Limpiar salida")
        }
    }

}
