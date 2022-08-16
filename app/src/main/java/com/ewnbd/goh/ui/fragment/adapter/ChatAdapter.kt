package com.ewnbd.goh.ui.fragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.SelectChatDetailsData
import com.ewnbd.goh.ui.chatDetails.ChatDetailsActivity
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.ewnbd.goh.data.model.response_all_class.chat.Result

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {
    lateinit var context:Context
    var chatList = ArrayList<Result>(emptyList())
    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return ChatHolder(
            layoutInflater.inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        if (chatList[position].unseen_message_count.unread_count!=0){
            holder.tvCountSms.show()
            holder.tvCountSms.text = chatList[position].unseen_message_count.unread_count.toString()
        }else{
            holder.tvCountSms.gone()
        }
        holder.tvFriendName.text = chatList[position].last_message.chat_with.full_name
        holder.civChatPerson.load("http://167.99.32.192"+chatList[position].last_message.chat_with.dp)
        holder.tvLastSms.text = chatList[position].last_message.msg
        holder.tvChatTime.text = dateConvert(chatList[position].changeable_timestamp)
        holder.itemView.setOnClickListener {
            if (chatList[position].block_status.blocked) {
                ConstValue.blockStatus = chatList[position].block_status.blocked
            }else{
                ConstValue.blockStatus = chatList[position].block_status.blocked
            }

            if (ConstValue.userId == chatList[position].user1.toString())
                ConstValue.chatId = chatList[position].user2.toString()
            else {
                ConstValue.chatId = chatList[position].user1.toString()
            }

            val selectChatDetailsData = SelectChatDetailsData(chatList[position].last_message.chat_with.full_name,
                chatList[position].last_message.chat_with.username,"http://167.99.32.192"+chatList[position].last_message.chat_with.dp)
            ConstValue.lastSms = selectChatDetailsData
            context.startActivity(Intent(context, ChatDetailsActivity::class.java))
        }


    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun updateChatList(listChat: List<Result>){
        chatList.clear()
        chatList.addAll(listChat)
        notifyDataSetChanged()
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