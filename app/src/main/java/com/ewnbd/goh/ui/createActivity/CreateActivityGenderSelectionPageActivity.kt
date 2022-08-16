package com.ewnbd.goh.ui.createActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.createActivity.viewmodel.CreateActivityViewModel
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_gender_selection_page.*
import kotlinx.android.synthetic.main.activity_prefarence.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class CreateActivityGenderSelectionPageActivity : AppCompatActivity() {
    lateinit var  ageSelectionAdapter: AgeSelectionAdapter
    lateinit var tokenGenerate: HashMap<String, String>
    var gender = ""
    var maleState = false
    var femaleState = false
    var privacy = "Friends"
    private  val viewModel: CreateActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_gender_selection_page)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        ageSelectionAdapter = AgeSelectionAdapter()
        if(ConstValue.activty_gender!=""){
            if (ConstValue.activty_gender=="Any"){
                llCreateMale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                ivMaleTic.load(R.drawable.tick_circle)
                tvCreateMaleText.setTextColor(resources.getColor(R.color.white))
                ivCreateMale.load(R.drawable.ic_man)
                maleState=true
                femaleState=true

            }else if (ConstValue.activty_gender=="Female"){
                ivCreateFemale.load(R.drawable.ic_female_white)
                llCreateFemale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                tvFemaleText.setTextColor(resources.getColor(R.color.white))
                ivFemaleCircle.load(R.drawable.tick_circle)
                femaleState=true
            }else{
                llCreateMale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                ivMaleTic.load(R.drawable.tick_circle)
                tvCreateMaleText.setTextColor(resources.getColor(R.color.white))
                ivCreateMale.load(R.drawable.ic_man)
                maleState=true

            }
        }
        if (ConstValue.activity_privecy!=""){
            if (ConstValue.activity_privecy=="Friends"){
                tvSelectFriend.setBackgroundResource(R.drawable.curved_send_sms_shape_green)
                tvSelectPublic.setBackgroundResource(R.drawable.curved_shape_white)
                tvSelectFriend.setTextColor(resources.getColor(R.color.white))
                tvSelectPublic.setTextColor(resources.getColor(R.color.black))
                privacy = "Friends"
            }else{
                tvSelectPublic.setBackgroundResource(R.drawable.curved_send_sms_shape_green)
                tvSelectFriend.setBackgroundResource(R.drawable.curved_shape_white)
                tvSelectPublic.setTextColor(resources.getColor(R.color.white))
                tvSelectFriend.setTextColor(resources.getColor(R.color.black))
                privacy = "Public"
            }
        }
        rvAgeRangeSelection.layoutManager = GridLayoutManager(this,3)
        rvAgeRangeSelection.adapter = ageSelectionAdapter

        tvNextReviewPage.setOnClickListener {
            if (validation()) {
                if (femaleState&&maleState){
                    ConstValue.gender=RequestBody.create(MultipartBody.FORM, "Any")
                    ConstValue.activty_gender = "Any"
                }
                else if (femaleState)
                {
                    ConstValue.gender=RequestBody.create(MultipartBody.FORM, "FEMALE")
                    ConstValue.activty_gender = "Female"
                }
                else if (maleState){
                    ConstValue.gender=RequestBody.create(MultipartBody.FORM, "MALE")
                    ConstValue.activty_gender = "Male"
                }
                else if (femaleState&&maleState){
                    ConstValue.gender=RequestBody.create(MultipartBody.FORM, "Any")
                    ConstValue.activty_gender = "Any"
                }
                ConstValue.activity_privecy= privacy
                ConstValue.privacy=RequestBody.create(MultipartBody.FORM, privacy)
                startActivity(Intent(this, CreateActivityReview::class.java))
                finish()
            }
        }
        viewModel.getAgeListRequest(tokenGenerate)

        lifecycleScope.launchWhenCreated {
            viewModel.ageListResponse.collectLatest {
                it?.let {
                    for ((i, item) in it.results.withIndex()){
                        if (ConstValue.age_range==item.id){
                            ConstValue.ageState= i
                        }
                    }
                    ageSelectionAdapter.updateAge(it.results)
                }
            }
        }

        llCreateMale.setOnClickListener {
            if (!maleState) {
                llCreateMale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                ivMaleTic.load(R.drawable.tick_circle)
                tvCreateMaleText.setTextColor(resources.getColor(R.color.white))
                ivCreateMale.load(R.drawable.ic_man)
                maleState=true
            }else {
                llCreateMale.setBackgroundResource(R.drawable.curved_shape_btn_back_white)
                ivCreateMale.load(R.drawable.ic_male_black)
                tvCreateMaleText.setTextColor(resources.getColor(R.color.black))
                ivMaleTic.load(R.drawable.ic_circle_grey)
                maleState=false
            }
        }
        llCreateFemale.setOnClickListener {
            if(!femaleState) {
                ivCreateFemale.load(R.drawable.ic_female_white)
                llCreateFemale.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                tvFemaleText.setTextColor(resources.getColor(R.color.white))
                ivFemaleCircle.load(R.drawable.tick_circle)

                femaleState=true
            }else{
                llCreateFemale.setBackgroundResource(R.drawable.curved_shape_btn_back_white)
                ivCreateFemale.load(R.drawable.ic_woman)
                tvFemaleText.setTextColor(resources.getColor(R.color.black))
                ivFemaleCircle.load(R.drawable.ic_circle_grey)
                femaleState = false
            }

        }

        tvSelectFriend.setOnClickListener {
            tvSelectFriend.setBackgroundResource(R.drawable.curved_send_sms_shape_green)
            tvSelectPublic.setBackgroundResource(R.drawable.curved_shape_white)
            tvSelectFriend.setTextColor(resources.getColor(R.color.white))
            tvSelectPublic.setTextColor(resources.getColor(R.color.black))
            privacy = "Friends"
        }
        tvSelectPublic.setOnClickListener {
            tvSelectPublic.setBackgroundResource(R.drawable.curved_send_sms_shape_green)
            tvSelectFriend.setBackgroundResource(R.drawable.curved_shape_white)
            tvSelectPublic.setTextColor(resources.getColor(R.color.white))
            tvSelectFriend.setTextColor(resources.getColor(R.color.black))
            privacy="Public"
        }

        val gender = RequestBody.create(MultipartBody.FORM, "MALE")
        val privacy = RequestBody.create(MultipartBody.FORM, "Public")
        val ageRangeId = RequestBody.create(MultipartBody.FORM, "1")
        val activityTimeId = RequestBody.create(MultipartBody.FORM, "1")
    }

    private fun validation(): Boolean {

        Log.e("GenderSelectChcek", "validation: $femaleState  $maleState", )
        if (!femaleState && !maleState){
            Toast.makeText(this,"Please Select Gender", Toast.LENGTH_SHORT).show()
            return false
        }
        if (ConstValue.age_range==0){
            Toast.makeText(this,"Please Select Age Range", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}