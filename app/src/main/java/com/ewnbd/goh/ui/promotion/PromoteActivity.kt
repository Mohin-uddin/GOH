package com.ewnbd.goh.ui.promotion

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.PromotionRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_promote.*
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class PromoteActivity : AppCompatActivity() {
    val amount = 283
    private  val viewModel: PromotionViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promote)
        ivPromoteBack.setOnClickListener {
            onBackPressed()
        }
        tvPlus.setOnClickListener {
            var daysCount = tvDaysCount.text.toString().toInt()
            daysCount++
            tvTotalAmount.text = "SR ${amount*daysCount}"
            tvResultData.text = "Payment (${amount*daysCount} SR)"
            tvDaysCount.setText(daysCount.toString())
        }
        tvMinus.setOnClickListener {
            var daysCount = tvDaysCount.text.toString().toInt()
            if (1<daysCount){
                daysCount--
                tvTotalAmount.text = "SR ${amount*daysCount}"
                tvResultData.text = "Payment (${amount*daysCount} SR)"
                tvDaysCount.setText(daysCount)
            }
        }
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData
        tvResultData.setOnClickListener {
            viewModel.getPromotionResponse(tokenGenerate, PromotionRequest(tvDaysCount.text.toString()))
        }

        lifecycleScope.launchWhenCreated {
            viewModel.promotionData.collectLatest {
                it?.let {
                    if (it.payment_url.isNotEmpty()&&it.payment_url!=null) {
                        val uri: Uri =
                            Uri.parse(it.payment_url) // missing 'http://' will cause crashed
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}