package com.ewnbd.goh.ui.organizationDetails

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.ui.createActivity.SelectCategoryname
import com.ewnbd.goh.ui.filter.FilterCategoryAdapter
import com.ewnbd.goh.ui.filter.SelectCategoryInterface
import com.ewnbd.goh.utils.DoesNetworkHaveInternet
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_organization_particepetor.*
import kotlinx.android.synthetic.main.bottom_sheet_category.view.*
import kotlinx.android.synthetic.main.bottom_sheet_organization.view.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class OrganizationParticepetorActivity : AppCompatActivity(),SelectCategoryname {
    lateinit var tokenGenerate: HashMap<String, String>
    private  val viewModel: ActivityDetailsViewModel by viewModels()
    lateinit var organizationParticipetorListAdapter: OrganizationParticipetorListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_particepetor)

        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        val userId = preference?.getString("userId","")?:""
        viewModel.getOrganizationParticepetor(tokenGenerate, DoesNetworkHaveInternet?.organization.id.toString())
        organizationParticipetorListAdapter = OrganizationParticipetorListAdapter(this)
        rvParticipetorListListShow.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvParticipetorListListShow.adapter=organizationParticipetorListAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.organizationParticepetorResponse.collectLatest {
                it?.let {
                    tvAllOrginzationParticeptorCounter.text="${it.results.size} Pepole joined"
                    organizationParticipetorListAdapter.updateRequestList(it.results)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.participator.collectLatest {
                it?.let {
                    viewModel.getOrganizationParticepetor(tokenGenerate, DoesNetworkHaveInternet?.organization.id.toString())
                    Toast.makeText(this@OrganizationParticepetorActivity,it.success_msg,Toast.LENGTH_SHORT).show()
                }
            }
        }

        ivBackOrganization.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun categoryName(name: String) {
        viewModel.getParticipatorCancel(tokenGenerate,name,DoesNetworkHaveInternet?.organization.id.toString())
    }


}