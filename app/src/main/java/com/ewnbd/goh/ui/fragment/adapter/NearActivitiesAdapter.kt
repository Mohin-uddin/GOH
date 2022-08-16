package com.ewnbd.goh.ui.fragment.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.Result
import com.ewnbd.goh.ui.activitiesDetails.ActivitiesDetails
import com.ewnbd.goh.ui.fragment.ActivityRequestSend
import com.ewnbd.goh.ui.fragment.ActivityShare
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_activies.*
import kotlinx.android.synthetic.main.item_categories.*
import kotlinx.android.synthetic.main.item_interst.*
import kotlinx.android.synthetic.main.item_time_slot.*
import java.text.ParseException
import java.text.SimpleDateFormat

class NearActivitiesAdapter(val activityRequestSend: ActivityRequestSend,val activityShare: ActivityShare) :
    RecyclerView.Adapter<NearActivitiesAdapter.NearActivitiesHolder>() {

    lateinit var context: Context
    var nearbyActivityList = ArrayList<Result>(emptyList())

    class NearActivitiesHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearActivitiesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        return NearActivitiesHolder(
            layoutInflater.inflate(
                R.layout.item_activies,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NearActivitiesHolder, position: Int) {
        holder.tvActivityAddress.isSelected = true

        when (nearbyActivityList[position].status) {
            0 -> {
                holder.tvJoinRequest.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                holder.tvJoinRequest.setTextColor(Color.parseColor("#FFFFFF"))
                holder.tvJoinRequest.text = "Join request"
            }
            1 -> {
                holder.tvJoinRequest.text = "Withdraw Request"
                holder.tvJoinRequest.setBackgroundResource(R.drawable.music_back)
                holder.tvJoinRequest.setTextColor(Color.parseColor("#41B06B"))
            }
            2 -> {
                holder.tvJoinRequest.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                holder.tvJoinRequest.setTextColor(Color.parseColor("#FFFFFF"))
                holder.tvJoinRequest.text = "Get Direction"
            }
        }
        holder.itemView.setOnClickListener {

            ConstValue.activityItem = nearbyActivityList[position]
            context.startActivity(Intent(context, ActivitiesDetails::class.java))
        }

        if (0<nearbyActivityList[position].priority){
            holder.ivPremium.show()
        }else{
            holder.ivPremium.gone()
        }

        holder.tvActivesName.text = nearbyActivityList[position].name
        Glide.with(context).load(nearbyActivityList[position].photo)
            .placeholder(R.drawable.logo).into(holder.ivItemActiveImage)
        holder.tvActivityAddress.text = nearbyActivityList[position]?.place
        holder.tvActivesTime.text = nearbyActivityList[position]?.activity_date + " " +
                Convert24to12(nearbyActivityList[position]?.start_time) + "-" + Convert24to12(nearbyActivityList[position]?.end_time)
        holder.tvJoinRequest.setOnClickListener {
            if (holder.tvJoinRequest.text.equals("Join request")) {
                holder.tvJoinRequest.text = "Withdraw Request"
                holder.tvJoinRequest.setBackgroundResource(R.drawable.music_back)
                holder.tvJoinRequest.setTextColor(Color.parseColor("#41B06B"))
                activityRequestSend.getActivityId(
                    nearbyActivityList[position].id.toString(),
                    nearbyActivityList[position].status.toString()
                )
            } else if (holder.tvJoinRequest.text.equals("Withdraw Request")) {
                holder.tvJoinRequest.text = "Join request"
                holder.tvJoinRequest.setTextColor(Color.parseColor("#FFFFFF"))
                holder.tvJoinRequest.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                activityRequestSend.getActivityId(
                    nearbyActivityList[position].id.toString(),
                    nearbyActivityList[position].status.toString()
                )
            }
        }
        holder.ivUpload.setOnClickListener {
            activityShare.activityShareId(nearbyActivityList[position].id.toString())
        }
    }

    private fun getListUpdate(position: Int) {

        //notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return nearbyActivityList.size
    }

    fun updateNearList(nearList: List<Result>) {
        nearbyActivityList.clear()
        for (item in nearList){
            if (item.creator.id.toString()!=ConstValue.userId){
                nearbyActivityList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun Convert24to12(time: String?): String? {
        var convertedTime = ""
        try {
            val displayFormat = SimpleDateFormat("hh:mm a")
            val parseFormat = SimpleDateFormat("HH:mm:ss")
            val date: java.util.Date? = parseFormat.parse(time)
            convertedTime = displayFormat.format(date)
            println("convertedTime : $convertedTime")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertedTime
        //Output will be 10:23 PM
    }
}