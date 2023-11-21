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
fun GraphScreen(
    navController: NavController,
    viewModel: GraphViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar() {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Graph Screen")
            }
        }
    ) {
        BodyContentGraph(navController, viewModel)
    }
}

@Composable
fun BodyContentGraph(navController: NavController, viewModel: GraphViewModel) {
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
                onClick = {
                    navController.navigate(route = AppScreens.LoginScreen.route)
                }
            ) {
                Text(text = "Ir Login")
            }
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
                    navController.navigate(route = AppScreens.SaludoScreen.route)
                }
            ) {
                Text(text = "Ir Saludo")
            }
        }

        Text(text = "Prueba de la API de Graph", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        Button(onClick = {
            viewModel.datos()
        }) {
            Text(text = "Test")
        }

        Text(viewModel.salida.value)

        Button(onClick = {
            viewModel.salida.value = "...."
        }) {
            Text(text = "Limpiar salida")
        }
    }

}
