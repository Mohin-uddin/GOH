package com.ewnbd.goh.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.ewnbd.goh.R
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import com.ewnbd.goh.data.model.response_all_class.notificationRe.Result
import com.ewnbd.goh.ui.activitiesDetails.AccptRequestSelect
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_notificasion.*
import kotlinx.android.synthetic.main.item_notificasion.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class NotificationAdapter(var accptRequestSelect: AccptRequestSelect) : RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {
    var notificationList = ArrayList<Result>(emptyList())
    lateinit var context : Context

    class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return NotificationHolder(
            layoutInflater.inflate(
                R.layout.item_notificasion,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.tvTimeNotification.text = dateConvert(notificationList[position].created)
          when(notificationList[position].noti_type){
              "RATING" ->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = true,
                      view = holder.itemView
                  )
                  Glide.with(context).load(notificationList[position].sender.dp).placeholder(R.drawable.not_pic)
                  holder.tvName.text = notificationList[position].sender.full_name+" "+notificationList[position].verb
              }
              "FRIEND REQUEST" ->{
                  holder.tvName.text = notificationList[position].sender.full_name+" "+notificationList[position].verb
                  Glide.with(context).load(notificationList[position].sender.dp).placeholder(R.drawable.not_pic)
                  holder.tvCancelOrDeny.text = "Cancel"
                  visibilityView(
                      tvAccept = true,
                      tvCancelOrDeny = true,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  holder.tvAccept.setOnClickListener {
                      ConstValue.requestType = 10
                      accptRequestSelect.selectActivityRequest(notificationList[position].sender.id.toString())
                  }
                  holder.tvCancelOrDeny.setOnClickListener {
                      ConstValue.requestType = 10
                      accptRequestSelect.cancelActivityRequest(notificationList[position].sender.id.toString())
                  }
              }
              "PARTICIPATION REQUEST" ->{
                  Glide.with(context).load(notificationList[position].sender.dp).placeholder(R.drawable.not_pic)
                  holder.tvName.text = notificationList[position].sender.full_name+" "+notificationList[position].verb
                  holder.itemView.tvCancelOrDeny.text = "Deny"
                  visibilityView(
                      tvAccept = true,
                      tvCancelOrDeny = true,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  ConstValue.activityId = notificationList[position].object_id.toString()
                  holder.tvAccept.setOnClickListener {
                      ConstValue.requestType = 11
                      accptRequestSelect.selectActivityRequest(notificationList[position].sender.id.toString())
                  }
                  holder.tvCancelOrDeny.setOnClickListener {
                      ConstValue.requestType = 11
                      accptRequestSelect.cancelActivityRequest(notificationList[position].sender.id.toString())
                  }

              }

              "ACTIVITY CREATE"->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  holder.civChatPerson.load(R.drawable.celebrating)
                  holder.tvName.text = "Congratulations! You've\n" +
                          "successfully created an activity"
              }
              "PARTICIPATION APPROVED" ->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  Glide.with(context).load(notificationList[position].sender.dp).placeholder(R.drawable.not_pic)
                  holder.tvName.text = notificationList[position].sender.full_name+" "+notificationList[position].verb
              }
              "ACCOUNT CREATION"->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  holder.tvName.text = notificationList[position].verb
                  holder.civChatPerson.load(R.drawable.account_creation)
              }
              "ACTIVITY CANCEL" ->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  holder.tvName.text = notificationList[position].extra_data.data.name+" "+"has been canceled"
                  holder.civChatPerson.load(R.drawable.cancel_activity)
              }
              "FRIEND REQUEST ACCEPTED"->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  Glide.with(context).load(notificationList[position].sender.dp).placeholder(R.drawable.not_pic)
                  holder.tvName.text = notificationList[position].sender.full_name+" "+notificationList[position].verb
              }
              "REPORT AN ISSUE" ->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  Glide.with(context).load(notificationList[position].sender.dp).placeholder(R.drawable.not_pic)
                  holder.tvName.text = notificationList[position].sender.full_name+" "+notificationList[position].verb
              }
              "PROMOTE ACTIVITY" ->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  holder.tvName.text=notificationList[position].extra_data.data.name+" "+notificationList[position].verb
                  holder.civChatPerson.load(R.drawable.promotion)
              }
              "PROMOTION EXPIRED"->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
              }
              "SHARE ACTIVITY" ->{
                  visibilityView(
                      tvAccept = false,
                      tvCancelOrDeny = false,
                      rbRatingBar = false,
                      view = holder.itemView
                  )
                  holder.tvName.text=notificationList[position]?.sender?.full_name+" "+notificationList[position]?.verb+" "+notificationList[position]?.extra_data?.data?.name
                  Glide.with(context).load(notificationList[position].sender.dp).placeholder(R.drawable.not_pic)
              }
          }
    }


    override fun getItemCount(): Int {

        return notificationList.size
    }
    fun updateList(notification : List<Result>){
        notificationList.addAll(notification)
        notifyDataSetChanged()
    }

    fun visibilityView(tvAccept:Boolean,tvCancelOrDeny: Boolean,rbRatingBar:Boolean,view:View){
        if (tvAccept){
            view.tvAccept.show()
        }else{
            view.tvAccept.gone()
        }
        if (tvCancelOrDeny){
            view.tvCancelOrDeny.show()
        }else{
            view.tvCancelOrDeny.gone()
        }
        if (rbRatingBar){
            view.rbRatingBar.show()
        }else{
            view.rbRatingBar.gone()
        }
    }

    fun dateConvert(date : String): String{
        var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX")
        var date =
            dateFormat.parse(date) //You will get date object relative to server/client timezone wherever it is parsed
        var formatter: DateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var formatter1: DateFormat =
            SimpleDateFormat("yyyy-MM-dd")//If you need time just put specific format for time like 'HH:mm:ss'
        val data1= formatter.parse(formatter.format(date))
        val oldTimeStamp =data1.time
        val timestamp = System.currentTimeMillis()

        val diffrent =timestamp-oldTimeStamp
        val second = diffrent/1000
        val min = second/60
        val hour = min/60
        val day = hour/24
        val weak = hour/7
        val month = day/30

        if (min=="0".toLong()){
            return "Now"
        }
        else if(hour=="0".toLong()){
            return "$min Min"
        }
        else if (day=="0".toLong()){
            return "$hour Hour"
        }
        else if (month =="0".toLong()){
            return "$day Days"
        }
        return  formatter1.format(date)
    }

}