package com.ewnbd.goh.ui.organizationDetails

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.activitiesDetails.AccptRequestSelect
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.utils.DoesNetworkHaveInternet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_organization_request_activty.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class OrganizationRequestActivty : AppCompatActivity(), AccptRequestSelect {
    lateinit var tokenGenerate: HashMap<String, String>
    lateinit var organizationRequestListAdapter: OrganizationRequestListAdapter
    private val viewModel: ActivityDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_request_activty)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData = preference?.getString("token", "") ?: ""
        tokenGenerate = HashMap<String, String>()
        tokenGenerate["Authorization"] = tokenData
        viewModel.getOrganizationRequestListRequest(
            tokenGenerate,
            DoesNetworkHaveInternet.activityId.toString()
        )
        organizationRequestListAdapter = OrganizationRequestListAdapter(this)
        rvRequestListListShow.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rvRequestListListShow.adapter = organizationRequestListAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.organizationRequestResponse.collectLatest {
                it?.let {
                    tvAllOrginzationRequestCounter.text = "${it.results.size} Join requested"
                    organizationRequestListAdapter.updateRequestList(it.results)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.activityRequestCacnelResponse.collectLatest {
                it?.let {
                     if (it.success_msg.contains("Join Request was declined")){
                         viewModel.getOrganizationRequestListRequest(
                             tokenGenerate,
                             DoesNetworkHaveInternet.activityId.toString()
                         )
                     }
                }

            }
        }
    }

    override fun selectActivityRequest(userId: String) {
        viewModel.getOrganizationRequestListRequest(
            tokenGenerate,
            DoesNetworkHaveInternet.activityId.toString()
        )
        viewModel.getActivityRequestAccept(
            tokenGenerate,
            DoesNetworkHaveInternet.organization.id.toString(),
            userId
        )
    }

    override fun cancelActivityRequest(userId: String) {
        viewModel.getActivityRequestCancel(
            tokenGenerate,
            DoesNetworkHaveInternet.organization.id.toString(),
            userId
        )
    }
}