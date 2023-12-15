package es.jose.emptyapp.graph

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.jose.emptyapp.loading.LoadingScreen
import es.jose.emptyapp.navigation.AppScreens
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            //.weight(1f, false)
        ,
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
            Text(text = "Datos")
        }

        Text(viewModel.salida.value)

        Button(onClick = {
            viewModel.datosFoto()
        }) {
            Text(text = "Datos Foto")
        }

        Text(viewModel.salida2.value)

        Button(onClick = {
            viewModel.verFoto()
        }) {
            Text(text = "Ver Foto")
        }

        Text(viewModel.salida3.value)

        if (viewModel.foto.value != null) {
            Image(
                bitmap = viewModel.foto.value!!, contentDescription = "Foto perfil",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            )
        }

        Button(onClick = {
            viewModel.salida.value = "...."
            viewModel.salida2.value = "...."
            viewModel.salida3.value = ""
            viewModel.foto.value = null
        }) {
            Text(text = "Limpiar salida")
        }

    }

    if(viewModel.mostarLoading.value) {
        LoadingScreen()
    }

}

@Preview()
@Composable
fun GraphScreenPreview() {
    GraphScreen(rememberNavController(), GraphViewModel(ApiGraphService.instance))
}

@OptIn(ExperimentalEncodingApi::class)
fun String.toBitmap(): Bitmap {
    val imageBytes = Base64.decode(this, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}


