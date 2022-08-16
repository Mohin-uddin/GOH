package com.ewnbd.goh.ui.filter

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.ui.fragment.ActivityRequestSend
import com.ewnbd.goh.ui.fragment.ActivityShare
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_filter_result.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class FilterResultActivity : AppCompatActivity(),ActivityRequestSend,ActivityShare{
    lateinit var nearByAdapter: NearActivitiesAdapter
    lateinit var tokenGenerate : HashMap<String,String>
    private lateinit var dialouge: Dialog
    private val nearActivityListViewModel: NearActivityListViewModel by viewModels()
    private  val viewModelDetails: ActivityDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_result)
       // dialouge=  ConstValue.animation(this,0)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        nearByAdapter = NearActivitiesAdapter(this,this)
        if(ConstValue.filterResult.results.isEmpty()){
            Toast.makeText(this,"Data Not Available",Toast.LENGTH_SHORT).show()
        }else{
            Log.e("FilterData", "onCreate: ${ConstValue.filterResult.results.size}", )
            rvFilterResult.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)
            rvFilterResult.adapter = nearByAdapter
            nearByAdapter.updateNearList(ConstValue.filterResult.results)
        }

        ivNearByBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            nearActivityListViewModel.activityShare.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(this@FilterResultActivity,it.success_msg,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    override fun getActivityId(activityId: String,state: String) {
        if (state=="1"){
            nearActivityListViewModel.getWithdrawRequest(tokenGenerate,activityId)
        } else if (state=="0"){
            viewModelDetails.getProfileRequest(tokenGenerate,activityId)
        }
    }

    override fun activityShareId(activity: String) {
        if (::dialouge.isInitialized)
            dialouge.show()
        nearActivityListViewModel.getActivityShare(tokenGenerate,activity)
    }
}