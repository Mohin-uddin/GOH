package com.ewnbd.goh.ui.login

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
import com.ewnbd.goh.ui.forgotpassword.ForgotPasswordActivity
import com.ewnbd.goh.ui.prefarence.PrefarenceActivity
import com.ewnbd.goh.ui.signup.SignUpActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private  val viewModel: LoginViewModel by viewModels()
    private lateinit var dialouge: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvLogin.setOnClickListener {

            if(validation()) {
                dialouge= ConstValue.animation(this,0)
                viewModel.getLoginRequest(etUserMobile.text.toString(), etPassword.text.toString())
            }
        }
        lifecycleScope.launch {
            viewModel.loginResponse.collect{
                it?.let {
                    if (::dialouge.isInitialized)
                    dialouge.dismiss()
                    val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
                    val editor = preference.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("token", "Token "+it.token)
                    editor.putString("userId",it.user_id)
                    editor.apply()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }


            }

        }

        lifecycleScope.launchWhenCreated {
            viewModel.responseCode.collectLatest { code->
                if (code!="0") {
                    Log.e("logeError", "onCreate: $code" )
                    if(::dialouge.isInitialized)
                    dialouge.dismiss()
                    when(code){
                        "400"->{
                            Toast.makeText(this@LoginActivity, "Unable to log in with provided credentials.", Toast.LENGTH_SHORT).show()
                        }

                    }
                    viewModel.getDataClear()
                }
            }
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }
        tvSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        ivVHide.setOnClickListener {
            ivPView.show()
            ivVHide.gone()
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        ivPView.setOnClickListener {
            ivVHide.show()
            ivPView.gone()
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }

    }

    private fun validation(): Boolean {
        if(etUserMobile.text.toString() == "")
        {
            etUserMobile.requestFocus()
            etUserMobile.error=getString(R.string.enter_your_email)
            Log.e("dataSetChange", "validation: namesaed")
            return false
        }
        if(etPassword.text.toString() == "") {
            etPassword.requestFocus()
            etPassword.error=getString(R.string.your_password)
            return false
        }

        return true
    }
}