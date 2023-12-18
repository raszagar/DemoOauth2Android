package es.jose.emptyapp.maps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationService {

    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(context: Context, tienePermisosLocalizacion: Boolean): Location? {
        val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationManager:LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled:Boolean =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if(!isGpsEnabled || !tienePermisosLocalizacion) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if(isComplete) {
                    if(isSuccessful) {
                        cont.resume(result) {

                        }
                    } else {
                        cont.resume(null) {}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it){}
                }
                addOnFailureListener {
                    cont.resume(null) {}
                }
                addOnCanceledListener {
                    cont.resume(null) {}
                }
            }
        }
    }
}