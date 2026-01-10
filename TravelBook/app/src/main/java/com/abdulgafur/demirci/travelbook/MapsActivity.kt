package com.abdulgafur.demirci.travelbook

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.abdulgafur.demirci.travelbook.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : ComponentActivity(), OnMapReadyCallback, OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncer: ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var trackBoolean: Boolean? = null
    private var selectedLatitude: Double? = null
    private var selectedLongitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = fragmentManager
            .findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

        registerLaucnher()

        sharedPreferences = this.getSharedPreferences("com.abdulgafur.demirci.travelbook", MODE_PRIVATE)
        trackBoolean = false
        selectedLatitude = 0.0
        selectedLongitude = 0.0
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)

        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager

        locationListener = LocationListener { location ->

            trackBoolean = sharedPreferences.getBoolean("trackBoolean", false)
            if (trackBoolean == false) {
                println("location: $location")
                val userLocation = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                sharedPreferences.edit { putBoolean("trackBoolean", true) }
            }

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // FINE LOCATION DENIED CASE
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // show snackbar
                permissionLauncer.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                // REQUEST PERMISSION
                permissionLauncer.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

        } else {
            // FINE LOCATION GRANTED CASE
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,  0, 0f, locationListener )
            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastLocation?.let {
                val lastUserLocation = LatLng(it.latitude, it.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15f))
            }
            mMap.isMyLocationEnabled = true
        }

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun registerLaucnher() {
        permissionLauncer =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionResult ->
                if (permissionResult) {
                    // Permission Granted by User
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,  0, 0f, locationListener )
                        val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        lastLocation?.let {
                            val lastUserLocation = LatLng(it.latitude, it.longitude)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation, 15f))
                        }
                        mMap.isMyLocationEnabled = true
                    }
                } else {
                    // Permission Denied by User
                    Toast.makeText(this@MapsActivity, "Permission needed!", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onMapLongClick(p0: LatLng) {
        println("Map long clicked")
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0))
        selectedLatitude = p0.latitude
        selectedLongitude = p0.longitude
    }

    fun save(view : View) {

    }

    fun delete(view : View) {

    }
}