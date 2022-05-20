package com.ewnbd.goh.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.fragment.adapter.CategoriesAdapter
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.nearbyActivitys.NearByActivities
import kotlinx.android.synthetic.main.fragment_explore.*


class ExploreFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvCategories.layoutManager = GridLayoutManager(context, 2)
        rvCategories.adapter = CategoriesAdapter()

        rvNearActivities.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        rvNearActivities.adapter = NearActivitiesAdapter()

        tvNearActivitiesViewAll.setOnClickListener {
            startActivity(Intent(context, NearByActivities::class.java))
        }
    }
}