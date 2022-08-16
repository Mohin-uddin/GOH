package com.ewnbd.goh.ui.friends

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.friendProfile.FriendProfileViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_all_friend_list.*
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class AllFriendListActivity  : AppCompatActivity(),FriendAcceptRemove {
    private  val viewModel: FriendProfileViewmodel by viewModels()
    lateinit var friendListAdapter: AllFriendListAdapter
    lateinit var tokenGenerate : HashMap<String,String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_friend_list)

        friendListAdapter =  AllFriendListAdapter(this)
        rvFriendListShow.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvFriendListShow.adapter =friendListAdapter
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
         tokenGenerate = HashMap<String,String>()
        val userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        viewModel.getFriendList(tokenGenerate,userId)

        lifecycleScope.launchWhenCreated {
            viewModel.friendListResponse.collectLatest {
                it?.let {
                  friendListAdapter.interestUpdateList(it.results)
                    tvAllFriendCounter.text= "${it.results.size} Friends"
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.friendRequestRemove.collectLatest {
                it?.let {
                    viewModel.getFriendList(tokenGenerate,userId)
                }
            }
        }
        ivBackFriend.setOnClickListener {
            onBackPressed()
        }
    }

    override fun acceptFriend(userId: String) {
        TODO("Not yet implemented")
    }

    override fun removeFriend(userId: String) {
         viewModel.friendRemove(tokenGenerate,userId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}