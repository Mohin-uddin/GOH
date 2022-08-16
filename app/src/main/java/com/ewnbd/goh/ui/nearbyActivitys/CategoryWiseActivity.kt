package com.ewnbd.goh.ui.nearbyActivitys

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
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
import kotlinx.android.synthetic.main.activity_category_wise.*
import kotlinx.android.synthetic.main.activity_near_by_activities.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.HashMap

@AndroidEntryPoint
class CategoryWiseActivity : AppCompatActivity(),ActivityRequestSend,ActivityShare {
    private  val viewModel: NearByActivitiesViewModel by viewModels()
    private val nearActivityListViewModel: NearActivityListViewModel by viewModels()
    private  val viewModelDetails: ActivityDetailsViewModel by viewModels()
    lateinit var tokenGenerate : HashMap<String,String>
    private lateinit var dialouge: Dialog
    lateinit var nearByAdapter: NearActivitiesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_wise)
        nearByAdapter= NearActivitiesAdapter(this,this)
        rvCategoryWiseActivitiesList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvCategoryWiseActivitiesList.adapter = nearByAdapter
        tvCategoryName.text=ConstValue.categoryWisedata.name
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        dialouge= ConstValue.animation(this,0)
        viewModel.getNearActivitiesCategoryName(tokenGenerate,ConstValue.categoryWisedata.id.toString())
        lifecycleScope.launchWhenCreated {
            viewModel.categoryWiseNearByActivitiesList.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    nearByAdapter.updateNearList(it.results)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.responseCode.collectLatest {

                if (it!="0"&&it!="200"){
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(this@CategoryWiseActivity,"Server error $it",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            nearActivityListViewModel.activityShare.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(this@CategoryWiseActivity,it.success_msg,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getActivityId(activityId: String,state:String) {

        if (state=="1"){
            nearActivityListViewModel.getWithdrawRequest(tokenGenerate, activityId)
        }
        else if (state=="0"){
            viewModelDetails.getProfileRequest(tokenGenerate,activityId)
        }
    }

    override fun activityShareId(activity: String) {
        if (::dialouge.isInitialized)
            dialouge.show()
        nearActivityListViewModel.getActivityShare(tokenGenerate,activity)
    }
}