package com.ewnbd.goh.ui.resetPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.request_all_class.forget.ForgetpasswordRequest
import com.ewnbd.goh.ui.forgotpassword.ForgetPasswordViewModel
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {
    private  val viewModel: RecentPasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        passwordHideAndGone()
        tvConfirm.setOnClickListener {
            if (validation()){
                val forgetPasswordRequest = ForgetpasswordRequest(etForgetPassword.text.toString(),
                etForgetConfirmPassword.text.toString())
               viewModel.getForgetRequest(forgetPasswordRequest,ConstValue.matchCode.username,
               ConstValue.matchCode.code)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.passwordChangeResponse.collectLatest {
                it?.let {
                    Toast.makeText(this@ResetPasswordActivity,it.success_message,Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.responseCode.collectLatest {
                if (it!="0"&&it!="200"){
                    Toast.makeText(this@ResetPasswordActivity,"Server error $it",Toast.LENGTH_SHORT).show()
                    viewModel.getDataClear()
                }
            }
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun passwordHideAndGone() {
        ivFVHide.setOnClickListener {
            ivFPView.show()
            ivFVHide.gone()
            etForgetPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        ivFPView.setOnClickListener {
            ivFVHide.show()
            ivFPView.gone()
            etForgetPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        ivFConVHide.setOnClickListener {
            ivFConPView.show()
            ivFConVHide.gone()
            etForgetConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        ivFConPView.setOnClickListener {
            ivFConVHide.show()
            ivFConPView.gone()
            etForgetConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun validation(): Boolean {
        if(etForgetPassword.text.toString() == "") {
            etForgetPassword.requestFocus()
            etForgetPassword.error=getString(R.string.your_password)
            return false
        }
        if(etForgetConfirmPassword.text.toString() == "") {
            etForgetConfirmPassword.requestFocus()
            etForgetConfirmPassword.error=getString(R.string.your_password)
            return false
        }

        // Log.e("passwordCheack", "validation: "+etSignPassword.text.toString()+"  "+etConfirmPassword.text.toString() )
        if(etForgetPassword.text.toString()!=etForgetConfirmPassword.text.toString())
        {
            etForgetPassword.requestFocus()
            etForgetPassword.error=getString(R.string.password_match)
            return false
        }
        if(etForgetPassword.text.toString().length<6)
        {
            etForgetPassword.requestFocus()
            etForgetPassword.error=getString(R.string.password_sort)
            return false
        }
        if(etForgetConfirmPassword.text.toString().length<6)
        {
            etForgetConfirmPassword.requestFocus()
            //   showPopUpError("Password is too sort")
            etForgetConfirmPassword.error=getString(R.string.password_sort)
            return false
        }


        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}