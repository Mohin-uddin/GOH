package com.ewnbd.goh.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import kotlinx.android.synthetic.main.fragment_activities.*
import kotlinx.android.synthetic.main.fragment_explore.*


class ActivitiesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvDifferentTypeOfActivity.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        rvDifferentTypeOfActivity.adapter = NearActivitiesAdapter()
    }

}