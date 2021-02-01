package com.example.googlemapsweather

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
//        !!
        mMap = googleMap!!

        val grodno = LatLng(53.6803665, 23.8290348)
        mMap.addMarker(MarkerOptions().position(grodno).title("Marker in Grodno"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(grodno))
        mMap.setMinZoomPreference(12.0F)
        mMap.setMaxZoomPreference(20.0F)
    }

    fun searchLocation(view: View) {
        println("qwwwwww" + editText.text)

        if (!editText.text.toString().equals("") || editText.text.toString() != null) {

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

}