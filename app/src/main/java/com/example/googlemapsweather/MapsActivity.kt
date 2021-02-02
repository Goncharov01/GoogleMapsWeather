package com.example.googlemapsweather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationServices.FusedLocationApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        val grodno = LatLng(53.6803665, 23.8290348)
        mMap.addMarker(MarkerOptions().position(grodno).title("Marker in Grodno"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(grodno))
        mMap.setMinZoomPreference(12.0F)
        mMap.setMaxZoomPreference(20.0F)

        mMap.setOnMyLocationButtonClickListener(object : GoogleMap.OnMyLocationButtonClickListener {

            override fun onMyLocationButtonClick(): Boolean {

                weather.isEnabled = true

                return false
            }
        })

        enableMyLocation()

    }

    fun searchLocation(view: View) {

        if (!editText.text.toString().equals("") || editText.text.toString() != null) {

            weather.isEnabled = true

            var geocoder: Geocoder = Geocoder(this)

            var addressList: List<Address> =
                geocoder.getFromLocationName(editText.text.toString(), 1)

            var address: Address = addressList.get(0)

            var latLng: LatLng = LatLng(address.latitude, address.longitude)

            mMap.addMarker(MarkerOptions().position(latLng).title(editText.text.toString()))
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap.setMinZoomPreference(12.0F)
            mMap.setMaxZoomPreference(20.0F)

        }

    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun enableMyLocation() {

        if (isPermissionGranted()) {
            mMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    fun goIntent(view: View) {

        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)

    }

}