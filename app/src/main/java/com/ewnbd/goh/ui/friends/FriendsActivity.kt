package com.ewnbd.goh.ui.friends

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.fragment.adapter.OrganizationActivityAdapter
import com.ewnbd.goh.ui.fragment.adapter.UserInterestAdapter
import com.ewnbd.goh.ui.friendProfile.FriendProfileViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class FriendsActivity : AppCompatActivity() , FriendAcceptRemove{
    private  val viewModel: FriendProfileViewmodel by viewModels()
    lateinit var friendRequestAdapter: FriendRequestAdapter
    lateinit var tokenGenerate : HashMap<String,String>
    lateinit var friendListAdapter: FriendListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
         tokenGenerate = HashMap<String,String>()
        val userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        viewModel.getFriendRequestList(tokenGenerate)
        viewModel.getFriendList(tokenGenerate,userId)
        friendListAdapter= FriendListAdapter()
        friendRequestAdapter = FriendRequestAdapter(this)
        rvFriend.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)
        rvFriend.adapter = friendListAdapter

        rvRequestFriends.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvRequestFriends.adapter = friendRequestAdapter
        tvViewAllFriend.setOnClickListener {
            startActivity(Intent(this,AllFriendListActivity::class.java))
        }

        lifecycleScope.launchWhenCreated {
            viewModel.friendRequestList.collectLatest {
                it?.let {
                    friendRequestAdapter.interestUpdateList(it.results)
                    tvFriendCounter.text= "${it.results.size} Friend requests"
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.friendListResponse.collectLatest {
                it?.let {
                    friendListAdapter.interestUpdateList(it.results)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.friendRequestRemove.collectLatest {
                it?.let {
                    viewModel.getFriendRequestList(tokenGenerate)
                    Toast.makeText(this@FriendsActivity,"Successfully remove friend request",Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.friendRequestAccept.collectLatest {
                it?.let {
                    viewModel.getFriendRequestList(tokenGenerate)
                    viewModel.getFriendList(tokenGenerate,userId)
                }
            }
        }
    }

    override fun acceptFriend(userId: String) {
        viewModel.friendRequestAccept(tokenGenerate,userId)
    }

    override fun removeFriend(userId: String) {
        viewModel.friendRequestRemove(tokenGenerate,userId)
    }
}