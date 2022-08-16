package com.ewnbd.goh.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.MainActivity
import com.ewnbd.goh.R
import com.ewnbd.goh.databinding.ActivitySplashScreanBinding
import com.ewnbd.goh.ui.login.LoginActivity
import com.ewnbd.goh.ui.prefarence.PrefarenceActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    lateinit var splashScreanBinding: ActivitySplashScreanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val state= preference?.getBoolean("isLoggedIn",false)?:false
        splashScreanBinding = ActivitySplashScreanBinding.inflate(layoutInflater)
        val view = splashScreanBinding.root
        setContentView(view)

        val hyperspaceJump: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        splashScreanBinding.cvLogo.startAnimation(hyperspaceJump)
        lifecycleScope.launch {
            delay(2000)
            if (state) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            }

        }
    }
}