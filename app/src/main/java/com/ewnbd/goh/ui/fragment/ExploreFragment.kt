package com.ewnbd.goh.ui.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.activitiesDetails.ActivityDetailsViewModel
import com.ewnbd.goh.ui.chatDetails.ChatDetailsDatGet
import com.ewnbd.goh.ui.fragment.adapter.CategoriesAdapter
import com.ewnbd.goh.ui.fragment.adapter.NearActivitiesAdapter
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.ui.login.LoginActivity
import com.ewnbd.goh.ui.nearbyActivitys.NearByActivities
import com.ewnbd.goh.ui.notification.NotificationActivity
import com.ewnbd.goh.ui.signup.SignUpViewModel
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.coroutines.flow.collectLatest
import okhttp3.*
import okio.ByteString
import java.util.HashMap

@AndroidEntryPoint
class ExploreFragment : Fragment(),ActivityRequestSend,ActivityShare {

    private  val viewModel: NearActivityListViewModel by viewModels()
    private  val viewModelDetails: ActivityDetailsViewModel by viewModels()
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var tokenGenerate : HashMap<String,String>
    private lateinit var dialouge: Dialog
    lateinit var nearByAdapter: NearActivitiesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference = context?.getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        dialouge= context?.let { ConstValue.animation(it,0) }!!

        otherFuntionality()
        lifecycleScope.launchWhenCreated {
            viewModel.categoryResponse.collectLatest {
                it?.let {
                    if (::categoriesAdapter.isInitialized){
                        categoriesAdapter.categoryUpdateList(it.results)
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.nearByActivityResponse.collectLatest {
                it?.let {
                    if (it.results.isEmpty()){
                        Toast.makeText(context,"Near by activity not available for this latitude and longitude", Toast.LENGTH_SHORT).show()
                    }
                    dialouge.dismiss()
                    if (::nearByAdapter.isInitialized)
                    nearByAdapter.updateNearList(it.results)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                   if (it=="401"){
                       if (::dialouge.isInitialized)
                           dialouge.dismiss()
                       val preference = context?.getSharedPreferences("GOH", Context.MODE_PRIVATE)
                       val editor = preference?.edit()
                       editor?.putBoolean("isLoggedIn", false)
                       editor?.apply()
                       startActivity(Intent(context, LoginActivity::class.java))
                   }
                    viewModel.getDataClear()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.notificationData.collectLatest {
                it.let {
                    if (it!=0){

                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.activityShare.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(context,it.success_msg,Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.notificationUpdate.collectLatest {
                it.let {
                    if (it!=""){
                       var value = tvNotifcatiNumber.text.toString().toInt()
                        value+=1
                        tvNotifcatiNumber.text = value.toString()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        start()
        viewModel.getNearByRequest(tokenGenerate,"23.82231","90.38577")
        viewModel.getCategoryByRequest(tokenGenerate)
    }

    private fun otherFuntionality() {
        categoriesAdapter = CategoriesAdapter()
        rvCategories.layoutManager = GridLayoutManager(context, 2)
        rvCategories.adapter = categoriesAdapter
        nearByAdapter=NearActivitiesAdapter(this,this)
        rvNearActivities.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        rvNearActivities.adapter = nearByAdapter

        tvNearActivitiesViewAll.setOnClickListener {
            startActivity(Intent(context, NearByActivities::class.java))
        }

        ivNotificationHead.setOnClickListener {
            startActivity(Intent(context,NotificationActivity::class.java))
        }
    }

    override fun getActivityId(activityId: String,state:String) {

        if (state=="1"){
            viewModel.getWithdrawRequest(tokenGenerate,activityId)
        } else if (state=="0"){
            viewModelDetails.getProfileRequest(tokenGenerate,activityId)
        }
    }

    override fun activityShareId(activity: String) {
        if (::dialouge.isInitialized)
        dialouge.show()
        viewModel.getActivityShare(tokenGenerate,activity)
    }

    inner class EchoWebSocketListener() : WebSocketListener() {

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.e("SocketData", "onClosed: ${webSocket.send("hellow")}" )

        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.e("SocketData", "onClosing: ${webSocket.send("hellow")}" )
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("SocketData", "fail: ${webSocket.request()} $t}" )
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            var countNumber=tvNotifcatiNumber.text.toString().toInt()
            countNumber+=1
            tvNotifcatiNumber.text =countNumber.toString()
            Log.e("SocketData", "onMessage: $text" )
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.e("SocketData", "onMessage byte: $bytes" )
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.e("SocketData", "open  asdasdasd : ${response.body}" )
        }

    }

    private var mClient: OkHttpClient? = null
    private fun start() {
        mClient = OkHttpClient()
        val request = Request.Builder().url("ws://167.99.32.192:8000/notifications/consumer/mobile/${ConstValue.userId}/").build()
        val listener = EchoWebSocketListener()
        val webSocket = mClient!!.newWebSocket(request, listener)
        mClient!!.dispatcher.executorService.shutdown()
    }
}