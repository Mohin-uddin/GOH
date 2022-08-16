package com.ewnbd.goh.ui.friends

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.friendRequestList.Result
import com.ewnbd.goh.ui.ProfileActivity
import com.ewnbd.goh.ui.friendProfile.FriendProfileActivity
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_joint_request_cancel.*

class FriendRequestAdapter(val friendAcceptRemove: FriendAcceptRemove) : RecyclerView.Adapter<FriendRequestAdapter.FriendRequestHolder>() {
    lateinit var context:Context
    var organizationList = ArrayList<Result>(emptyList())
    class FriendRequestHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return FriendRequestHolder(
            layoutInflater.inflate(
                R.layout.item_joint_request_cancel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendRequestHolder, position: Int) {
        holder.tvName.text= organizationList[position].fr_sent_by.full_name
        holder.civChatPerson.load(organizationList[position].fr_sent_by.dp)
        holder.tvDetails.text = organizationList[position].request_sent_time
        holder.tvAcceptRequest.setOnClickListener {
            friendAcceptRemove.acceptFriend(organizationList[position].fr_sent_by.id.toString())
            organizationList.removeAt(position)
            notifyDataSetChanged()
        }
        holder.ivCancelRequest.setOnClickListener {
            friendAcceptRemove.removeFriend(organizationList[position].fr_sent_by.id.toString())
            organizationList.removeAt(position)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            ConstValue.userId = organizationList[position].fr_sent_by.id.toString()
            context.startActivity(Intent(context, FriendProfileActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return organizationList.size
    }
    fun interestUpdateList(organization: List<Result>){
        organizationList.clear()
        organizationList.addAll(organization)
        notifyDataSetChanged()
    }
}