package com.ewnbd.goh.ui.friendProfile

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
import com.ewnbd.goh.data.model.response_all_class.SelectChatDetailsData
import com.ewnbd.goh.databinding.ActivityFriendProfileBinding
import com.ewnbd.goh.databinding.ActivitySplashScreanBinding
import com.ewnbd.goh.ui.chatDetails.ChatDetailsActivity
import com.ewnbd.goh.ui.createActivity.SelectCategoryname
import com.ewnbd.goh.ui.createActivity.adapter.CategorySelectAdapter
import com.ewnbd.goh.ui.fragment.adapter.OrganizationActivityAdapter
import com.ewnbd.goh.ui.fragment.adapter.UserInterestAdapter
import com.ewnbd.goh.ui.fragment.viewmodel.ProfileViewModel
import com.ewnbd.goh.ui.friends.AllFriendListAdapter
import com.ewnbd.goh.utils.ConstValue
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_all_friend_list.*
import kotlinx.android.synthetic.main.activity_friend_profile.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.bottom_category_select.view.*
import kotlinx.android.synthetic.main.bottom_friend_share.view.*
import kotlinx.android.synthetic.main.fragment_activities.*
import kotlinx.android.synthetic.main.fragment_activities.tvOrganization
import kotlinx.android.synthetic.main.fragment_activities.tvParticipated
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.HashMap

@AndroidEntryPoint
class FriendProfileActivity : AppCompatActivity(), SelectCategoryname {
    var name=""
    var profile =""
    var nickname=""
    private var state: Int=0
    lateinit var binding : ActivityFriendProfileBinding
    lateinit var selectChatDetailsData: SelectChatDetailsData
    private  val viewModel: FriendProfileViewmodel by viewModels()
    lateinit var interestAdapter: UserInterestAdapter
    lateinit var tokenGenerate : HashMap<String,String>
    lateinit var friendListAdapter: FriendShareAdapter
    var userId =""
    lateinit var organizationActivityAdapter: OrganizationActivityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dataDet()

        binding.tvCityNameProfile.isSelected=true
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData


        viewModel.getProfileRequest(tokenGenerate,ConstValue.userId)

        interestAdapter= UserInterestAdapter()
        organizationActivityAdapter= OrganizationActivityAdapter()
        binding.rvDifferentTypeOfActivity.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        binding.rvDifferentTypeOfActivity.adapter = organizationActivityAdapter
        viewModel.getOrganizationRequest(tokenGenerate,ConstValue.userId)
        ConstValue.organizationState = 4
        tvOrganization.setOnClickListener {
            ConstValue.organizationState = 4
            binding.tvOrganization.setBackgroundResource(R.drawable.curved_shape_green)
            binding.tvParticipated.setBackgroundResource(R.drawable.curved_shape_white)
            binding.tvOrganization.setTextColor(resources.getColor(R.color.white))
            binding.tvParticipated.setTextColor(resources.getColor(R.color.black))
            viewModel.getOrganizationRequest(tokenGenerate,ConstValue.userId)
            if (viewModel.organizationActivityResponse.value!=null){
                organizationActivityAdapter.interestUpdateList(viewModel.organizationActivityResponse.value!!.results,0)
            }
        }
        tvParticipated.setOnClickListener {
            ConstValue.organizationState = 4
            binding.tvParticipated.setBackgroundResource(R.drawable.curved_shape_green)
            binding.tvOrganization.setBackgroundResource(R.drawable.curved_shape_white)
            binding.tvParticipated.setTextColor(resources.getColor(R.color.white))
            binding.tvOrganization.setTextColor(resources.getColor(R.color.black))
            viewModel.getParticipatedByRequest(tokenGenerate,ConstValue.userId)
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

            }
        }

        binding.tvAcceptFriendRequest.setOnClickListener {
            if (binding.tvAcceptFriendRequest.text.toString()=="Add Friend"){
                state=1
                viewModel.sentProfileRequest(tokenGenerate,ConstValue.userId)
            } else {
                if (binding.tvAcceptFriendRequest.text.toString()=="Message"){
                    if (::selectChatDetailsData.isInitialized) {
                        ConstValue.chatId = ConstValue.userId
                        startActivity(Intent(this, ChatDetailsActivity::class.java))
                    }else{
                        Toast.makeText(this@FriendProfileActivity,"Please wait for user data", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.sentRequestResponse.collectLatest {
               it?.let {
                   if(state==1){
                       binding.tvAcceptFriendRequest.text="Cancel Request"
                       Toast.makeText(this@FriendProfileActivity,"Successfully send request",Toast.LENGTH_SHORT).show()
                   }
               }
            }
        }
        binding.ivProfileBack.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.friendListResponse.collectLatest {
                it?.let {
                    friendListAdapter.friendListUpdateList(it.results)
                }
            }
        }
        tvIgnore.setOnClickListener {
            friendList()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun dataDet() {
        lifecycleScope.launchWhenCreated {
            viewModel.profileResponse.collectLatest {
                it?.let {
                    if(it.data.status==0){
                        binding.tvAcceptFriendRequest.text="Add Friend"
                    }else if(it.data.status==1){
                        binding.tvAcceptFriendRequest.text="Cancel Request"
                    }else{
                        binding.tvAcceptFriendRequest.text = "Message"
                        binding.tvIgnore.text = "Share"
                    }
                    selectChatDetailsData= SelectChatDetailsData(it.data.user.full_name,it.data.user.username,"http://167.99.32.192"+it.data.user.dp)
                    ConstValue.lastSms=selectChatDetailsData
                    name=it.data.user.full_name
                    profile = it.data.user.dp
                    binding.cvDetailsProfile.load(it.data.user.dp)
                    nickname=it.data.nickname
                    binding.tvProfilePersonName.text=it.data.user.full_name
                    binding.tvProfileEmail.text=it.data.nickname
                    binding.tvGenderProfile.text=it.data.gender
                    binding.tvCityNameProfile.text=it.data.city
                    binding.tvAboutProfile.text= it.data.about
                    binding.tvEmail.text=it.data.user.email
                    binding.tvMobileNumber.text=it.data.user.username
                    interestAdapter.interestUpdateList(it.data.interests)

                    binding.tvOrganizationCount.text = it.extra_data.organized_activity_count.toString()
                    binding.tvParticipateCount.text = it.extra_data.participated_activity_count.toString()
                    val age=it.data.date_of_birth
                    Log.e("AgeCheck", "dataDet: $age")
                    if (age!=null) {
                        val year = age.subSequence(0, 4).toString().toInt()
                        val month = age.subSequence(5, 7).toString().toInt()
                        val day = age.subSequence(8, 10).toString().toInt()
                        binding.tvAge.text = ConstValue.getAge(year, month, day)
                    }
                    else{
                        binding.tvAge.text="0"
                    }

                }
            }
        }
    }

    lateinit var dialogCC: DialogPlus


    fun friendList() {
        viewModel.getFriendList(tokenGenerate,userId)
        dialogCC = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.bottom_friend_share))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialogCC.holderView
//        view.tvDone.setOnClickListener {
//            dialogCC.dismiss()
//        }
        view.tvName.text = name
        view.civChatPerson.load(profile)
        view.tvDetailsRatingPerson.text = nickname
        friendListAdapter = FriendShareAdapter(this)
        view.rvFriendList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        view.rvFriendList.adapter = friendListAdapter
        dialogCC.show()


    }

    override fun categoryName(name: String) {

    }
}