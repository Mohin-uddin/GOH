package com.ewnbd.goh.ui.termsCondition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ewnbd.goh.R
import kotlinx.android.synthetic.main.activity_tearms_and_condition.*

class TearmsAndCondition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tearms_and_condition)

        ivTermsBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}