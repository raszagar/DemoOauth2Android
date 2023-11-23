package com.mkiperszmid.emptyapp.saludo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mkiperszmid.emptyapp.loading.LoadingScreen
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
                    navController.navigate(route = AppScreens.GraphScreen.route)
                }
            ) {
                Text(text = "Ir Graph")
            }
        }

        Text(text = "Prueba de la API", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        Button(onClick = {
            viewModel.test()
        }) {
            Text(text = "Test")
        }

        Text(viewModel.salida.value)

        Button(onClick = {
            viewModel.getSaludo()
        }) {
            Text(text = "Saludo")
        }

        Text(viewModel.salida2.value)

        Button(onClick = {
            viewModel.salida.value = "...."
            viewModel.salida2.value = "...."
        }) {
            Text(text = "Limpiar salida")
        }
    }

    if (viewModel.mostarLoading.value) {
        LoadingScreen()
    }

}

@Preview
@Composable
fun SaludoScreenPreview(){
    SaludoScreen(navController = rememberNavController(), viewModel = SaludoViewModel(ApiSaludoService.instance))
}
