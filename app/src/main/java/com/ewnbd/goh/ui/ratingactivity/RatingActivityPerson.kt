package com.ewnbd.goh.ui.ratingactivity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.RatingActivityRequest
import com.ewnbd.goh.ui.rating.ActivtyRatingViewModel
import com.ewnbd.goh.ui.rating.RatingActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.DataPassing
import kotlinx.android.synthetic.main.activity_rating2.*
import kotlinx.android.synthetic.main.toast_layout.view.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class RatingActivityPerson : AppCompatActivity() {
    private  val viewModel: ActivtyRatingViewModel by viewModels()
    private lateinit var dialouge: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating2)
        dataSet()
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        tvIssueSubmit.setOnClickListener {
            dialouge = ConstValue.animation(this, 0)
            var comments = ""
            if (etIssueWrite.text.toString().isNotEmpty()){
                comments=etIssueWrite.text.toString()
            }
            val ratingActivityRequest = RatingActivityRequest(rbRatingData.rating.toString().toDouble().toInt().toString(),comments,radioButton1.isChecked)
            viewModel.ratingList(tokenGenerate,DataPassing.userInformation?.id.toString(),ratingActivityRequest)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.activityRatingPerson.collectLatest {
                it?.let {
                    if (::dialouge.isInitialized){
                        dialouge.dismiss()
                    }
                    ConstValue.activityId = DataPassing.userInformation?.id.toString()
                    Toast.makeText(this@RatingActivityPerson,"Successfully rating submit",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RatingActivityPerson, RatingActivity::class.java))
                    finish()
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest {
                it.let {
                    if (it!="0"){
                        if (::dialouge.isInitialized){
                            dialouge.dismiss()
                        }
                    }
                }

            }
        }
    }

    private fun dataSet() {
        civChatPerson.load(DataPassing.userInformation?.photo)
        tvName.text = DataPassing.userInformation?.name
        tvDetailsRatingPerson.text = DataPassing.userInformation?.desc
    }
}