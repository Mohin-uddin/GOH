package com.ewnbd.goh.ui.trackIssue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.trackingissue.Result
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tracking_list.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class TrackingIssueAdapter : RecyclerView.Adapter<TrackingIssueAdapter.TrackingIssueHolder>() {
    lateinit var context:Context
    var listRating =ArrayList<Result>(emptyList())
    class TrackingIssueHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingIssueHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return TrackingIssueHolder(
            layoutInflater.inflate(
                R.layout.item_tracking_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrackingIssueHolder, position: Int) {
         holder.tvTrackHead.text = listRating[position].category.issue_cat
        holder.tvTrackDetails.text = listRating[position].desc
        holder.tvDateTime.text = dateConvert(listRating[position].created)

    }

    override fun getItemCount(): Int {
        return listRating.size
    }

    fun updateListRating(updateList : List<Result>){
        listRating.clear()
        listRating.addAll(updateList)
        notifyDataSetChanged()
    }

    fun dateConvert(date : String): String{
        var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
        var date =
            dateFormat.parse(date) //You will get date object relative to server/client timezone wherever it is parsed
        var formatter: DateFormat =
            SimpleDateFormat("dd MMM,yyyy") //If you need time just put specific format for time like 'HH:mm:ss'
        return formatter.format(date)
    }

}