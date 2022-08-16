package com.ewnbd.goh.ui.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.changePassword.ChangePasswordActivity
import com.ewnbd.goh.ui.fragment.viewmodel.NearActivityListViewModel
import com.ewnbd.goh.ui.resetPassword.ResetPasswordActivity
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    private  val viewModel: ForgetPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        tvSubmit.setOnClickListener {
            val data = tvPhoneNumberForRecetCode.text.toString()
            if(data.isNotEmpty()){
                ConstValue.mobileNumber=data
                viewModel.getRecentCodeGenerateRequest(data)
            }else{
                tvPhoneNumberForRecetCode.requestFocus()
                tvPhoneNumberForRecetCode.error="Enter valid Phone number"
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.recetCodeResponse.collectLatest {
                it?.let {
                    ConstValue.matchCode=it
                    ConstValue.signUpCode=it.code
                    startActivity(Intent(this@ForgotPasswordActivity, ForgetPasswordOtpActivity::class.java))
                    finish()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.responseCode.collectLatest {
                if(it=="400"){
                    Toast.makeText(this@ForgotPasswordActivity,"Phone number was not found",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
                else if (it!="0"&&it!="200"){
                    Toast.makeText(this@ForgotPasswordActivity,"Server Error $it",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
            }
        }

    }
}