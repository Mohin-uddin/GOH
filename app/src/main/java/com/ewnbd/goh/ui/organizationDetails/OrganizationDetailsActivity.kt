package com.ewnbd.goh.ui.organizationDetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.databinding.ActivityOrganizationDetailsBinding
import com.ewnbd.goh.ui.activitiesDetails.AccptRequestSelect
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.ui.createActivity.CreateActivity
import com.ewnbd.goh.ui.friendProfile.FriendProfileActivity
import com.ewnbd.goh.ui.promotion.PromoteActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.DoesNetworkHaveInternet
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_organization_details.*
import kotlinx.android.synthetic.main.bottom_sheet_organization.view.*
import kotlinx.coroutines.flow.collectLatest
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.HashMap

@AndroidEntryPoint
class OrganizationDetailsActivity : AppCompatActivity(), AccptRequestSelect {
    lateinit var tokenGenerate: HashMap<String, String>
    lateinit var organizationAcceptingAdapter: OrganizationAcceptingAdapter
    private  val viewModel: ActivityDetailsViewModel by viewModels()
    lateinit var organizaionDetails : ActivityOrganizationDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        organizaionDetails = ActivityOrganizationDetailsBinding.inflate(layoutInflater)
        setContentView(organizaionDetails.root)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        dataSet()

        tvRequestViewAll.setOnClickListener {
            Log.e("checkClick", "onCreate: ")
            startActivity(Intent(this,OrganizationRequestActivty::class.java))
        }


        lifecycleScope.launchWhenCreated {
            viewModel.organizationRequestResponse.collectLatest {
                it?.let {
                    organizationAcceptingAdapter.updateRequestList(it.results)
                }
            }
        }
        organizaionDetails.tvAskTOJoin.setOnClickListener {
            viewModel.getActivityCancelRequest(tokenGenerate,DoesNetworkHaveInternet.activityId.toString())
        }
        lifecycleScope.launchWhenCreated {
            viewModel.activityCacnelResponse.collectLatest {
                it?.let {
                    if (it.success_msg!=""){
                        Toast.makeText(this@OrganizationDetailsActivity,it.success_msg,Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                if (it=="404"){
                    Toast.makeText(this@OrganizationDetailsActivity,"Join Request Not Found",Toast.LENGTH_SHORT).show()
                }
                else if (it!="0"){
                    Toast.makeText(this@OrganizationDetailsActivity,"Server $it",Toast.LENGTH_SHORT).show()
                }

            }
        }
        ivDetailsBackArrow.setOnClickListener {
            onBackPressed()
        }
        organizaionDetails.ivDot.setOnClickListener {
            updateTodo()
        }

        tvPromotion.setOnClickListener {
            ConstValue.activityId = DoesNetworkHaveInternet.activityId.toString()
            startActivity(Intent(this,PromoteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrganizationRequestListRequest(tokenGenerate,DoesNetworkHaveInternet.activityId.toString())
    }
    private fun dataSet() {
        tvSponsorPerson.setOnClickListener {
            ConstValue.userId=DoesNetworkHaveInternet.organization.creator.id.toString()
            startActivity(Intent(this,FriendProfileActivity::class.java))
        }
        if (::organizaionDetails.isInitialized){
            organizaionDetails.appCompatImageView.load(DoesNetworkHaveInternet?.organization?.photo)
            organizaionDetails.tvActivitiesName.text=DoesNetworkHaveInternet?.organization?.name
            organizaionDetails.tvSponsorPerson.text = DoesNetworkHaveInternet?.organization?.creator?.email
            organizaionDetails.tvAboutDetails.text = DoesNetworkHaveInternet?.organization?.desc
            organizaionDetails.tvJoinPersonCount.text = DoesNetworkHaveInternet?.organization?.min_participate?.toString()+" persons"
            organizaionDetails.tvActivityStartDate.text = DoesNetworkHaveInternet?.organization?.activity_date+" "+ Convert24to12(DoesNetworkHaveInternet?.organization?.start_time)+"-"+Convert24to12(DoesNetworkHaveInternet?.organization?.end_time)
            organizaionDetails.tvGenderActivity.text = DoesNetworkHaveInternet?.organization?.gender
            organizationAcceptingAdapter = OrganizationAcceptingAdapter(this)
            organizaionDetails.rvRequestList.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false)
            organizaionDetails.rvRequestList.adapter=organizationAcceptingAdapter
        }
        lifecycleScope.launchWhenCreated {
            viewModel.activityAcceptResponse.collectLatest {
                it?.let {
                    Toast.makeText(this@OrganizationDetailsActivity, "Successfully Accept Request", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun selectActivityRequest(userId: String) {
        viewModel.getActivityRequestAccept(tokenGenerate, DoesNetworkHaveInternet?.organization?.id.toString(),userId)
    }

    override fun cancelActivityRequest(userId: String) {
        viewModel.getActivityRequestCancel(tokenGenerate, DoesNetworkHaveInternet?.organization?.id.toString(),userId)
    }

    fun updateTodo() {

        val dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.bottom_sheet_organization))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialog.holderView

        view.tvViewParticipate.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this,OrganizationParticepetorActivity::class.java))

        }
        view.tvUpdateActivity.setOnClickListener {
            ConstValue.editeState=1
            ConstValue.activityId = DoesNetworkHaveInternet?.organization.id.toString()
            startActivity(Intent(this, CreateActivity::class.java))
            dialog.dismiss()
        }
        dialog.show()

    }

    fun Convert24to12(time: String?): String? {
        var convertedTime = ""
        try {
            val displayFormat = SimpleDateFormat("hh:mm a")
            val parseFormat = SimpleDateFormat("HH:mm:ss")
            val date: java.util.Date? = parseFormat.parse(time)
            convertedTime = displayFormat.format(date)
            println("convertedTime : $convertedTime")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertedTime
        //Output will be 10:23 PM
    }
}