package com.ewnbd.goh.ui.reportIssue

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.ReportIssueRequest
import com.ewnbd.goh.ui.prefarence.InterestAdapter
import com.ewnbd.goh.ui.trackIssue.TrackIssueActivity
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_report_issue.*
import kotlinx.android.synthetic.main.bottom_sheet_interest.view.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ReportIssueActivity : AppCompatActivity() {
    private  val viewModel: ReportIssueViewmodel by viewModels()
    private var selectdata =""
    private var categoryId = ""
    private lateinit var dialouge: Dialog

    lateinit var reportIssueAdapter: ReportIssueListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_issue)
        ivReportIssue.setOnClickListener {
            onBackPressed()
        }
        llPreviousIssueList.setOnClickListener {
            startActivity(Intent(this@ReportIssueActivity,TrackIssueActivity::class.java))
        }
        reportIssueAdapter = ReportIssueListAdapter()
        rvIssueLis.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvIssueLis.adapter = reportIssueAdapter
//        rgRadioGroupData.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { arg0, id ->
//            selectdata = findViewById<RadioButton>(id).text.toString()
//            when(selectdata){
//                "Service was not good"->{
//                    categoryId ="1"
//                }
//                "Seller’s behavior was rude"->{
//                    categoryId ="2"
//                }
//                "I have to wait a lot"->{
//                    categoryId ="3"
//                }
//                "I can’t track my order"->{
//                    categoryId ="4"
//                }
//                "Others"->{
//                    categoryId ="5"
//                }
//            }
//        })
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        viewModel.getReportIssueList(tokenGenerate)
        tvIssueSubmit.setOnClickListener {
           if (ConstValue.issueStateCategoryId=="" ){
               Toast.makeText(this,"Please select report issue",Toast.LENGTH_SHORT).show()
           }
            if (etIssueWrite.text.toString().isEmpty()){
                etIssueWrite.requestFocus()
                etIssueWrite.error=getString(R.string.report_issue)
               // Toast.makeText(this,"Please write report issue",Toast.LENGTH_SHORT).show()
            }
            else{
                dialouge= ConstValue.animation(this,0)
                val reportIssueRequest = ReportIssueRequest(ConstValue.issueStateCategoryId,etIssueWrite.text.toString())
               viewModel.getReportIssueRequest(tokenGenerate,reportIssueRequest)
           }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.reportIssueReponse.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    Toast.makeText(this@ReportIssueActivity,"Successfully submit your Report ",Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    if (it!="0"&&it!="200"){
                        Toast.makeText(this@ReportIssueActivity,"Server error $it",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.reportIssueListReponse.collectLatest {
                it?.let {
                  reportIssueAdapter.updateList(it.results)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}