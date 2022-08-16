package com.ewnbd.goh.ui.activitiesDetails

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.ui.friendProfile.FriendProfileActivity
import com.ewnbd.goh.utils.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_activities_details.*
import kotlinx.android.synthetic.main.item_activies.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat

@AndroidEntryPoint
class ActivitiesDetails : AppCompatActivity() , OnMapReadyCallback {
    var placesClient: PlacesClient? = null
    private lateinit var map: GoogleMap
    companion object{
        const val LOCATION_PERMISSION_REQUEST = 1

    }
    private lateinit var dialouge: Dialog
    private  val viewModel: ActivityDetailsViewModel by viewModels()
    private val viewmodelNear : NearActivityListViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationListener: LocationListener
    private  var latitude:String=""
    private  var longitude:String=""
    var autocompleteSupportFragment: AutocompleteSupportFragment? = null
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities_details)

        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        val userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        if(Permissons.Check_FINE_LOCATION(this)) {
            initiatePlaces()
        }
        else
        {
            Permissons.Request_FINE_LOCATION(this,22)
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapViewActivity) as SupportMapFragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment.getMapAsync(this)

        tvAskTOJoin.setOnClickListener {
            if (tvAskTOJoin.text.equals("Join request")) {
                tvAskTOJoin.text="Withdraw Request"
                tvAskTOJoin.setTextColor(resources.getColor(R.color.colorPrimary))
                tvAskTOJoin.setBackgroundResource(R.drawable.green_line_border)
                viewModel.getProfileRequest(tokenGenerate,ConstValue.activityItem.id.toString())

            }else if (tvAskTOJoin.text.equals("Withdraw Request")) {
                tvAskTOJoin.text = "Join request"
                tvAskTOJoin.setTextColor(Color.parseColor("#FFFFFF"))
                tvAskTOJoin.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                viewmodelNear.getWithdrawRequest(tokenGenerate,ConstValue.activityItem.id.toString())
            }

        }
        tvSponsorPerson.setOnClickListener {
            ConstValue.userId=ConstValue.activityItem.creator.id.toString()
            startActivity(Intent(this,FriendProfileActivity::class.java))
        }
        resultSet()

        lifecycleScope.launchWhenCreated {
            viewModel.activityRequestResponse.collectLatest {
               it?.let {


               }
            }
        }
        lifecycleScope.launch {
            viewModel.responseCode.collectLatest {
                if (it!="0"&&it!="200"){
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(this@ActivitiesDetails,"Server error $it",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }

            }
        }
        ivDetailsBackArrow.setOnClickListener {
            onBackPressed()
        }

        when (ConstValue.activityItem.status) {
            0 -> {
                cvLocation.gone()
                tvAskTOJoin.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                tvAskTOJoin.setTextColor(Color.parseColor("#FFFFFF"))
                tvAskTOJoin.text = "Join request"
            }
            1 -> {
                cvLocation.gone()
                tvAskTOJoin.text = "Withdraw Request"
                tvAskTOJoin.setBackgroundResource(R.drawable.music_back)
                tvAskTOJoin.setTextColor(Color.parseColor("#41B06B"))
            }
            2 -> {
                cvLocation.show()
                tvAskTOJoin.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                tvAskTOJoin.setTextColor(Color.parseColor("#FFFFFF"))
                tvAskTOJoin.text = "Get Direction"
            }
        }

        appCompatImageView2.setOnClickListener {
            dialouge= this.let { ConstValue.animation(it,0) }
            viewmodelNear.getActivityShare(tokenGenerate,ConstValue.activityItem.id.toString())
        }

        lifecycleScope.launchWhenCreated {
            viewmodelNear.activityShare.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(this@ActivitiesDetails,it.success_msg,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun resultSet() {
        if(ConstValue.activityItem.priority!=0){
            llSponsorMarker.show()
        }else{
            llSponsorMarker.invisible()
        }
        tvActivitiesName.text = ConstValue.activityItem.name
        tvSponsorPerson.text = ConstValue.activityItem.creator.email
        var gender =getString(R.string.any)
        tvAboutDetails.text= ConstValue?.activityItem?.desc
        if (ConstValue.activityItem?.gender=="MALE"){
            gender=getString(R.string.male)
        }else if(ConstValue.activityItem?.gender=="FEMALE"){
            gender=getString(R.string.female)
        }
        tvGenderActivity.text = gender
        Glide.with(this).load(ConstValue.activityItem.photo)
            .placeholder(R.drawable.logo).into(appCompatImageView)
//        appCompatImageView.load(ConstValue.activityItem.photo)
        tvJoinPersonCount.text = ConstValue.activityItem.min_participate.toString()+"+ Person"
        tvLocation.text = ConstValue.activityItem.place
        tvActivityStartDate.text = ConstValue.activityItem?.activity_date+" "+Convert24to12(ConstValue.activityItem?.start_time)+"-"+Convert24to12(ConstValue.activityItem?.end_time)

    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
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
                LOCATION_PERMISSION_REQUEST)
    }
    private fun getLocationUpdates() {
        val markerOptions = MarkerOptions()
        val latLng = LatLng(ConstValue.activityItem.latitude,ConstValue.activityItem.longitude)
        markerOptions.position(latLng)
        markerOptions.title(ConstValue.activityItem.name)
        map.clear()
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f))
        map.addMarker(markerOptions)
    }

    private fun initiatePlaces() {
        Places.initialize(this, getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)
    }

    fun Convert24to12(time: String?): String? {
        var convertedTime = ""
        try {
            val displayFormat = SimpleDateFormat("hh:mm a")
            val parseFormat = SimpleDateFormat("HH:mm:ss")
            val date: java.util.Date? = parseFormat.parse(time)
            convertedTime = displayFormat.format(date)
            println("convertedTime : $convertedTime")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertedTime
        //Output will be 10:23 PM
    }
}