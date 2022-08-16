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
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.Result
import com.ewnbd.goh.ui.createActivity.CreateActivity
import com.ewnbd.goh.ui.organizationDetails.OrganizationDetailsActivity
import com.ewnbd.goh.ui.rating.RatingActivity
import com.ewnbd.goh.ui.ratingactivity.RatingActivityPerson
import com.ewnbd.goh.utils.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_activies.*
import java.text.ParseException
import java.text.SimpleDateFormat

class OrganizationActivityAdapter() :
    RecyclerView.Adapter<OrganizationActivityAdapter.OrganizationActivityHolder>() {
    lateinit var context: Context
    var organizationList = ArrayList<Result>(emptyList())
    var state = 0
    class OrganizationActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer{
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationActivityHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return OrganizationActivityHolder(
            layoutInflater.inflate(
                R.layout.item_activies,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrganizationActivityHolder, position: Int) {
        holder.tvActivityAddress.isSelected=true
        if (ConstValue.organizationState==4){
            when (organizationList[position].status) {
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

        }else{
            if (state==0) {
                holder.ivUpload.show()
                holder.tvJoinRequest.text = "Update activity"
            }
            else{
                holder.ivUpload.gone()
                holder.tvJoinRequest.text="Rate the Organizer"
            }
        }
        if (0<organizationList[position].priority){
            holder.ivPremium.show()
        }else{
            holder.ivPremium.gone()
        }

        holder.tvActivesName.text=organizationList[position]?.name
        Glide.with(context).load(organizationList[position]?.photo)
            .placeholder(R.drawable.logo).into(holder.ivItemActiveImage)
        holder.tvActivityAddress.text =organizationList[position]?.place
        holder.tvActivesTime.text=organizationList[position]?.activity_date+" "+
                Convert24to12(organizationList[position]?.start_time)+"-"+Convert24to12(organizationList[position]?.end_time)

        holder.tvJoinRequest.setOnClickListener {
            if (holder.tvJoinRequest.text.toString()=="Update activity"){
                ConstValue.editeState=1
                ConstValue.activityId = organizationList[position].id.toString()
                context.startActivity(Intent(context, CreateActivity::class.java))
            }
        }

        holder.itemView.setOnClickListener {
             if(state==0) {
                context.startActivity(Intent(context, OrganizationDetailsActivity::class.java))
                DoesNetworkHaveInternet.organization = organizationList[position]
                DoesNetworkHaveInternet.activityId = organizationList[position]?.id
            }else{
                ConstValue.activityId = organizationList[position].id.toString()
                DataPassing.userInformation = organizationList[position]
                context.startActivity(Intent(context, RatingActivityPerson::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return organizationList.size
    }

    fun interestUpdateList(organization: List<Result>,sta:Int){
        organizationList.clear()
        state=sta
        organizationList.addAll(organization)
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