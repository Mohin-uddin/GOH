package com.ewnbd.goh

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.ui.chatDetails.ChatDetailsDatGet
import com.ewnbd.goh.ui.createActivity.CreateActivity
import com.ewnbd.goh.ui.fragment.ActivitiesFragment
import com.ewnbd.goh.ui.fragment.ChatFragment
import com.ewnbd.goh.ui.fragment.ExploreFragment
import com.ewnbd.goh.ui.fragment.ProfileFragment
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.ConstValue.userId
import com.ewnbd.goh.utils.InternetConnectivityCheck
import com.ewnbd.goh.utils.changeVisibilityStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import org.java_websocket.client.WebSocketClient


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),ChatDetailsDatGet {
    private lateinit var webSocketClient: WebSocketClient
    lateinit var selectedFragment: Fragment
    private  val viewModel: NearActivityListViewModel by viewModels()
    lateinit var connectionLiveData : InternetConnectivityCheck
    private var state =0
    private var networkState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyStoragePermissions(this)
       // start()
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        userId = preference?.getString("userId","")?:""
        connectionLiveData = InternetConnectivityCheck(this)
        connectionLiveData.observe(this) { isNetworkState ->

            if (isNetworkState) {
                if(state==1) {

                    lifecycleScope.launch {
                        tvNetworkState.text = "Internet connected"
                        tvNetworkState.setTextColor(getColor(R.color.white))
                        tvNetworkState.setBackgroundColor(getColor(R.color.purple_700))
                        delay(2000)
                        tvNetworkState.changeVisibilityStatus()

                    }
                    state=0
                }

            } else {
                state=1
                tvNetworkState.changeVisibilityStatus()
                tvNetworkState.text = "Internet connection lost"
                tvNetworkState.setTextColor(getColor(R.color.black))
                tvNetworkState.setBackgroundColor(getColor(R.color.purple_200))
            }
            networkState=isNetworkState
        }
        selectedFragment=
            ExploreFragment()
        changeFrag(selectedFragment)
        bottom_navigation.itemIconTintList = null
        bottom_navigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.idExplore -> {
                    selectedFragment=
                        ExploreFragment()

                }
                R.id.idActivities -> {
                    selectedFragment=
                        ActivitiesFragment()

                }
                R.id.idChat ->{
                    selectedFragment=
                        ChatFragment()
                }
                R.id.idProfile ->{
                    selectedFragment=
                        ProfileFragment()
                }
            }
            changeFrag(selectedFragment)

        }

        ivHaydiHome.setOnClickListener {
            ConstValue.editeState=0
            startActivity(Intent(this@MainActivity,CreateActivity::class.java))
        }

    }

    private fun changeFrag(selectedFragment: Fragment): Boolean {

        val fm = supportFragmentManager
        fm.beginTransaction()
            .replace(R.id.contentContainer, selectedFragment)
            .addToBackStack(null)
            .commit()
        return true

    }

    override fun onResume() {
        super.onResume()
       // start()
    }
    override fun onPause() {
        super.onPause()
    }




    override fun getChatData(chat: String) {

    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    fun verifyStoragePermissions(activity: Activity?) {
        val permission =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val checkPermission = permission == PackageManager.PERMISSION_GRANTED
        val toastPermission = Toast.makeText(
            activity,
            "Is permission granted? $checkPermission",
            Toast.LENGTH_SHORT
        )
//        val toastLayoutPermission = toastPermission.view as LinearLayout?
//        val toastTVPermission = toastLayoutPermission!!.getChildAt(0) as TextView
//        toastTVPermission.textSize = 30f
//        toastPermission.show()
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}