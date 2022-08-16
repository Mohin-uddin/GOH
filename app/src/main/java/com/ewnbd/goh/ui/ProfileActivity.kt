package com.ewnbd.goh.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.InterserAddRequest
import com.ewnbd.goh.data.model.request_all_class.UpdateNameRequest
import com.ewnbd.goh.data.model.response_all_class.AboutResponse
import com.ewnbd.goh.data.model.response_all_class.NickNameUpdate
import com.ewnbd.goh.ui.editeProfile.EditProfileActivity
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.fragment.adapter.UserInterestAdapter
import com.ewnbd.goh.ui.fragment.viewmodel.ProfileViewModel
import com.ewnbd.goh.ui.prefarence.InterestAdapter
import com.ewnbd.goh.ui.prefarence.InterestList
import com.ewnbd.goh.ui.prefarence.MapSearchAdapter
import com.ewnbd.goh.ui.prefarence.PrefarenceViewModel
import com.ewnbd.goh.ui.selectLocation.SelectLocationActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.ConstValue.userId
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_prefarence.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.bottom_sheet_interest.view.*
import kotlinx.android.synthetic.main.dialouge_all.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.tvAccountEmail
import kotlinx.android.synthetic.main.fragment_profile.tvPersonName
import kotlinx.android.synthetic.main.item_dialuge_beef.*
import kotlinx.android.synthetic.main.location_add.view.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ProfileActivity : AppCompatActivity(), InterestList {

    private  val viewModel: ProfileViewModel by viewModels()
    lateinit var interestAdapter: UserInterestAdapter
    lateinit var tokenGenerate: HashMap<String, String>
    lateinit var interestUpdateAdapter: InterestAdapter
    var finalInterest = ""
    private  val prefarenceViewModel: PrefarenceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
         tokenGenerate = java.util.HashMap<String, String>()
        val userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData

        tvCityNameProfile.isSelected =true
        adapterInit()
        dataDet()
        ivProfileBack.setOnClickListener {
            onBackPressed()
        }
        tvEditProfile.setOnClickListener {
          startActivity(Intent(this@ProfileActivity,EditProfileActivity::class.java))
            viewModel.getDataClear()
        }
        tvAboutEdit.setOnClickListener {
            updateName("About", tokenGenerate)
        }
        tvNickNameUpdate.setOnClickListener {
            updateNickName("Nick Name",tokenGenerate)
        }
        lifecycleScope.launchWhenCreated {

        }
        lifecycleScope.launchWhenCreated {
            prefarenceViewModel.interestResponse.collectLatest {
                it?.let {
                    interestUpdateAdapter.updateList(it.results)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            prefarenceViewModel.interestAddResponse.collectLatest {
                it?.let {
                    viewModel.getProfileRequest(tokenGenerate,userId)
                    Toast.makeText(this@ProfileActivity,"Successfully update interest",Toast.LENGTH_SHORT).show()
                }
            }
        }

        ivInterestUpdate.setOnClickListener {
            interestUpdate()
        }
    }

    private fun dataDet() {
        lifecycleScope.launchWhenCreated {
            viewModel.profileResponse.collectLatest {
                it?.let {

                    cvDetailsProfile.load("http://167.99.32.192"+it.data.user.dp)
                    tvProfilePersonName.text=it.data.user.full_name
                    tvProfileEmail.text=it.data?.nickname
                    tvGenderProfile.text=it.data.gender
                    tvCityNameProfile.text=it.data.city
                    tvAboutProfile.text= it.data.about
                    tvEmail.text=it.data.user.email
                    tvMobileNumber.text=it.data.user.username
                    interestAdapter.interestUpdateList(it.data.interests)

                    tvOrganizationCount.text = it.extra_data.organized_activity_count.toString()
                    tvParticipateCount.text = it.extra_data.participated_activity_count.toString()
                    val age=it.data.date_of_birth
                    if (age!=null) {
                        val year = age.subSequence(0, 4).toString().toInt()
                        val month = age.subSequence(5, 7).toString().toInt()
                        val day = age.subSequence(8, 10).toString().toInt()
                        tvAge.text = ConstValue.getAge(year, month, day)
                    }
                }
            }
        }
    }
    lateinit var dialog: DialogPlus
    fun interestUpdate() {
        prefarenceViewModel.getInterestList(tokenGenerate)
        dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.bottom_sheet_interest))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialog.holderView
        view.tvUpdate.setOnClickListener {
            val interserAddRequest = InterserAddRequest(finalInterest)
            prefarenceViewModel.getAddInterest(tokenGenerate, userId,interserAddRequest)
            dialog.dismiss()
        }

        interestUpdateAdapter = InterestAdapter(this)
        view.rvInterestUpdate.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        view.rvInterestUpdate.adapter = interestUpdateAdapter
        dialog.show()


    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
    override fun onResume() {
        super.onResume()
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        val userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        viewModel.getProfileRequest(tokenGenerate,userId)
    }
    fun adapterInit(){
        interestAdapter= UserInterestAdapter()
        rvUserInterest.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvUserInterest.adapter = interestAdapter
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
                val aboutResponse = AboutResponse(dialog.etDialogData.text.toString())
                tvAboutProfile.text = dialog.etDialogData.text.toString()
                viewModel.getAboutUpdate(tokenGenerate,aboutResponse)
            }else{
                Toast.makeText(this,"Enter your about", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun updateNickName(name: String, tokenGenerate: HashMap<String, String>) {
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
                val aboutResponse = NickNameUpdate(dialog.etDialogData.text.toString())
                tvProfileEmail.text = dialog.etDialogData.text.toString()
                viewModel.getNickNameUpdate(tokenGenerate,aboutResponse)
            }else{
                Toast.makeText(this,"Enter your about", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.tvDialogCancel.setOnClickListener {
            dialog.dismiss()
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