package es.jose.emptyapp.maps

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng

data class MapState(
    val coordenadasMapa: MutableState<LatLng> = mutableStateOf(LatLng(0.0,0.0)),
    val coordenadasMarker: MutableState<LatLng> = mutableStateOf(LatLng(0.0,0.0)),
    val coordenadasMarkerGps: MutableState<LatLng> = mutableStateOf(LatLng(0.0,0.0)),
    val coordenadasGPS: MutableState<LatLng?> = mutableStateOf(null)
)
