package com.ewnbd.goh.ui.filter

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.data.model.request_all_class.ActivityFilterRequest
import com.ewnbd.goh.ui.createActivity.AgeSelectionAdapter
import com.ewnbd.goh.ui.createActivity.adapter.TimerAdapter
import com.ewnbd.goh.ui.createActivity.viewmodel.CreateActivityViewModel
import com.ewnbd.goh.ui.nearbyActivitys.CategorySelection
import com.ewnbd.goh.utils.ConstValue
import com.google.android.material.datepicker.MaterialDatePicker
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.bottom_sheet_category.view.*
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.Result
import com.ewnbd.goh.data.model.response_all_class.mapSearch.Prediction
import com.ewnbd.goh.ui.prefarence.MapSearchAdapter
import com.ewnbd.goh.ui.prefarence.OnItemClickListener
import com.ewnbd.goh.ui.prefarence.PrefarenceViewModel
import com.ewnbd.goh.ui.selectLocation.SelectLocationActivity
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_near_by_activities.*
import kotlinx.android.synthetic.main.bottom_sheet_category.view.tvNextData
import kotlinx.android.synthetic.main.location_add.view.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class FilterActivity : AppCompatActivity(),SelectCategoryInterface, OnItemClickListener {
    private var resultValue: String=""
    private var categoryId =""
    lateinit var filterCategoryAdapter : FilterCategoryAdapter
    lateinit var timerAdapter: TimerAdapter
    lateinit var ageSelectionAdapter: AgeSelectionAdapter
    lateinit var activityDateOfActivityAdapter: DateOfActivityAdapter
    lateinit var tokenGenerate: HashMap<String, String>
    var latitude =""
    var longitude = ""
    var city=""
     var categoryData = ArrayList<Result>()
    private  val viewModel: FilterViewModel by viewModels()
    private  val viewModelP: PrefarenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        ConstValue.activty_gender=""
        ConstValue.latitudeValue=""
        ConstValue.longitudeValue=""

        viewModel.getTimeRequest(tokenGenerate)
        viewModel.getAgeListRequest(tokenGenerate)
        timerAdapter = TimerAdapter()
        ageSelectionAdapter = AgeSelectionAdapter()
        activityDateOfActivityAdapter = DateOfActivityAdapter()
        llCategories.setOnClickListener {
            updateTodo()
        }
        tvSelectArea.setOnClickListener {
            locationSearch()
        }
        lifecycleScope.launchWhenCreated {
            viewModel.categoryResponse.collectLatest {
                it?.let {
                    categoryData.clear()
                    categoryData.addAll(it.results)
                    filterCategoryAdapter.categoryUpdateList(it.results)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.timeListResponse.collectLatest {
                it?.let {
                    timerAdapter.timeUpdateList(it.results)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.ageListResponse.collectLatest {
                it?.let {
                    ageSelectionAdapter.updateAge(it.results)
                }
            }
        }

        rvTimeSelection.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvTimeSelection.adapter=timerAdapter

        rvAgeRange.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvAgeRange.adapter=ageSelectionAdapter
        rvDateSelection.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvDateSelection.adapter=activityDateOfActivityAdapter

        rvGenderSelect.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvGenderSelect.adapter=GenderSelectAdapter()

        tvResultData.setOnClickListener {
            if (validation()) {
                val filterRequest = ActivityFilterRequest(
                    categoryId, ConstValue.activty_gender,
                    ConstValue.activity_date_slot, ConstValue.latitudeValue, ConstValue.longitudeValue,ConstValue.activity_timeId,ConstValue.age_range)
                viewModel.getFilterByRequest(tokenGenerate,filterRequest)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.filterResponse.collectLatest {
                it?.let {
                    if (it.results.isNotEmpty()) {
                        ConstValue.filterResult = it
                        startActivity(Intent(this@FilterActivity, FilterResultActivity::class.java))
                    }else{
                        Toast.makeText(this@FilterActivity,"Result not found",Toast.LENGTH_SHORT).show()
                    }
                    viewModel.getDataClear()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (it!="0" && it!="200"){
                        Toast.makeText(this@FilterActivity, " Filter result not found",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        tvReset.setOnClickListener {
            ConstValue.selectAddress=""
            categoryId=""
            startActivity(Intent(this, FilterActivity::class.java))
            finish()
        }
        ivFilterBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            viewModelP.locationMapResult.collectLatest {
                it?.let {
                    mapSearchAdapter.setUpdateData(it.predictions)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        tvSelectArea.text=ConstValue.selectAddress
    }
    override fun onBackPressed() {
        ConstValue.selectAddress=""
        super.onBackPressed()
    }
    private fun validation(): Boolean {
        if (categoryId==""){
            Toast.makeText(this,"Select category",Toast.LENGTH_SHORT).show()
            return false
        }
        if (ConstValue.activty_gender==""){
            Toast.makeText(this,"Select Gender",Toast.LENGTH_SHORT).show()
            return false
        }
        if (ConstValue.activity_date_slot==""){
            Toast.makeText(this,"Select Date of activities",Toast.LENGTH_SHORT).show()
            return false
        }
        if (ConstValue.activity_timeId==0){
            Toast.makeText(this,"Select times of activities",Toast.LENGTH_SHORT).show()
            return false
        }
        if (ConstValue.age_range==0){
            Toast.makeText(this,"Select age of activities",Toast.LENGTH_SHORT).show()
            return false
        }
        if (tvSelectArea.text.toString().isEmpty()){
            Toast.makeText(this,"Select city area",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun updateTodo() {

        val dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.bottom_sheet_category))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialog.holderView
        filterCategoryAdapter = FilterCategoryAdapter(this)

        view.tvNextData.setOnClickListener {
            dialog.dismiss()
        }
        view.rvCategory.layoutManager = GridLayoutManager(applicationContext,2)
        view.rvCategory.adapter = filterCategoryAdapter
        if (viewModel.categoryResponse.value!=null){
            filterCategoryAdapter.categoryUpdateList(viewModel.categoryResponse.value!!.results)
        }else{
            viewModel.getCategoryByRequest(tokenGenerate)
        }

        dialog.show()



    }


    override fun selectString(data: HashMap<Int,Boolean>) {
        var pos =0
        resultValue =""
        categoryId=""
        for (item in data){
            if (item.value){
                if (resultValue!="") {
                    resultValue += ","
                    categoryId+=","
                }
                resultValue+=categoryData[pos].cat_name
                categoryId+=categoryData[pos].id
            }
            pos++
        }
        if (resultValue==""){
            categoryId=""
        }
        tvCategory.text =resultValue
    }

    lateinit var dialogL: DialogPlus
    lateinit var mapSearchAdapter: MapSearchAdapter
    fun locationSearch() {
        dialogL = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.location_add))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialogL.holderView
        view.tvNextData.setOnClickListener {
            dialogL.dismiss()
        }

        view.llMapView.setOnClickListener {
            startActivity(Intent(this, SelectLocationActivity::class.java))
        }
        mapSearchAdapter = MapSearchAdapter(this)

        view.rvMapList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        view.rvMapList.adapter = mapSearchAdapter
        view.svSearchTodo.addTextChangedListener(textWatcher)
        dialogL.show()


    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            Log.e("CHeckData", "afterTextChanged: $s")
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            Log.e("CHeckData", "beforeTextChanged: $s")
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModelP.getMapResponse(resources.getString(R.string.google_maps_key),s.toString())
        }
    }
    override fun itemClickListener(pos: Prediction) {
        dialogL.dismiss()
        tvSelectArea.text = pos.description
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

            latitude=lat.toString()
            longitude=lng.toString()
            city = strAddress!!
            Log.e("LocationLat", "" + lat)
            Log.e("LocationLat", "" + lng)
            ConstValue.latitudeValue =latitude
            ConstValue.longitudeValue=longitude
            val latLng = LatLng(lat, lng)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}