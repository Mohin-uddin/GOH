package com.ewnbd.goh.ui.notification

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.activitiesDetails.AccptRequestSelect
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.ui.friendProfile.FriendProfileViewmodel
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.DoesNetworkHaveInternet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class NotificationActivity : AppCompatActivity(), AccptRequestSelect {
    private  val viewModel: NotificationViewModel by viewModels()
    private  val friendsViewmodel: FriendProfileViewmodel by viewModels()
    lateinit var tokenGenerate: HashMap<String, String>
    private  val actvityDetailsViewmodel: ActivityDetailsViewModel by viewModels()
    private lateinit var dialouge: Dialog
    private var next_url=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        dialouge= ConstValue.animation(this,0)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = java.util.HashMap<String, String>()
        tokenGenerate["Authorization"] = tokenData
        viewModel.getNotification(tokenGenerate)
        val notificationAdapter = NotificationAdapter(this)
        rvNotification.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvNotification.adapter = notificationAdapter

        rvNotification.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = getCurrentItem()
                    Log.e("checkData", "onScrollStateChanged: $position", )
                    if (position%10==1){
                        viewModel.getNotificationNext(tokenGenerate,next_url)
                    }
                }
            }
        })
        ivNotificationBack.setOnClickListener {
            onBackPressed()
        }
        lifecycleScope.launchWhenCreated {
            viewModel.notificationResponse.collectLatest {
                it?.let {
                    next_url= it.next
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    notificationAdapter.updateList(it.results)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (it!="0"){
                        if (::dialouge.isInitialized)
                            dialouge.dismiss()
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            friendsViewmodel.friendRequestRemove.collectLatest {
                it?.let {
                   // viewModel.getFriendRequestList(tokenGenerate)
                    Toast.makeText(this@NotificationActivity,"Successfully remove friend request", Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            friendsViewmodel.friendRequestAccept.collectLatest {
                it?.let {
                    Toast.makeText(this@NotificationActivity,"Successfully friend Added", Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            actvityDetailsViewmodel.activityAcceptResponse.collectLatest {
                it?.let {
                    Toast.makeText(this@NotificationActivity, "Successfully Accept Request", Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            actvityDetailsViewmodel.activityCacnelResponse.collectLatest {
                it?.let {
                    Toast.makeText(this@NotificationActivity, "Successfully Cancel Request", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun getCurrentItem(): Int {
        return (rvNotification.layoutManager as LinearLayoutManager)
            .findFirstVisibleItemPosition()
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun selectActivityRequest(userId: String) {
        if (ConstValue.requestType==11){
            actvityDetailsViewmodel.getActivityRequestAccept(tokenGenerate,  ConstValue.activityId,userId)
        }else{
            friendsViewmodel.friendRequestAccept(tokenGenerate,userId)
        }
    }

    override fun cancelActivityRequest(userId: String) {
        if (ConstValue.requestType==11){
            actvityDetailsViewmodel.getActivityRequestCancel(tokenGenerate, ConstValue.activityId,userId)
        }else{
            friendsViewmodel.friendRequestRemove(tokenGenerate,userId)
        }

    }
}