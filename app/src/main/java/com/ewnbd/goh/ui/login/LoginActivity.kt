package com.ewnbd.goh.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ewnbd.goh.MainActivity
import com.ewnbd.goh.R
import com.ewnbd.goh.ui.forgotpassword.ForgotPasswordActivity
import com.ewnbd.goh.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvLogin.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }
        tvSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}