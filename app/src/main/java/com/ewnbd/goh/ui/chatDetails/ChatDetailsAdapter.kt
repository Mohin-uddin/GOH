package com.ewnbd.goh.ui.chatDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.ChatDetailsModel
import com.ewnbd.goh.data.model.response_all_class.friendList.Result
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_details.*

class ChatDetailsAdapter : RecyclerView.Adapter<ChatDetailsAdapter.ChatDetailsHolder>() {
    var chatDetails = ArrayList<ChatDetailsModel>(emptyList())
    class ChatDetailsHolder (itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatDetailsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ChatDetailsHolder(
            layoutInflater.inflate(
                R.layout.item_chat_details,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatDetailsHolder, position: Int) {
          if (chatDetails[position].chat.isNotEmpty()){
              holder.cIvFriendChat.gone()
              holder.tvFriendChat.gone()
              holder.tvMyChat.show()
          }
        else
          {
              holder.cIvFriendChat.show()
              holder.tvFriendChat.show()
              holder.tvMyChat.gone()
          }
        holder.cIvFriendChat.load(ConstValue.lastSms?.img)
        holder.tvMyChat.text=chatDetails[position].chat
        holder.tvFriendChat.text=chatDetails[position].chatPerson
    }

    override fun getItemCount(): Int {
        return  chatDetails.size
    }

    fun updateChat(chatUpdate : ArrayList<ChatDetailsModel>){
        chatDetails.clear()
        chatDetails.addAll(chatUpdate)
        notifyDataSetChanged()
    }
}

