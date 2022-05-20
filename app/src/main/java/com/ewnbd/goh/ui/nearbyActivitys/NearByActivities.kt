package com.ewnbd.goh.ui.nearbyActivitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.nearbyActivitys.adapter.NearActivitiesItemCategoryAdapter
import kotlinx.android.synthetic.main.activity_near_by_activities.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NearByActivities : AppCompatActivity() {
    private val viewModel: NearByActivitiesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_by_activities)

        viewModel.getNearActivitiesCategoryName()

        lifecycleScope.launch {
            viewModel.nearByActivitiesList.collect {
                rvCategoryList.layoutManager = LinearLayoutManager(this@NearByActivities,
                    LinearLayoutManager.HORIZONTAL,false)
                rvCategoryList.adapter = NearActivitiesItemCategoryAdapter(it)
            }
        }
        rvCategoryActivitiesList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvCategoryActivitiesList.adapter = NearActivitiesAdapter()
    }
}