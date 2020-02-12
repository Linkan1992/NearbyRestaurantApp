package linkan.a740362.nearbyrestaurantapp.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService

object PermissionUtil {

    const val LOCATION_PERMISSION_ID = 100

    fun checkLocationPermissions(activityCompat: AppCompatActivity): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activityCompat,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ActivityCompat.checkSelfPermission(
                        activityCompat,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                )
                    return true
            } else
                return true
        }
        return false
    }


    fun requestPermissions(activityCompat: AppCompatActivity) {
        ActivityCompat.requestPermissions(
            activityCompat,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            LOCATION_PERMISSION_ID
        )
    }


    fun isLocationEnabled(activityCompat: AppCompatActivity): Boolean {
        val locationManager: LocationManager =
            activityCompat.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

}