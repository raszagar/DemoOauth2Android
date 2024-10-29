package es.jose.emptyapp.openmaps

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.maps.model.LatLng
import org.osmdroid.util.GeoPoint

data class OpenMapsState (
    val coordenadasMarker: MutableState<GeoPoint?> = mutableStateOf(null),
    val coordenadasGPS: MutableState<GeoPoint?> = mutableStateOf(null),
)