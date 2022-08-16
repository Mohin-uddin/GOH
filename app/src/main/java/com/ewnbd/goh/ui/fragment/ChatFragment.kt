package com.ewnbd.goh.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.fragment.adapter.ChatAdapter
import com.ewnbd.goh.ui.fragment.viewmodel.ChatViewModel
import com.ewnbd.goh.ui.fragment.viewmodel.ProfileViewModel
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private  val viewModel: ChatViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference = context?.getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = java.util.HashMap<String, String>()
        ConstValue.userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        viewModel.getProfileRequest(tokenGenerate)
       var chatAdapter = ChatAdapter()
        rvChat.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        rvChat.adapter = chatAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.chatListResponse.collectLatest {
                it?.let {
                    chatAdapter.updateChatList(it.results)
                }
            }
        }
    }

}