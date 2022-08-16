package com.ewnbd.goh.ui.createActivity

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.AddInterest
import com.ewnbd.goh.data.model.response_all_class.actvityDetails.ActvityDetailsResponse
import com.ewnbd.goh.data.model.response_all_class.mapSearch.Prediction
import com.ewnbd.goh.ui.createActivity.adapter.CategorySelectAdapter
import com.ewnbd.goh.ui.createActivity.adapter.TimerAdapter
import com.ewnbd.goh.ui.createActivity.viewmodel.CreateActivityViewModel
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.ui.nearbyActivitys.CategorySelection
import com.ewnbd.goh.ui.prefarence.MapSearchAdapter
import com.ewnbd.goh.ui.prefarence.OnItemClickListener
import com.ewnbd.goh.ui.prefarence.PrefarenceViewModel
import com.ewnbd.goh.ui.selectLocation.SelectLocationActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.show
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.datepicker.MaterialDatePicker
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_prefarence.*
import kotlinx.android.synthetic.main.bottom_category_select.view.*
import kotlinx.android.synthetic.main.bottom_sheet_time.*
import kotlinx.android.synthetic.main.bottom_sheet_time.view.*
import kotlinx.android.synthetic.main.location_add.view.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CreateActivity : AppCompatActivity(), OnItemClickListener ,SelectCategoryname{
    private var start: String = ""
    private var end : String =""
    var state = 0
    var categoryState = 0
    lateinit var endTime:AppCompatTextView
    lateinit var startTime: AppCompatTextView
    private var cityState: String=""
    private var longitude: String=""
    private var latitude: String =""
    lateinit var mapSearchAdapter: MapSearchAdapter
    private var cityName: String = ""
    private var userId: String="I am Boy"
    lateinit var dialog: DialogPlus
    private  val viewModelP: PrefarenceViewModel by viewModels()
    lateinit var timerAdapter: TimerAdapter
    lateinit var categorySelectAdapter: CategorySelectAdapter
    lateinit var tokenGenerate: HashMap<String, String>
    var SELECT_PICTURE = 200
    private var currentSelectedDate: Long? = null
    var min_practic =0
    private  val category: NearActivityListViewModel by viewModels()
    private  val viewModel: CreateActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        ConstValue.state=-1
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
         userId = preference?.getString("userId","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData

        if (ConstValue.editeState == 1){
            viewModel.getActvityDetailsRequest(tokenGenerate,ConstValue.activityId)
        }
        allClick()
        lifecycleScope.launchWhenCreated {
            viewModel.timeListResponse.collectLatest {
                if (it!=null) {
                    Log.e("dataCheckProfile", "updateTodo: $it" )
                    timerAdapter.timeUpdateList(it.results)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            category.categoryResponse.collectLatest {
                it?.let {
                    for ((i, item) in it.results.withIndex()){
                        if (ConstValue.categoryId==item.id.toString()){
                            ConstValue.state= i
                        }
                    }
                    categorySelectAdapter.categoryUpdateList(it.results)
                }
            }
        }
        llCategory.setOnClickListener {
            categoryState=1
            category()
        }
        llLocationAdd.setOnClickListener {
            locationSearch()
        }

        lifecycleScope.launchWhenCreated {
            viewModelP.locationMapResult.collectLatest {
                it?.let {
                    mapSearchAdapter.setUpdateData(it.predictions)
                }
            }
        }
        tvSelectAddress.isSelected = true
        tvSelectDate.isSelected = true
        tvSelectCategory.isSelected = true


        lifecycleScope.launchWhenCreated {
            viewModel.actvitiyDetails.collectLatest {
                it?.let {
                    dataSet(it)
                }
            }
        }
    }

    private fun dataSet ( it: ActvityDetailsResponse) {
        ConstValue.activity_name=it.name
        etCreateActivityName.setText(it.name)
        cvImage.show()
        ivSelectImageView.load("http://167.99.32.192"+it.photo)
        tvParticipateCounter.text = it.min_participate.toString()
        tvSelectDate.text = it.start_time+" - "+it.end_time
        start=it.start_time
        end = it.end_time
        ConstValue.activity_privecy=it.privacy
        ConstValue.age_range = it.age_range_id.toInt()
        ConstValue.activty_gender=it.gender
        ConstValue.activity_img= it.photo.toUri()
        ConstValue.activity_place = it.place
        ConstValue.about = it.desc
        ConstValue.activity_date_slot = it.activity_date
        latitude=it.latitude.toString()
        longitude=it.longitude.toString()
        tvSelectAddress.text = it.place

        ConstValue.age_range_id=RequestBody.create(MultipartBody.FORM,it.age_range_id )
        ConstValue.attendent = tvParticipateCounter.text.toString()
        ConstValue.activity_date= RequestBody.create(MultipartBody.FORM,it.activity_date )
        ConstValue.min_participate =
            RequestBody.create(MultipartBody.FORM, tvParticipateCounter.text.toString())
       // ConstValue.dp =  MultipartBody.Part.createFormData("photo", file.name, requestFile)

        tvAboutDes.setText(it.desc)
        cityState = "Select"
        ConstValue.startTime=RequestBody.create(MultipartBody.FORM, it.start_time)
        ConstValue.endTime=RequestBody.create(MultipartBody.FORM, it.end_time)
        ConstValue.desc = RequestBody.create(MultipartBody.FORM, tvAboutDes.text.toString())
        ConstValue.name =
            RequestBody.create(MultipartBody.FORM, etCreateActivityName.text.toString())
        ConstValue.latitude = RequestBody.create(MultipartBody.FORM, it.latitude.toString())
        ConstValue.longitude = RequestBody.create(MultipartBody.FORM, it.longitude.toString())
        ConstValue.place = RequestBody.create(MultipartBody.FORM, it.place)


        if (it.category_id!=null) {
            ConstValue.categoryId = it.category_id
            ConstValue.category_id = RequestBody.create(MultipartBody.FORM, it.category_id)
        }

    }

    fun allClick(){
        ivCross.setOnClickListener {
            onBackPressed()
        }
        tvNext.setOnClickListener {
            if (validation()) {
                ConstValue.activity_name = etCreateActivityName.text.toString()
                ConstValue.about =tvAboutDes.text.toString()
                ConstValue.attendent = tvParticipateCounter.text.toString()
                ConstValue.name =
                    RequestBody.create(MultipartBody.FORM, etCreateActivityName.text.toString())

                ConstValue.desc = RequestBody.create(MultipartBody.FORM, tvAboutDes.text.toString())
                ConstValue.min_participate =
                    RequestBody.create(MultipartBody.FORM, tvParticipateCounter.text.toString())
                startActivity(
                    Intent(
                        this@CreateActivity,
                        CreateActivityGenderSelectionPageActivity::class.java
                    )
                )

            }
        }

        llAddActivityPic.setOnClickListener {
            cvImage.show()
            openGalleryForImage()
        }
        tvPlus.setOnClickListener {
            var counter = tvParticipateCounter.text.toString().toInt()
            ++counter
            tvParticipateCounter.text=counter.toString()
        }
        tvMinus.setOnClickListener {
            var counter = tvParticipateCounter.text.toString().toInt()
            if(0<counter) {
                --counter
                tvParticipateCounter.text=counter.toString()
            }
        }
        llTimeDate.setOnClickListener {
            updateTodo()
        }

        lifecycleScope.launchWhenCreated {

        }

    }

    override fun onResume() {
        super.onResume()
    }
    private fun validation(): Boolean {

        if(etCreateActivityName.text.toString().isEmpty()) {
            etCreateActivityName.requestFocus()
            etCreateActivityName.error=getString(R.string.activity_name)
            return false
        }
        if (tvAboutDes.text.toString().isEmpty()){
            tvAboutDes.requestFocus()
            tvAboutDes.error="Enter Activity details"
            return false
        }
        if(tvParticipateCounter.text.toString() == "0"){
            Toast.makeText(this,"Please Enter Participate value",Toast.LENGTH_SHORT).show()
            return false
        }
        if (ConstValue.dp==null){
            Toast.makeText(this,"Please Select activity image",Toast.LENGTH_SHORT).show()
            return false
        }
        if (cityState==""){
            Toast.makeText(this,"Please Select Location",Toast.LENGTH_SHORT).show()
            return false
        }
        if (start.isEmpty()||end.isEmpty()){
            Toast.makeText(this,"Please Select Start time and End Time",Toast.LENGTH_SHORT).show()
            return false
        }
        if (ConstValue.activity_date_slot.isEmpty()){
            Toast.makeText(this,"Please Select Date slot",Toast.LENGTH_SHORT).show()
            return false
        }
        if (categoryState==0){
            Toast.makeText(this,"Please Select Category",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }



    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE){
            ivSelectImageView.load(data?.data) // handle chosen image
            val uri = data?.data
            if (uri!=null) {
                val imagePath = getPath(uri)
                val file = File(imagePath)
                //create RequestBody instance from file
                //create RequestBody instance from file
                val requestFile: RequestBody = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    file
                )
                ConstValue.dp =  MultipartBody.Part.createFormData("photo", file.name, requestFile)
                ConstValue.latitude = RequestBody.create(MultipartBody.FORM, "23.82231")
                ConstValue.longitude = RequestBody.create(MultipartBody.FORM, "90.38577")
                ConstValue.place = RequestBody.create(MultipartBody.FORM, "Kala chadpur road")
                ConstValue.activity_img= data.data!!

            }
        }
    }


    private fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }
    fun updateTodo() {


        dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.bottom_sheet_time))
            .setExpanded(true) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialog.holderView
        timerAdapter = TimerAdapter()
        if (ConstValue.activity_date_slot!=""){
            view.tvDateTimeSlot.text = ConstValue.activity_date_slot
            view.tvStartTime.text = start
            view.tvEndTime.text =end
        }
        view.ivCalender.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.
            datePicker()
                .build()

            datePicker.show(supportFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
                val date = dateFormatter.format(Date(it))
                view.tvDateTimeSlot.text=date
                ConstValue.activity_date= RequestBody.create(MultipartBody.FORM,date )
                ConstValue.activity_date_slot = date

            }
        }
        view.llStartTime.setOnClickListener {
            startTime = view.tvStartTime
            state=1
            val timePicker = TimePickerDialog(
                // pass the Context
                this,
                timePickerDialogListener,
                0,
                0,
                false
            )
            timePicker.show()
        }
        view.llEndTime.setOnClickListener {

            if(state==1) {
                state=2
                endTime = view.tvEndTime
                val timePicker = TimePickerDialog(

                    this,
                    timePickerDialogListener,

                    0,
                    // default minute when the time picker
                    // dialog is opened
                    0,
                    // 24 hours time picker is
                    // false (varies according to the region)
                    false
                )

                // then after building the timepicker
                // dialog show the dialog to user
                timePicker.show()
            }else{
                Toast.makeText(this,"Please select start time",Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getTimeRequest(tokenGenerate)
        dialog.show()



    }
    lateinit var dialogCC: DialogPlus


    fun category() {
        dialogCC = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.bottom_category_select))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialogCC.holderView
        view.tvDone.setOnClickListener {
            dialogCC.dismiss()
        }
        categorySelectAdapter = CategorySelectAdapter(this)

        view.rvCategorySelect.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        view.rvCategorySelect.adapter = categorySelectAdapter
        if (category.categoryResponse.value!=null){
            categorySelectAdapter.categoryUpdateList(category.categoryResponse.value!!.results)
        }
        dialogCC.show()
        category.getCategoryByRequest(tokenGenerate)

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> // logic to properly handle
            // the picked timings by user
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }


            val date12Format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            val date24Format = SimpleDateFormat("HH:mm:ss")
            val convertDate =date24Format.format(date12Format.parse(formattedTime))
            Log.e("TimeTable", " time      $convertDate")
            val timeSelect =""
            if(state==1) {
                start =formattedTime
                ConstValue.startTime=RequestBody.create(MultipartBody.FORM,convertDate )
                startTime.text = formattedTime

            }
            else{
                if(::startTime.isInitialized)
                tvSelectDate.text = startTime.text.toString()+ "-"+formattedTime
                ConstValue.endTime=RequestBody.create(MultipartBody.FORM,convertDate )
                endTime.text = formattedTime
                end =formattedTime
            }
        }

    lateinit var dialogL: DialogPlus
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
       //  city = pos.description
        cityState = "Select"
        tvSelectAddress.text = pos.description
        getLocationFromAddress(pos.description)
    }


    fun getLocationFromAddress(strAddress: String?) {
        ConstValue.activity_place=strAddress!!
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
            ConstValue.latitude = RequestBody.create(MultipartBody.FORM, latitude)
            ConstValue.longitude = RequestBody.create(MultipartBody.FORM, longitude)
            ConstValue.place = RequestBody.create(MultipartBody.FORM, strAddress!!)
            Log.i("LocationLat", "" + lat)
            Log.i("LocationLat", "" + lng)
            val latLng = LatLng(lat, lng)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            if (::dialogL.isInitialized)
                dialogL.dismiss()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun categoryName(name: String) {
        tvSelectCategory.text =name
    }


}