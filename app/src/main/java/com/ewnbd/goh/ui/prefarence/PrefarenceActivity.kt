package com.ewnbd.goh.ui.prefarence

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ewnbd.goh.MainActivity
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.AddInterest
import com.ewnbd.goh.data.model.request_all_class.InterserAddRequest
import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.response_all_class.mapSearch.Prediction
import com.ewnbd.goh.ui.selectLocation.SelectLocationActivity
import com.ewnbd.goh.utils.ConstValue
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.GeoPoint
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_near_by_activities.*
import kotlinx.android.synthetic.main.activity_prefarence.*
import kotlinx.android.synthetic.main.bottom_category_select.view.*
import kotlinx.android.synthetic.main.location_add.view.*
import kotlinx.coroutines.flow.collectLatest
import java.io.IOException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


@AndroidEntryPoint
class PrefarenceActivity : AppCompatActivity(),OnItemClickListener,InterestList {

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var date = ""
    var gender = "Male"
    var latitude =""
    var longitude = ""
    var city=""
    var finalInterest = ""
    private var currentSelectedDate: Long? = null
    private lateinit var dialouge: Dialog
    lateinit var mapSearchAdapter: MapSearchAdapter
    private  val viewModel: PrefarenceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prefarence)

        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        val userId= preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData

        viewModel.getInterestList(tokenGenerate)
        llMale.setOnClickListener {
            llMale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
            llFemale.setBackgroundResource(R.drawable.curved_shape_btn_back_white)
            ivMale.load(R.drawable.ic_man)
            ivFemale.load(R.drawable.ic_woman)
            tvMaleName.setTextColor(resources.getColor(R.color.white))
            tvFemale.setTextColor(resources.getColor(R.color.black))
            gender = "Male"
        }
        llFemale.setOnClickListener {
            llFemale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
            llMale.setBackgroundResource(R.drawable.curved_shape_btn_back_white)
            ivMale.load(R.drawable.ic_male_black)
            ivFemale.load(R.drawable.ic_female_white)
            tvFemale.setTextColor(resources.getColor(R.color.white))
            tvMaleName.setTextColor(resources.getColor(R.color.black))
            gender="Female"
        }
        cvDate.setOnClickListener {
            showDatePicker()
        }
        tvNextData.setOnClickListener {
            if(validation()) {
                Log.e("tokencCheck", "onCreate: $tokenData" )
                val interserAddRequest =InterserAddRequest(finalInterest)
                viewModel.getAddInterest(tokenGenerate, userId,interserAddRequest)
                dialouge= ConstValue.animation(this,0)
                val basicProfileRequest = BasicProfileRequest(gender,date,tvAboutText.text.toString(),city,longitude,latitude)
                viewModel.getPrefarenceRequest(tokenData,basicProfileRequest)
            }
        }
        val intereAdapter = InterestAdapter(this)
        rvInterest.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvInterest.adapter = intereAdapter

        lifecycleScope.launchWhenCreated {
            viewModel.interestAddResponse.collectLatest {
                it?.let {
                    Log.e("intereest", "onCreate: ${it.user}", )
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.prefarenceResponse.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    startActivity(Intent(this@PrefarenceActivity,MainActivity::class.java))
                    finish()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                Log.e("serverCode", "onCreate: $it" )
                if (it!="0"&&it!="200"){
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    if (it=="401")
                    Toast.makeText(this@PrefarenceActivity,"Authentication credentials were not provided.",Toast.LENGTH_SHORT).show()
                    else
                    {
                        Toast.makeText(this@PrefarenceActivity,"Server error please try again",Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.getDataClear()

            }
        }
        tvCityName.setOnClickListener {
            locationSearch()
        }
            ivBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            viewModel.location.collectLatest {
                it?.let {
                    tvCityName.text=it.cityName
                    latitude=it.latitude
                    longitude=it.longitude
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.locationMapResult.collectLatest {
                it?.let {
                    mapSearchAdapter.setUpdateData(it.predictions)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.interestResponse.collectLatest {
                it?.let {
                    intereAdapter.updateList(it.results)
                }
            }
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun validation(): Boolean {

        if (date=="") {
            Toast.makeText(this,"Please select date",Toast.LENGTH_SHORT).show()
            return false
        }
        if(city==""){
            Toast.makeText(this,"Please select date",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showDatePicker() {
        val selectedDateInMillis = currentSelectedDate ?: System.currentTimeMillis()

        MaterialDatePicker.Builder.datePicker().setSelection(selectedDateInMillis).build().apply {
            addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis) }
        }.show(supportFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }

    private fun onDateSelected(dateTimeStampInMillis: Long) {
        currentSelectedDate = dateTimeStampInMillis
        val dateTime: LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(
            currentSelectedDate!!
        ), ZoneId.systemDefault())
        val dateAsFormattedText: String = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        tvDateOfBirth.text = dateAsFormattedText
        date=dateAsFormattedText
    }

    lateinit var dialog: DialogPlus
    fun locationSearch() {
        dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.location_add))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialog.holderView
        view.tvNextData.setOnClickListener {
            dialog.dismiss()
        }

        view.llMapView.setOnClickListener {
            startActivity(Intent(this, SelectLocationActivity::class.java))
        }
        mapSearchAdapter = MapSearchAdapter(this)

        view.rvMapList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        view.rvMapList.adapter = mapSearchAdapter
        view.svSearchTodo.addTextChangedListener(textWatcher)
        dialog.show()


    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Log.e("CHeckData", "afterTextChanged: $s")
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Log.e("CHeckData", "beforeTextChanged: $s")
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.getMapResponse(resources.getString(R.string.google_maps_key),s.toString())
        }
    }
    override fun itemClickListener(pos: Prediction) {
        city=pos.description
        getLocationFromAddress(pos.description)
    }


    fun getLocationFromAddress(strAddress: String?) {

        val coder = Geocoder(this)
        val addresses: List<Address>?
        try {
            addresses = coder.getFromLocationName(strAddress, 5)
            if (addresses == null) {
            }
            val location = addresses[0]
            val lat = location.latitude
            val lng = location.longitude
            tvCityName.text=strAddress
            latitude=lat.toString()
            longitude=lng.toString()
            Log.i("LocationLat", "" + lat)
            Log.i("LocationLat", "" + lng)
            val latLng = LatLng(lat, lng)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            if (::dialog.isInitialized)
            dialog.dismiss()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun interestList(interest: HashMap<String, String>) {
        finalInterest=""
        for (item in interest){
            if (item.value!=null&&item.value!=""){
                finalInterest+=item.value+","
            }
        }
        val size = finalInterest.length-1
        if(0<=size) {
            val subString = finalInterest.subSequence(0, size)
            finalInterest = subString.toString()
            Log.e("interestList", "interestList: ${finalInterest} ",)
        }
    }

}