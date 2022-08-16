package com.ewnbd.goh.ui.privecy_policy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ewnbd.goh.R
import kotlinx.android.synthetic.main.activity_privecy_policy.*

class PrivecyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privecy_policy)

        ivPrivacyBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}