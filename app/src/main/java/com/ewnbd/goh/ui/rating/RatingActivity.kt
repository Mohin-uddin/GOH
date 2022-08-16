package com.ewnbd.goh.ui.rating

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewnbd.goh.R
import com.ewnbd.goh.utils.ConstValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_rating.*
import kotlinx.coroutines.flow.collectLatest
import java.util.HashMap

@AndroidEntryPoint
class RatingActivity : AppCompatActivity() {
    private  val viewModel: ActivtyRatingViewModel by viewModels()
    private lateinit var dialouge: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        val preference = getSharedPreferences("GOH", Context.MODE_PRIVATE)
        val tokenData= preference?.getString("token","")?:""
        val tokenGenerate = HashMap<String,String>()
        tokenGenerate["Authorization"] = tokenData

        viewModel.ratingList(tokenGenerate, ConstValue.activityId)
        val ratingAdapter = RatingPersonAdapter()
        rvRatingPerson.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rvRatingPerson.adapter = ratingAdapter


        lifecycleScope.launchWhenCreated {
            viewModel.activtyRatingList.collectLatest {
                it?.let {
                    pBOne.progress=it.results.each_star_percentage.one_star.percentage.toDouble().toInt()
                    tvOneRatingPar.text = it.results.each_star_percentage.one_star.percentage.toDouble().toInt().toString()+"%"

                    pbTwoRating.progress = it.results.each_star_percentage.two_star.percentage.toDouble().toInt()
                    tvTwoRatingPar.text = it.results.each_star_percentage.two_star.percentage.toDouble().toInt().toString()+"%"

                    pbThreeRating.progress = it.results.each_star_percentage.three_star.percentage.toDouble().toInt()
                    tvThreePar.text = it.results.each_star_percentage.three_star.percentage.toDouble().toInt().toString()+"%"

                    pbFourStar.progress = it.results.each_star_percentage.four_star.percentage.toDouble().toInt()
                    tvFourPar.text = it.results.each_star_percentage.four_star.percentage.toDouble().toInt().toString()+"%"

                    pbFiveParsentag.progress = it.results.each_star_percentage.five_star.percentage.toDouble().toInt()
                    tvFivePa.text = it.results.each_star_percentage.five_star.percentage.toDouble().toInt().toString()+"%"

                   // ratingAdapter.updateRatingState(it.results.ratings)
                    tvRatingPoint.text = "${it.results.ratings.size} People Ratings"
                }
            }
        }

        ivRatingBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}