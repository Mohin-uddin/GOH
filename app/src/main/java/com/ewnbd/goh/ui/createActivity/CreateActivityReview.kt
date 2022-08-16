package com.ewnbd.goh.ui.createActivity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.ewnbd.goh.MainActivity
import com.ewnbd.goh.ui.promotion.PromoteActivity
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.createActivity.viewmodel.CreateActivityViewModel
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.invisible
import com.ewnbd.goh.utils.show
import com.ncorti.slidetoact.SlideToActView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_review.*
import kotlinx.android.synthetic.main.dialogue_succes.*
import kotlinx.android.synthetic.main.dialouge_all.*
import kotlinx.android.synthetic.main.item_dialuge_beef.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CreateActivityReview : AppCompatActivity(), SlideToActView.OnSlideCompleteListener {

    private var userId: String=""
    private lateinit var dialouge: Dialog
    lateinit var tokenGenerate: HashMap<String, String>
    private  val viewModel: CreateActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_review)

        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        userId = preference?.getString("userId","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData

        tvEdit.setOnClickListener {
            onBackPressed()
        }
        dataSet()

        example.onSlideCompleteListener = this
        ivReviewBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            viewModel.createActivityResponse.collectLatest {
                it?.let {
                    ConstValue.activityId = it.id.toString()
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    updateName()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    if(it=="403") {
                        viewModel.getDataClear()
                        Toast.makeText(this@CreateActivityReview, "permission_denied: You do not have permission to perform this action", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    }

    private fun dataSet() {
        ivBannerActivity.load(ConstValue.activity_img)
        tvActivitiesName.text = ConstValue.activity_name
        tvPraivecy.text = ConstValue.activity_privecy
        tvAboutDetails.text= ConstValue.about
        tvPersonAttend.text = ConstValue.attendent+" Persons"
        tvActivityDate.text = ConstValue.activity_date_slot +", "+ConstValue.activity_time
        tvActivityLocation.text = ConstValue.activity_place
        tvActivityGender.text = ConstValue.activty_gender
    }

    override fun onSlideComplete(view: SlideToActView) {

        view.outerColor=resources.getColor(R.color.slider_color)
        tvView.setBackgroundResource(R.drawable.curved_shape_green)
        example.invisible()
        ivBottom.show()
        if (::tokenGenerate.isInitialized&&ConstValue.dp!=null) {
            viewModel.getDataClear()
            Log.e("slideCehck", "onSlideComplete: " )
            dialouge= ConstValue.animation(this,0)
            if(ConstValue.editeState == 1){
                viewModel.updateActivity(
                    tokenGenerate,
                    ConstValue.dp!!,
                    ConstValue.name!!,
                    ConstValue.desc!!,
                    ConstValue.min_participate!!,
                    ConstValue.gender!!,
                    ConstValue.privacy!!,
                    ConstValue.latitude!!,
                    ConstValue.longitude!!,
                    ConstValue.place!!,
                    ConstValue.activity_date!!,
                    ConstValue.category_id!!,
                    ConstValue.age_range_id!!,
                    ConstValue.startTime!!,
                    ConstValue.endTime!!,
                    ConstValue.activityId
                )
            }else{
                viewModel.getRecentCodeGenerateRequest(
                    tokenGenerate,
                    ConstValue.dp!!,
                    ConstValue.name!!,
                    ConstValue.desc!!,
                    ConstValue.min_participate!!,
                    ConstValue.gender!!,
                    ConstValue.privacy!!,
                    ConstValue.latitude!!,
                    ConstValue.longitude!!,
                    ConstValue.place!!,
                    ConstValue.activity_date!!,
                    ConstValue.category_id!!,
                    ConstValue.age_range_id!!,
                    ConstValue.startTime!!,
                    ConstValue.endTime!!,
                    userId
                )
            }

        }
    }

    fun updateName() {
        val dialog = Dialog(this, R.style.CommonDialog2)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_succes)
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            dialog.ivLoader,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f))
        scaleDown.duration = 310

        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE
        dialog.show()
        dialog.vCross.setOnClickListener {
           dialog.dismiss()
            startActivity(Intent(this,MainActivity::class.java))
        }
        dialog.vPromotion.setOnClickListener {
            startActivity(Intent(this, PromoteActivity::class.java))
            finish()
            dialog.dismiss()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun dateSelect(){

    }


}