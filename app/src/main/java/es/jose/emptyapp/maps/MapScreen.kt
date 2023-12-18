package es.jose.emptyapp.maps

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import es.jose.emptyapp.loading.LoadingScreen
import es.jose.emptyapp.navigation.AppScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar() {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Google Map Screen")
            }
        }
    ) {
       BodyContent(navController, viewModel)
    }
}

@Composable
fun BodyContent(navController: NavController, viewModel: MapViewModel) {
    val state = viewModel.state

    var columnScrollingEnabled: Boolean by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = columnScrollingEnabled
    ) {
        item {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
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

            Text(text = "Prueba de API de Google Maps", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

            Text(text = "Puedes mover un marcador dejando el dedo sobre él", fontSize = 12.sp)
        }

        item {
            val context = LocalContext.current

            var cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(state.coordenadasMapa.value, 18f)
            }
            val coordenadasMarker = rememberMarkerState(position = viewModel.coordenadasMarker)

            GoogleMap(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .height(300.dp)
                    .motionEventSpy {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                columnScrollingEnabled = false
                            }

                            MotionEvent.ACTION_UP -> {
                                columnScrollingEnabled = true
                            }
                        }
                    },
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = true,
                    compassEnabled = true,
                    zoomControlsEnabled = true
                )
            ) {
                //Marcador estatico
                Marker(
                    state = MarkerState(position = viewModel.coordenadasPrueba),
                    title = "Puerta del sol",
                    snippet = "Posición del sol",
                    draggable = true
                )

                //Marcador que se puede mover
                Marker(
                    state = coordenadasMarker,
                    title = "Marcador",
                    snippet = "Marcador para mover",
                    draggable = true
                )
            }

            Text(text = "Las coordenadas del Marker")
            Text(text = "Coordenadas: " + state.coordenadasMarker.value)

            Button(
                onClick = {
                    viewModel.getCoordenadasMarcador(coordenadasMarker.position)
                }
            ) {
                Text(text = "Ver coordenadas marcador")
            }

            Text(text = "Las coordenadas del GPS" )
            Text(text = "Coordenadas: " + state.coordenadasGPS.value)

            //Peticion permisos para GPS
            val locationPermissionRequest = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        // Precise location access granted.
                        Log.d("INFO", "Hay permisos de ubicacion fina")
                        println("Hay permisos de ubicacion fina")
                        viewModel.getCoordenadasGPS()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                        Log.d("INFO", "Hay permisos de ubicacion aproximada")
                        println("Hay permisos de ubicacion aproximada")
                        viewModel.getCoordenadasGPS()
                    }
                    else -> {
                        // No location access granted.
                        Log.d("INFO", "No hay permisos de ubicacion")
                        Toast.makeText(context, "Debe conceder permiso de Ubiación", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            Button(
                onClick = {
                    locationPermissionRequest.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION))
                }
            ) {
                Text(text = "Ver coordenadas GPS")
            }

            Button(
                onClick = {
                    viewModel.recargarMapaGPS(cameraPositionState)
                }
            ) {
                Text(text = "Centrar mapa con GPS")
            }

            Text(text = "Texto para tener más scroll\nTexto para tener más scroll\nTexto para tener más scroll\n")
            Text(text = "Texto para tener más scroll\nTexto para tener más scroll\nTexto para tener más scroll\n")
            Text(text = "Texto para tener más scroll\nTexto para tener más scroll\nTexto para tener más scroll\n")
            Text(text = "Texto para tener más scroll\nTexto para tener más scroll\nTexto para tener más scroll\n")

        }
    }

}

@Preview
@Composable
fun MapScreenPreview(){
    MapScreen(navController = rememberNavController(), viewModel = MapViewModel())
}
