package es.jose.emptyapp.openmaps

import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jose.emptyapp.EmptyApp
import es.jose.emptyapp.maps.LocationService
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class OpenMapsViewModel (): ViewModel() {
    var state by mutableStateOf(OpenMapsState())
        private set

    //Coordenadas puerta del sol
    val coordenadasPrueba = GeoPoint(40.416930778899626, -3.7036545163383945)
    //Coordenadas cerca de puerta del sol
    val coordenadasMarker = GeoPoint(40.41736790196411, -3.7042956464955923)

    private val locationService: LocationService = LocationService()

    fun getCoordenadasGPS(marcadorGpsState: MutableState<GeoPoint?>) {
        viewModelScope.launch {
            try {
                println("obteniendo informacion gps")
                val context = EmptyApp.instance!!

                val result: Location? = locationService.getUserLocation(context, true)
                if(result != null) {
                    Log.d("INFO", "Coordenadas gps -> Latitud ${result.latitude} y longitud ${result.longitude}")
                    state.coordenadasGPS.value = GeoPoint(result.latitude, result.longitude)
                    marcadorGpsState.value = GeoPoint(result.latitude, result.longitude)
                }
            } catch (e: Exception) {
                println("Error: " + e.message)
            }
        }
    }

    fun getCoordenadasMarcador(latLng: GeoPoint?) {
        println(latLng)
        state.coordenadasMarker.value = latLng
    }

    fun reiniciarMarcador(mapView: MapView, marker: Marker) {
        mapView.controller.setCenter(
            GeoPoint(
                coordenadasMarker.latitude,
                coordenadasMarker.longitude
            )
        )

        marker.position = GeoPoint(
            coordenadasMarker.latitude,
            coordenadasMarker.longitude
        )
    }

    fun onMoveMarker(newGeoPoint: GeoPoint) {
        state.coordenadasGPS.value = newGeoPoint
        Log.d("geo ", state.coordenadasGPS.value.toString())
    }

    fun recargarMapaGPS(mapView: MapView, marker: Marker) {
        mapView.controller.setCenter(
            GeoPoint(
                state.coordenadasGPS.value?.latitude ?: 0.0,
                state.coordenadasGPS.value?.longitude ?: 0.0
            )
        )

        marker.position = GeoPoint(
            state.coordenadasGPS.value?.latitude ?: 0.0,
            state.coordenadasGPS.value?.longitude ?: 0.0
        )
    }

}