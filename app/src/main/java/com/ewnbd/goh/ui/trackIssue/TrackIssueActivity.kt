package com.ewnbd.goh.ui.trackIssue

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.notification.NotificationAdapter
import com.ewnbd.goh.ui.reportIssue.ReportIssueViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.activity_track_issue.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TrackIssueActivity : AppCompatActivity() {
    private  val viewModel: ReportIssueViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_issue)

        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        viewModel.getTrackingRequest(tokenGenerate)
        val trackingAdapter = TrackingIssueAdapter()
        rvTrackingIssue.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvTrackingIssue.adapter = trackingAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.trackingIssue.collectLatest {
                it?.let {
                    trackingAdapter.updateListRating(it.results)
                }
            }
        }
        ivTrackIssueBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}