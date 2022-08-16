package com.ewnbd.goh.ui.nearbyActivitys

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.ui.filter.FilterActivity
import com.ewnbd.goh.ui.fragment.ActivityRequestSend
import com.ewnbd.goh.ui.fragment.ActivityShare
import com.ewnbd.goh.ui.fragment.adapter.CategoriesAdapter
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.ui.login.LoginActivity
import com.ewnbd.goh.ui.nearbyActivitys.adapter.NearActivitiesItemCategoryAdapter
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_near_by_activities.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.HashMap
@AndroidEntryPoint
class NearByActivities : AppCompatActivity(),CategorySelection,ActivityRequestSend ,ActivityShare{
    lateinit var categoriesAdapter: NearActivitiesItemCategoryAdapter
    lateinit var nearByAdapter: NearActivitiesAdapter
    private  val viewModel: NearActivityListViewModel by viewModels()
    private  val viewModelDetails: ActivityDetailsViewModel by viewModels()
    private lateinit var dialouge: Dialog
    lateinit var tokenGenerate : HashMap<String,String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_by_activities)

        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        dialouge= ConstValue.animation(this,0)
        viewModel.getNearByRequest(tokenGenerate,"23.82231","90.38577")
        viewModel.getCategoryByRequest(tokenGenerate)
        nearByAdapter= NearActivitiesAdapter(this,this)
        categoriesAdapter=NearActivitiesItemCategoryAdapter(this)
        rvCategoryActivitiesList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvCategoryActivitiesList.adapter = nearByAdapter

        rvCategoryList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvCategoryList.adapter = categoriesAdapter

        ivFilter.setOnClickListener {
            startActivity(Intent(this,FilterActivity::class.java))
        }

        lifecycleScope.launchWhenCreated {
            val categoryList = ArrayList<NearActivitiesCategoryListName>()
            viewModel.categoryResponse.collectLatest {
                it?.let {
                    if (::categoriesAdapter.isInitialized){
                        categoryList.clear()
                        categoryList.add(NearActivitiesCategoryListName("All",0))
                        for (item in it.results){
                             categoryList.add(NearActivitiesCategoryListName(item.cat_name,item.id))
                        }
                        categoriesAdapter.categoryUpdateList(categoryList)
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.nearByActivityResponse.collectLatest {

                it?.let {
                    if (it.results.isEmpty()){
                        Toast.makeText(this@NearByActivities,"Near by activity not available for this latitude and longitude", Toast.LENGTH_SHORT).show()
                    }
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    if (::nearByAdapter.isInitialized) {
                        nearByAdapter.updateNearList(it.results)
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    if (it=="401"){

                        val preference =getSharedPreferences("GOH", Context.MODE_PRIVATE)
                        val editor = preference?.edit()
                        editor?.putBoolean("isLoggedIn", false)
                        editor?.apply()
                        startActivity(Intent(this@NearByActivities, LoginActivity::class.java))
                    }else if (it!="200"&&it!="0"){
                        Toast.makeText(this@NearByActivities,"Server error $it",Toast.LENGTH_SHORT)
                            .show()
                    }
                    viewModel.getDataClear()
                }
            }
        }

        ivNearByBackBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            viewModel.activityShare.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(this@NearByActivities,it.success_msg,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun selectPosition(pos: NearActivitiesCategoryListName) {
       ConstValue.categoryWisedata = pos
        startActivity(Intent(this@NearByActivities,CategoryWiseActivity::class.java))
    }

    override fun getActivityId(activityId: String,state:String) {

        if (state=="1")
        viewModel.getWithdrawRequest(tokenGenerate,activityId)
        else if (state=="0"){
            viewModelDetails.getProfileRequest(tokenGenerate,activityId)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun activityShareId(activity: String) {
        if (::dialouge.isInitialized)
            dialouge.show()
        viewModel.getActivityShare(tokenGenerate,activity)
    }
}