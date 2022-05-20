package com.ewnbd.goh.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.ewnbd.goh.R
import com.ewnbd.goh.databinding.ActivitySplashScreanBinding
import com.ewnbd.goh.ui.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    lateinit var splashScreanBinding: ActivitySplashScreanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreanBinding = ActivitySplashScreanBinding.inflate(layoutInflater)
        val view = splashScreanBinding.root
        setContentView(view)

        val hyperspaceJump: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        splashScreanBinding.cvLogo.startAnimation(hyperspaceJump)
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()

        }
    }
}