package com.ewnbd.goh.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.fragment.adapter.OrganizationActivityAdapter
import com.ewnbd.goh.ui.fragment.viewmodel.ActivityOrganizationAndPartiViewmodel
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.utils.ConstValue.userId
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_prefarence.*
import kotlinx.android.synthetic.main.fragment_activities.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.HashMap

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {
    private  val viewModel: ActivityOrganizationAndPartiViewmodel by viewModels()
    lateinit var organizationActivityAdapter: OrganizationActivityAdapter
    lateinit var tokenGenerate : HashMap<String,String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        organizationActivityAdapter= OrganizationActivityAdapter()

        val preference = context?.getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        rvDifferentTypeOfActivity.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        rvDifferentTypeOfActivity.adapter = organizationActivityAdapter

        tvOrganization.setOnClickListener {
            tvOrganization.setBackgroundResource(R.drawable.curved_shape_green)
            tvParticipated.setBackgroundResource(R.drawable.curved_shape_white)
            tvOrganization.setTextColor(resources.getColor(R.color.white))
            tvParticipated.setTextColor(resources.getColor(R.color.black))
            viewModel.getOrganizationRequest(tokenGenerate,userId)
            if (viewModel.organizationActivityResponse.value!=null){
                organizationActivityAdapter.interestUpdateList(viewModel.organizationActivityResponse.value!!.results,0)
            }
        }
        tvParticipated.setOnClickListener {
            tvParticipated.setBackgroundResource(R.drawable.curved_shape_green)
            tvOrganization.setBackgroundResource(R.drawable.curved_shape_white)
            tvParticipated.setTextColor(resources.getColor(R.color.white))
            tvOrganization.setTextColor(resources.getColor(R.color.black))
            viewModel.getParticipatedByRequest(tokenGenerate,userId)
            if (viewModel.participatedActivityResponse.value!=null){
                organizationActivityAdapter.interestUpdateList(viewModel.participatedActivityResponse.value!!.results,1)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.organizationActivityResponse.collectLatest {
                it?.let {
                    organizationActivityAdapter.interestUpdateList(it.results,0)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.participatedActivityResponse.collectLatest {
                it?.let {
                    organizationActivityAdapter.interestUpdateList(it.results,1)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.responseCode.collectLatest {
                if (it!="0"&&it!="200"){
                    Toast.makeText(context,"Server error $it",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrganizationRequest(tokenGenerate,userId)
    }

    override fun onPause() {
        super.onPause()
    }

}