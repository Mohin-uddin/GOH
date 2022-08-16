package com.ewnbd.goh.ui.signup

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.MainActivity
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.login.LoginViewModel
import com.ewnbd.goh.ui.verification.VerificationActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private  val viewModel: SignUpViewModel by viewModels()
    private  var dialouge: Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tvSignUpNext.setOnClickListener {
            if (validation()) {
                dialouge = ConstValue.animation(this, 0)
                viewModel.getSignUpRequest(
                    etSignFullName.text.toString(),
                    etSignUpMobileNumber.text.toString(),
                    etSignUpEmail.text.toString(),
                    etSignUpPassword.text.toString(),
                    etSignUpConfirmPassword.text.toString()
                )
            }
        }

        lifecycleScope.launch {

            viewModel.signUpResponse.collectLatest {
                Log.e("dataCheck", "onCreate: " )
                if(it!=null) {

                    dialouge?.dismiss()
                    ConstValue.signUpCode = it.code
                    val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
                    val editor = preference.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("userId",it.user.id.toString())
                    editor.putString("token", "Token "+it.token)
                    editor.apply()
                    startActivity(Intent(this@SignUpActivity,VerificationActivity::class.java))
                    finish()
                }
            }

        }
        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest { code->
                if (code!=null&&code!="0") {
                    dialouge?.dismiss()
                        Toast.makeText(this@SignUpActivity,code,Toast.LENGTH_LONG).show()
                        viewModel.getDataClear()

                }


            }
        }


        passwordHideAndGone()

    }

    private fun passwordHideAndGone() {
        ivSVHide.setOnClickListener {
            ivSPView.show()
            ivSVHide.gone()
            etSignUpPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        ivSPView.setOnClickListener {
            ivSVHide.show()
            ivSPView.gone()
            etSignUpPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        ivConVHide.setOnClickListener {
            ivConPView.show()
            ivConVHide.gone()
            etSignUpConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        ivConPView.setOnClickListener {
            ivConVHide.show()
            ivConPView.gone()
            etSignUpConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    private fun validation(): Boolean {
        if(etSignFullName.text.toString() == "")
        {
            etSignFullName.requestFocus()
            etSignFullName.error=getString(R.string.name)
            return false
        }
        if(etSignUpMobileNumber.text.toString()==""){
            etSignUpMobileNumber.requestFocus()
            etSignUpMobileNumber.error=getString(R.string.mobile)
            return false
        }
        if(etSignUpPassword.text.toString() == "") {
            etSignUpPassword.requestFocus()
            etSignUpPassword.error=getString(R.string.your_password)
            return false
        }
        if(etSignUpConfirmPassword.text.toString() == "") {
            etSignUpConfirmPassword.requestFocus()
            etSignUpConfirmPassword.error=getString(R.string.your_password)
            return false
        }
        if (!cbState.isChecked){
            Toast.makeText(this,"Select Terms and Condition",Toast.LENGTH_SHORT).show()
            return false
        }

       // Log.e("passwordCheack", "validation: "+etSignPassword.text.toString()+"  "+etConfirmPassword.text.toString() )
        if(etSignUpPassword.text.toString()!=etSignUpConfirmPassword.text.toString())
        {
            etSignUpPassword.requestFocus()
            etSignUpPassword.error=getString(R.string.password_match)
            return false
        }
        if(etSignUpPassword.text.toString().length<4)
        {
            etSignUpPassword.requestFocus()
            etSignUpPassword.error=getString(R.string.password_sort)
            return false
        }
        if(etSignUpConfirmPassword.text.toString().length<4)
        {
            etSignUpConfirmPassword.requestFocus()
            //   showPopUpError("Password is too sort")
            etSignUpConfirmPassword.error=getString(R.string.password_sort)
            return false
        }


        return true
    }

}