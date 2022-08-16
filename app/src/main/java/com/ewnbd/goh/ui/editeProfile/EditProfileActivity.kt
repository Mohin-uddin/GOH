package com.ewnbd.goh.ui.editeProfile

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.app.Dialog
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
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.UpdateNameRequest
import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.response_all_class.mapSearch.Prediction
import com.ewnbd.goh.ui.fragment.viewmodel.ProfileViewModel
import com.ewnbd.goh.ui.prefarence.MapSearchAdapter
import com.ewnbd.goh.ui.prefarence.OnItemClickListener
import com.ewnbd.goh.ui.prefarence.PrefarenceViewModel
import com.ewnbd.goh.ui.selectLocation.SelectLocationActivity
import com.ewnbd.goh.utils.ConstValue
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_prefarence.*
import kotlinx.android.synthetic.main.dialouge_all.*
import kotlinx.android.synthetic.main.item_dialuge_beef.*
import kotlinx.android.synthetic.main.location_add.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity(), OnItemClickListener {
    private var aboutPr: String=""
    lateinit var tokenGenerate: HashMap<String, String>
    var SELECT_PICTURE = 200
    private var gender : String ="Male"
    private var date : String=""
    var latitude =""
    var longitude = ""
    var city=""
    private  val viewModelP: PrefarenceViewModel by viewModels()
    private  val viewModelProfile: ProfileViewModel by viewModels()
    private  val viewModel: EditProfileViewmodel by viewModels()
    private  val viewModelPre: PrefarenceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        dateSet()
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = java.util.HashMap<String, String>()
        val userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (it=="413"){
                        Toast.makeText(this@EditProfileActivity,"Profile picture is too large",Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.getDataClear()
            }
        }
        ivUpdateProfile.setOnClickListener {
            val basicProfileRequest = BasicProfileRequest(gender,date,aboutPr,tvEditCityName.text.toString(), longitude, latitude)
            viewModelPre.getPrefarenceRequest(tokenData,basicProfileRequest)
        }
        lifecycleScope.launchWhenCreated {
            viewModelPre.prefarenceResponse.collectLatest {
                it?.let {
                    onBackPressed()
                }
            }
        }
        ivEditBack.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launchWhenCreated {
            viewModelP.locationMapResult.collectLatest {
                it?.let {
                    mapSearchAdapter.setUpdateData(it.predictions)
                }
            }
        }
        tvEditCityName.isSelected=true
    }

    private fun dateSet() {
        lifecycleScope.launchWhenCreated {
            viewModel.profilePicUpdateResponse.collectLatest {
                it?.let {
                    Log.e("responseDataCheck", "onCreate: ${it.dp}")
                }
            }
        }

        tvEditGender.setOnClickListener {
            gender()
        }
        lifecycleScope.launchWhenCreated {
            viewModelProfile.profileResponse.collectLatest {
                it?.let {
                    tvEditProfileName.text=it.data.user.full_name
                    tvEditGender.text= it.data.gender
                    tvEditCityName.text= it.data.city
                    tvEditEmail.text=it.data.user.email
                    tvEditPhoneNumber.text=it.data.user.username
                    civProfilePicEdit.load("http://167.99.32.192"+it.data.user.dp)
                    date = it.data.date_of_birth
                    aboutPr= it.data.about
                    val age=it.data.date_of_birth
                    if (age!=null) {
                        val year = age.subSequence(0, 4).toString().toInt()
                        val month = age.subSequence(5, 7).toString().toInt()
                        val day = age.subSequence(8, 10).toString().toInt()
                        tvEditAge.text = ConstValue.getAge(year, month, day)
                    }
                    else{
                        date="1995-05-28"
                    }
                    if (aboutPr==null){
                        aboutPr=""
                    }
                    if (tvEditCityName.text.toString()==""){
                    //    tvEditCityName.text="Dhaka,Bangladesh"
                    }
                }
            }
        }

        tvEditProfileName.setOnClickListener {
            updateName("User Name",tokenGenerate)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.updateNameResponse.collectLatest {
                it?.let {
                    tvEditProfileName.text=it.full_name
                }
            }
        }

        lifecycleScope.launch {
            viewModel.responseCode.collectLatest {
                if (it!="0"&&it!="200"){
                    Toast.makeText(this@EditProfileActivity,"Server error $it",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
            }
        }
        tvEditCityName.setOnClickListener {
            locationSearch()
            //startActivity(Intent(this,SelectLocationActivity::class.java))
        }
        lifecycleScope.launchWhenCreated {
            viewModelPre.location.collectLatest {
                it?.let {
                    tvEditCityName.text=it.cityName
                    latitude=it.latitude
                    longitude=it.longitude
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val userId = preference?.getString("userId","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        ivCamera.setOnClickListener {
            openGalleryForImage()
        }
        viewModelProfile.getProfileRequest(tokenGenerate,userId)
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURE){
            civProfilePicEdit.load(data?.data) // handle chosen image
            val uri = data?.data
            if (uri!=null) {
                val imagePath = getPath(uri)
                val file = File(imagePath)
                //create RequestBody instance from file
                //create RequestBody instance from file
                val requestFile: RequestBody = RequestBody.create(
                    "multipart/form-data".toMediaTypeOrNull(),
                    file
                ) //allow image and any other file


                // MultipartBody.Part is used to send also the actual file name

                // MultipartBody.Part is used to send also the actual file name
                val body =  MultipartBody.Part.createFormData("dp", file.name, requestFile)
                if (::tokenGenerate.isInitialized)
                    viewModel.getProfileRequest(tokenGenerate,body)
            }


        }
    }


    fun updateName(name: String, tokenGenerate: HashMap<String, String>) {
        val dialog = Dialog(this, R.style.CommonDialog2)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialouge_all)
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            dialog.ivLoader,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f))
        scaleDown.duration = 310

        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE
        dialog.tvDialogName.text=name
        dialog.show()
        dialog.tvDialogUpdate.setOnClickListener {
            if (dialog.etDialogData.text.toString().isNotEmpty()) {
                dialog.dismiss()
                val updateNameRequest = UpdateNameRequest(dialog.etDialogData.text.toString())
                viewModel.updateNameRequest(tokenGenerate,updateNameRequest)
            }else{
                Toast.makeText(this,"Enter your name",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun gender() {
        val dialog = Dialog(this, R.style.CommonDialog2)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.gender_selector)
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            dialog.ivLoader,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f))
        scaleDown.duration = 310

        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE
        dialog.show()
        dialog.llMale.setOnClickListener {
            dialog.llMale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
            dialog.llFemale.setBackgroundResource(R.drawable.curved_shape_btn_back_white)
            dialog.ivMale.load(R.drawable.ic_man)
            dialog.ivFemale.load(R.drawable.ic_woman)
            dialog.tvMaleName.setTextColor(resources.getColor(R.color.white))
            dialog.tvFemale.setTextColor(resources.getColor(R.color.black))
            gender = "Male"
            tvEditGender.text="Male"
        }
        dialog.llFemale.setOnClickListener {
            dialog.llFemale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
            dialog.llMale.setBackgroundResource(R.drawable.curved_shape_btn_back_white)
            dialog.ivMale.load(R.drawable.ic_male_black)
            dialog.ivFemale.load(R.drawable.ic_female_white)
            dialog.tvFemale.setTextColor(resources.getColor(R.color.white))
            dialog.tvMaleName.setTextColor(resources.getColor(R.color.black))
            gender="Female"
            tvEditGender.text="Female"
        }
        dialog.tvDialogUpdate.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun itemClickListener(pos: Prediction) {
        //cityState = "Select"
        dialogL.dismiss()
        tvEditCityName.text = pos.description
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
            Log.i("LocationLat", "" + lat)
            Log.i("LocationLat", "" + lng)
            val latLng = LatLng(lat, lng)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)

        } catch (e: IOException) {
            e.printStackTrace()
        }
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
}