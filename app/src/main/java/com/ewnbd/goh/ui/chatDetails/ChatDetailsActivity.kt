package com.ewnbd.goh.ui.chatDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.ChatDetailsModel
import com.ewnbd.goh.data.model.ChatSenderModel
import com.ewnbd.goh.ui.fragment.viewmodel.ChatViewModel
import com.ewnbd.goh.ui.fragment.viewmodel.ProfileViewModel
import com.ewnbd.goh.ui.friendProfile.FriendProfileViewmodel
import com.ewnbd.goh.ui.organizationDetails.OrganizationParticepetorActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import com.google.gson.Gson
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.bottom_block_design.view.*
import kotlinx.android.synthetic.main.bottom_sheet_organization.view.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.*
import okio.ByteString
import java.util.*

@AndroidEntryPoint
class ChatDetailsActivity : AppCompatActivity(),ChatDetailsDatGet {
    lateinit var webSocket: WebSocket
    var userId =""
    var username =""
    private  val profileViewModel: ProfileViewModel by viewModels()
    public lateinit var tokenGenerate: HashMap<String, String>
    public  val viewModel: ChatViewModel by viewModels()
    public val friendViewmodel : FriendProfileViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_details)
        if (ConstValue.blockStatus){
            llSmsSend.gone()
        }else{
            llSmsSend.show()
        }
        civChatPerson.load(ConstValue.lastSms?.img)
        tvName.text = ConstValue.lastSms?.fullName
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = java.util.HashMap<String, String>()
         userId = preference?.getString("userId","")?:""
        tokenGenerate["Authorization"] = tokenData
        profileViewModel.getProfileRequest(tokenGenerate,userId)
        viewModel.getChatDetails(tokenGenerate,ConstValue.chatId)
        start()
        var chatDetailsList = ArrayList<ChatDetailsModel>()




        var chatDetailsAdapter =ChatDetailsAdapter()
        val layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvChatDetails.layoutManager = layoutManager
        rvChatDetails.adapter = chatDetailsAdapter



// this might be helpful

        lifecycleScope.launchWhenCreated {
            profileViewModel.profileResponse.collectLatest {
                if(it!=null) {
                    username = it.data.user.username
                }
            }
        }



        ivChatBack.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.chatDetailsResponse.collectLatest { chatDetails ->
                chatDetails?.let {
                    chatDetailsList.clear()
                    for (item in chatDetails.results){
                        var shareLink=""
                        var textdata =""
                        if (item.text!=null){
                            textdata = item.text
                        }
                        if (item.sender == userId.toInt()){
                            if (item.shared_profile_link!=null)
                                shareLink = item.shared_profile_link
                            chatDetailsList.add(ChatDetailsModel(textdata,"",item.sent,shareLink))
                        }else{
                            if (item.shared_profile_link!=null)
                                shareLink = item.shared_profile_link
                            chatDetailsList.add(ChatDetailsModel("",textdata,item.sent,shareLink))
                        }
                    }
                    Log.e("chatDataList", "onCreate: ${chatDetailsList.size}" )
                    chatDetailsList.reverse()
                    chatDetailsAdapter.updateChat(chatDetailsList)
                    rvChatDetails.scrollToPosition(chatDetailsList.size-1)
                }
            }
        }

        ivSend.setOnClickListener {
            if (etSendSms.text.toString().isNotEmpty()){
                val chatSenderModel = ChatSenderModel(etSendSms.text.toString(), username,ConstValue.lastSms?.username?:"")
                val gson = Gson()
                val json: String = gson.toJson(chatSenderModel)
                Log.e("chatDetails", "onCreate: ${json}")
                webSocket.send(json)
                chatDetailsList.add(ChatDetailsModel(etSendSms.text.toString(),"","",""))
                chatDetailsAdapter.updateChat(chatDetailsList)
                rvChatDetails.scrollToPosition(chatDetailsList.size-1)
                etSendSms.setText("")
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.chatBlock.collectLatest {
                it?.let {
                    Toast.makeText(this@ChatDetailsActivity,it.success_msg,Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            friendViewmodel.friendRequestRemove.collectLatest {
                it?.let {
                    Toast.makeText(this@ChatDetailsActivity , it.msg,Toast.LENGTH_SHORT).show()
                }
            }
        }

        ivDotC.setOnClickListener {
            updateTodo()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private var mClient: OkHttpClient? = null
    private fun start() {
        mClient = OkHttpClient()
        val request = Request.Builder().url("ws://167.99.32.192:8000/chat_between/$userId/${ConstValue.chatId}/").build()
        val listener = EchoWebSocketListener(this)
         webSocket = mClient!!.newWebSocket(request, listener)
        mClient!!.dispatcher.executorService.shutdown()
    }
    inner class EchoWebSocketListener(val chatDetailsDatGet: ChatDetailsDatGet) : WebSocketListener() {

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.e("SocketData", "onClosed: ${webSocket.send("hellow")}" )

        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.e("SocketData", "onClosing: chat ${webSocket.send("hellow")}" )
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("SocketData", "fail: ${webSocket.request()} $t}" )
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            chatDetailsDatGet.getChatData(text)
            Log.e("SocketData", "onMessage: $text" )
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.e("SocketData", "onMessage byte: $bytes" )
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.e("SocketData", "open  chat asdasdasd : ${response.body}" )
        }
    }

    override fun getChatData(chat: String) {
        viewModel.getChatDetails(tokenGenerate,ConstValue.chatId)
    }

    fun updateTodo() {

        val dialog = DialogPlus.newDialog(this)
            .setContentHolder(ViewHolder(R.layout.bottom_block_design))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()

        val view: View = dialog.holderView
        view.tvFriendJoinDate.text = ConstValue.lastSms?.username
        view.civChatPerson.load(ConstValue.lastSms?.img)
        view.tvName.text = ConstValue.lastSms?.fullName
        if (ConstValue.blockStatus){
            view.tvSmsEnd.text= "UnBlock "+ConstValue.lastSms?.fullName
            view.tvSmsEnd.setTextColor(resources.getColor(R.color.black))
            view.ivBlock.load(R.drawable.unlocked)
        }else{
            view.tvSmsEnd.text= "Block "+ ConstValue.lastSms?.fullName
            view.tvSmsEnd.setTextColor(resources.getColor(R.color.red))
            view.ivBlock.load(R.drawable.ic_block)
        }
        view.llBlock.setOnClickListener {
            dialog.dismiss()
            viewModel.getChatBlock(tokenGenerate,ConstValue.chatId)

        }
        view.llUnfriend.setOnClickListener {

            dialog.dismiss()
            friendViewmodel.friendRemove(tokenGenerate,ConstValue.chatId)
        }
        dialog.show()

    }
}