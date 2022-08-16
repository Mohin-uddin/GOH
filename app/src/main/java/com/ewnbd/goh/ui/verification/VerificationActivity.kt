package com.ewnbd.goh.ui.verification

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.MainActivity
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.login.LoginViewModel
import com.ewnbd.goh.ui.prefarence.PrefarenceActivity
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationActivity : AppCompatActivity() {
    private lateinit var dialouge: Dialog
    private  val viewModel: VerifyCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val userId= preference?.getString("userId","")?:"false"
        etFirstCode.setText(ConstValue.signUpCode[0].toString())
        etSecondCode.setText(ConstValue.signUpCode[1].toString())
        etThirdCode.setText(ConstValue.signUpCode[2].toString())
        etFourthCode.setText(ConstValue.signUpCode[3].toString())

        tvVerify.setOnClickListener {
            dialouge= ConstValue.animation(this,0)
           viewModel.getVerificationRequest(userId,ConstValue.signUpCode)
        }
        lifecycleScope.launch {
            viewModel.verifactionResponse.collect {
                it?.let {
                    if (::dialouge.isInitialized)
                        dialouge.dismiss()
                    val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
                    val editor = preference.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("userId",it.user.id.toString())
                    editor.putString("userName",it.user.full_name)
                    editor.apply()
                    startActivity(Intent(this@VerificationActivity,PrefarenceActivity::class.java))
                    finish()
                }


            }

        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collect { code->
                code.let { sms->
                    if (::dialouge.isInitialized)
                    dialouge.dismiss()
                    if (sms!="0"&&sms!="200") {
                        Toast.makeText(
                            this@VerificationActivity,
                            "Server error $sms",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.getDataClear()
                    }
                }

            }
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }

        tvReSendOtp.setOnClickListener {

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}