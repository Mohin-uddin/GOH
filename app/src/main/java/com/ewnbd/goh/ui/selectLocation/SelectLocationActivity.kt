package com.ewnbd.goh.ui.selectLocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ewnbd.goh.R
import com.ewnbd.goh.databinding.ActivitySelectLocationBinding
import com.ewnbd.goh.ui.prefarence.PrefarenceViewModel
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.Permissons
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_select_location.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import com.google.android.gms.common.api.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    var temp=0
    companion object{
        const val LOCATION_PERMISSION_REQUEST = 1
        private const val  AUTOCOMPLETE_REQUEST_CODE = 1
    }

    private  val viewModel: PrefarenceViewModel by viewModels()
    var placesClient: PlacesClient? = null
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationListener: LocationListener
    private  var latitude:String=""
    private  var longitude:String=""
    var autocompleteSupportFragment: AutocompleteSupportFragment? = null
    var placeField: List<Place.Field> = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS
    )
    private val place1: MarkerOptions? = null
    private  var place2: MarkerOptions? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)

        if(Permissons.Check_FINE_LOCATION(this)) {
            initiatePlaces()
            setupPLaceAutoComplete()
        }
        else
        {
            Permissons.Request_FINE_LOCATION(this,22)
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment.getMapAsync(this)
        tvYes.setOnClickListener {
            viewModel.latitudeData(etLocation.text.toString(),latitude,longitude)
            onBackPressed()
        }
        if(temp==0) {
            temp=1
            etLocation.text = getAddress()
        }

        ivBackPress.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        Log.e("callingmap", "onMapReady: ")
        getLocationAccess()
    }

    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true

            getLocationUpdates()
         //   startLocationUpdates()
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST
            )
    }
    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 30000
        locationRequest.fastestInterval = 20000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        latitude=location.latitude.toString()
                        longitude=location.longitude.toString()
                      //  etLocation.text=location.
                        val markerOptions = MarkerOptions().position(latLng)
                        map.addMarker(markerOptions)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

                    }
                }
            }
        }
    }

    private fun initiatePlaces() {
        Places.initialize(this, getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)
    }
    private fun setupPLaceAutoComplete() {
        autocompleteSupportFragment = supportFragmentManager
                .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        autocompleteSupportFragment?.setPlaceFields(placeField)
        autocompleteSupportFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                if (Geocoder.isPresent()) {
                    try {
                        val location: String = place.getName().toString()
                        val gc = Geocoder(this@SelectLocationActivity)
                        val addresses: MutableList<android.location.Address>? =
                                gc.getFromLocationName(location, 5) // get the found Address Objects
                        val ll: MutableList<LatLng> =
                                ArrayList(addresses!!.size) // A list to save the coordinates if they are available
                        for (a in addresses) {
                            if (a.hasLatitude() && a.hasLongitude()) {
                                Log.e("latlongdata", "onPlaceSelected: "+a.latitude+"  "+a.longitude )
                                latitude=a.latitude.toString()
                                longitude=a.longitude.toString()
                                place2 = MarkerOptions().position(
                                        LatLng(
                                                a.getLatitude(),
                                                a.getLongitude()
                                        )
                                )
                                map.clear()
                                place2?.let {
                                    map.addMarker(
                                            MarkerOptions().position(it.position)
                                                    .title(place.name).icon(
                                                            BitmapDescriptorFactory.defaultMarker(
                                                                    BitmapDescriptorFactory.HUE_RED
                                                            )
                                                    )
                                    )
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, 15f))
                                }

                                etLocation.text =place.name+","+a.countryName
                                //Toast.makeText(MapsActivity.this, ""+a.getLatitude(), Toast.LENGTH_SHORT).show();
                                ll.add(LatLng(a.latitude, a.longitude))

                                ConstValue.selectAddress=etLocation.text.toString()
                                ConstValue.latitudeValue=a.latitude.toString()
                                ConstValue.longitudeValue = a.longitude.toString()
                                val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
                                val editor = preference.edit()
                                editor.putBoolean("isLoggedIn", true)
                                editor.putString("Latitude", a.latitude.toString())
                                editor.putString("Longitude",a.longitude.toString())
                                editor.putString("address",etLocation.text.toString())
                                editor.apply()
                            }
                        }
                    } catch (e: IOException) {
                        // handle the exception
                    }
                }
//                place1 = MarkerOptions().position(LatLng(latLng.latitude, latLng.longitude))
//                    .title("Current Position")



//                //drawline(place.getLatLng(),mylocation);
//                FetchURL(this@MapsActivity).execute(
//                    getUrl(
//                        place1.getPosition(),
//                        place2.getPosition(),
//                        "driving"
//                    ), "driving"
//                )
            }

            override fun onError(p0: Status) {

            }


        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                map.isMyLocationEnabled = true
            }
            else {
             //   Toast.makeText(this, "User has not granted location access permission",
                   //     Toast.LENGTH_LONG).show()
               // finish()
            }
        }
    }

    private fun getAddress(): String? {
        val gps = GPSTracker(this@SelectLocationActivity)
        val geocoder = Geocoder(this@SelectLocationActivity, Locale.getDefault())
        var address: String? = ""
        try {
            val addresses = geocoder.getFromLocation(gps.latitude, gps.longitude, 1)
            if (addresses != null && addresses.size > 0) {
                if(addresses[0].getAddressLine(0)!=null) {
                    address = addresses[0].getAddressLine(0)
                }
                else
                {
                    address = addresses[0].locality+","+addresses[0].countryName
                }
            }

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        return address

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}