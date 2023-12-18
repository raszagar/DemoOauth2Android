package es.jose.emptyapp.maps

import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import es.jose.emptyapp.EmptyApp
import kotlinx.coroutines.launch

class MapViewModel(

) : ViewModel() {
    var state by mutableStateOf(MapState())
        private set

    //Coordenadas puerta del sol
    val coordenadasPrueba = LatLng(40.416930778899626, -3.7036545163383945)
    //Coordenadas cerca de puerta del sol
    val coordenadasMarker = LatLng(40.41736790196411, -3.7042956464955923)

    private val locationService:LocationService = LocationService()

    init {
        state.coordenadasMapa.value = coordenadasPrueba
    }

    val mostarLoading:MutableState<Boolean> = mutableStateOf(false)

    fun getCoordenadasMarcador(latLng: LatLng) {
        println(latLng)
        state.coordenadasMarker.value = latLng
    }

    fun getCoordenadasGPS() {
        viewModelScope.launch {
            try {
                println("obteniendo informacion gps")
                val context = EmptyApp.instance!!

                val result:Location? = locationService.getUserLocation(context, true)
                if(result != null) {
                    Log.d("INFO", "Coordenadas gps -> Latitud ${result.latitude} y longitud ${result.longitude}")
                    state.coordenadasGPS.value = LatLng(result.latitude, result.longitude)
                }
            } catch (e: Exception) {
                println("Error: " + e.message)
            }
        }
    }

    /**
     * Mueve el mapa a la coordenada de gps, previamente obtenida en el state
     */
    fun recargarMapaGPS(cameraPositionState: CameraPositionState) {
        viewModelScope.launch {
            try {
                if(state.coordenadasGPS != null) {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(state.coordenadasGPS.value!!, 18f, 0f, 0f)
                        ),
                        durationMs = 1000
                    )
                }
            } catch (e: Exception) {
                println("Error: " + e.message)
            }
        }
    }

}