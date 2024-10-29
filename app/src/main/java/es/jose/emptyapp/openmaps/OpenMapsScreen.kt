package es.jose.emptyapp.openmaps

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.jose.emptyapp.navigation.AppScreens
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OpenMapsScreen(
    navController: NavController,
    viewModel: OpenMapsViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar() {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "ArrowBack",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Open Street Map Screen")
            }
        }
    ) {
        BodyContentOpenMaps(navController, viewModel)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun BodyContentOpenMaps(navController: NavController, viewModel: OpenMapsViewModel) {
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

            Text(text = "Prueba de API de Open Street Maps", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

            Text(text = "Puedes mover un marcador dejando el dedo sobre él", fontSize = 12.sp)
        }

        item {
            val context = LocalContext.current

            val coordenadasMarkerGps:MutableState<GeoPoint?> = mutableStateOf(null)

            var mapView: MapView by remember { mutableStateOf( MapView(context)) }
            var marker: Marker by remember { mutableStateOf(Marker(mapView)) }

            Box(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .height(300.dp)
                    .fillMaxWidth(),
            ) {
                AndroidView(
                    modifier = Modifier
                        .matchParentSize()
                        .fillMaxWidth()
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
                    factory = { context ->
                        //mapViewOpen = MapView(context)

                        mapView.apply {
                            clipToOutline = true
                        }

                        //Zoom en el marcador
                        mapView.controller.setZoom(15.0)
                        mapView.controller.setCenter(
                            GeoPoint(
                                viewModel.coordenadasMarker.latitude,
                                viewModel.coordenadasMarker.longitude
                            )
                        )

                        // Añadir marcador
                        marker = Marker(mapView)
                        marker!!.position = GeoPoint(
                            viewModel.coordenadasMarker.latitude,
                            viewModel.coordenadasMarker.longitude
                        )
                        marker!!.title = "Mi ubicación"
                        marker!!.isDraggable = true

                        marker!!.setOnMarkerDragListener(object : Marker.OnMarkerDragListener {
                            override fun onMarkerDrag(marker: Marker) {

                            }

                            override fun onMarkerDragEnd(marker: Marker) {
                                val newGeoPoint = marker.position
                                viewModel.onMoveMarker(newGeoPoint)
                            }

                            override fun onMarkerDragStart(marker: Marker) {
                            }
                        })
                        mapView.overlays.add(marker)

                        mapView.setTileSource(TileSourceFactory.MAPNIK)
                        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
                        mapView.setMultiTouchControls(true)
                        mapView
                    }
                )
            }


            Text(text = "Las coordenadas del Marker")
            Text(text = "Coordenadas: " + state.coordenadasMarker.value)

            Button(
                onClick = {
                    viewModel.getCoordenadasMarcador(marker?.position)
                }
            ) {
                Text(text = "Ver coordenadas marcador")
            }

            Button(
                onClick = {
                    viewModel.reiniciarMarcador(mapView, marker)
                }
            ) {
                Text(text = "Reiniciar marcador")
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
                        viewModel.getCoordenadasGPS(coordenadasMarkerGps)
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                        Log.d("INFO", "Hay permisos de ubicacion aproximada")
                        println("Hay permisos de ubicacion aproximada")
                        viewModel.getCoordenadasGPS(coordenadasMarkerGps)
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
                    viewModel.recargarMapaGPS(mapView, marker)
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
fun OpenMapsScreenPreview(){
    OpenMapsScreen(navController = rememberNavController(), viewModel = OpenMapsViewModel())
}
