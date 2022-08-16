package com.ewnbd.goh.ui.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.CodeMatchingRequest
import com.ewnbd.goh.ui.resetPassword.ResetPasswordActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.ConstValue.signUpCode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_forget_password_otp.*
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordOtpActivity : AppCompatActivity() {
    private  val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_otp)
        etForgetFirstCode.setText(ConstValue.signUpCode[0].toString())
        etForgetSecondCode.setText(ConstValue.signUpCode[1].toString())
        etForgetThirdCode.setText(ConstValue.signUpCode[2].toString())
        etForgetFourthCode.setText(ConstValue.signUpCode[3].toString())

        tvSendNumberForget.text = "Sent to "+ConstValue.mobileNumber
        tvForgetVerify.setOnClickListener {
            val codeMatchingRequest = CodeMatchingRequest(ConstValue.matchCode.code)
            viewModel.getMatchCodeRequest(codeMatchingRequest,ConstValue.matchCode.username)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.matchCodeResponse.collectLatest {
                it?.let {
                    Log.e("dighnUpCode", "onCreate: ${it.code}", )
                    if (signUpCode==it.code){
                        startActivity(Intent(this@ForgetPasswordOtpActivity,ResetPasswordActivity::class.java))
                        finish()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.responseCode.collectLatest {
                if (it=="404"){
                    Toast.makeText(this@ForgetPasswordOtpActivity,"user with this code does not exists",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
            }
        }
        tvSend.setOnClickListener {
            viewModel.getRecentCodeGenerateRequest(ConstValue.mobileNumber)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.recetCodeResponse.collectLatest {
                it?.let {

                    etForgetFirstCode.setText(it.code[0].toString())
                    etForgetSecondCode.setText(it.code[1].toString())
                    etForgetThirdCode.setText(it.code[2].toString())
                    etForgetFourthCode.setText(it.code[3].toString())
                }
            }
        }
    }
}