package com.mkiperszmid.emptyapp.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mkiperszmid.emptyapp.navigation.AppScreens
import retrofit2.http.Body

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Home Screen")
            }
        }
    ) {
        BodyContent(navController, viewModel)
    }
}

@Composable
fun BodyContent(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val state = viewModel.state

    Column(
        //modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row (
            modifier = Modifier.align(Alignment.End)
        ){
            Button(
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = {
                    navController.navigate(route = AppScreens.SaludoScreen.route)
                }
            ) {
                Text(text = "Ir Saludo")
            }
            Button(
                onClick = {
                    navController.navigate(route = AppScreens.LoginScreen.route)
                }
            ) {
                Text(text = "Ir Login")
            }
        }

        Text(text = "Mis Productos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        TextField(
            value = state.productName,
            onValueChange = { viewModel.changeName(it) },
            placeholder = { Text(text = "Nombre del producto") }
        )
        TextField(
            value = state.productPrice,
            onValueChange = { viewModel.changePrice(it) },
            placeholder = { Text(text = "Precio") }
        )
        Button(onClick = { viewModel.createProduct() }) {
            Text(text = "Agregar Producto")
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.products) {
                ProductItem(product = it, modifier = Modifier.fillMaxWidth(), onEdit = {
                    viewModel.editProduct(it)
                }, onDelete = {
                    viewModel.deleteProduct(it)
                })
            }
        }
    }

}
