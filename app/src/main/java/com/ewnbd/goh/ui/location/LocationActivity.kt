package com.ewnbd.goh.ui.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ewnbd.goh.R
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        ivLocationBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}