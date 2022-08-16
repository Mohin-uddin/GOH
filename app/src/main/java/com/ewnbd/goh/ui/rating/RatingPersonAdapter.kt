package com.ewnbd.goh.ui.rating

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.data.model.response_all_class.activity_rating.Rating
import com.ewnbd.goh.data.model.response_all_class.activity_rating.Results
import com.ewnbd.goh.ui.nearbyActivitys.adapter.NearActivitiesItemCategoryAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rating_person_information.*
import kotlinx.android.synthetic.main.toast_layout.view.*

class RatingPersonAdapter : RecyclerView.Adapter<RatingPersonAdapter.RatingPersonHolder>() {
    lateinit var context:Context
    var listRating =ArrayList<Rating>(emptyList())
    class RatingPersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingPersonHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return RatingPersonHolder(
            layoutInflater.inflate(
                R.layout.item_rating_person_information,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RatingPersonHolder, position: Int) {
         holder.civChatPerson.load(listRating[position].rater.dp)
        holder.tvName.text = listRating[position].rater.full_name
        holder.tvDetails.text = listRating[position].rater.full_name
        holder.tvRatingReview.text = listRating[position].comment
    }

    override fun getItemCount(): Int {
        return listRating.size
    }

    fun updateRatingState(update: List<Rating>){
        listRating.clear()
        listRating.addAll(update)
        notifyDataSetChanged()
    }

}