package com.ewnbd.goh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ewnbd.goh.ui.fragment.ActivitiesFragment
import com.ewnbd.goh.ui.fragment.ChatFragment
import com.ewnbd.goh.ui.fragment.ExploreFragment
import com.ewnbd.goh.ui.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var selectedFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedFragment=
            ExploreFragment()
        changeFrag(selectedFragment)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.idExplore -> {
                    selectedFragment=
                        ExploreFragment()

                }
                R.id.idActivities -> {
                    selectedFragment=
                        ActivitiesFragment()

                }
                R.id.idChat ->{
                    selectedFragment=
                        ChatFragment()
                }
                R.id.idProfile ->{
                    selectedFragment=
                        ProfileFragment()
                }
            }
            changeFrag(selectedFragment)

        }


    }

    private fun changeFrag(selectedFragment: Fragment): Boolean {

        val fm = supportFragmentManager
        fm.beginTransaction()
            .replace(R.id.contentContainer, selectedFragment)
            .addToBackStack(null)
            .commit()
        return true

    }
}