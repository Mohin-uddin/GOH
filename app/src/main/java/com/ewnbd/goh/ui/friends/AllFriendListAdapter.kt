package com.ewnbd.goh.ui.friends

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.ewnbd.goh.R
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.bottom_block_design.view.*
import kotlinx.android.synthetic.main.item_joint_request_cancel.*
import com.ewnbd.goh.data.model.response_all_class.friendList.Result

class AllFriendListAdapter (val friendAcceptRemove: FriendAcceptRemove): RecyclerView.Adapter<AllFriendListAdapter.AllFriendsHolder>() {
    lateinit var context:Context
    lateinit var dialog: DialogPlus
    var organizationList = ArrayList<Result>(emptyList())
    class AllFriendsHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFriendsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return AllFriendsHolder(
            layoutInflater.inflate(
                R.layout.item_joint_request_cancel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllFriendsHolder, position: Int) {
        holder.tvAcceptRequest.text="Share"
        Glide.with(context).load(R.drawable.ic_doted)
           .into(holder.ivCancelRequest)
       holder.ivCancelRequest.setOnClickListener {
           dialougeOpen(organizationList[position])
       }
        holder.civChatPerson.load(organizationList[position].dp)
        holder.tvName.text = organizationList[position].full_name
        holder.tvDetails.text = organizationList[position].date_joined

    }

    override fun getItemCount(): Int {
        return organizationList.size
    }

    fun dialougeOpen(result: Result) {
         dialog = DialogPlus.newDialog(context)
            .setContentHolder(ViewHolder(R.layout.bottom_block_design))
            .setExpanded(false) // This will enable the expand feature, (similar to android L share dialog)
            .setCancelable(true)
            .setContentBackgroundResource(R.drawable.bottom_sheet_top_curb)
            .create()
        val view: View = dialog.holderView
        view.civChatPerson.load(result.dp)
        view.tvFriendJoinDate.text=result.date_joined
        view.tvName.text = result.full_name
        view.tvSmsEnd.text= "Message ${result.full_name}"
        view.tvUnfriend.text= "Unfriend ${result.full_name}"
        view.llBlock.setOnClickListener {
            friendAcceptRemove.acceptFriend(result.id.toString())
            dialog.dismiss()
        }
        view.llUnfriend.setOnClickListener {
            friendAcceptRemove.removeFriend(result.id.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    fun interestUpdateList(organization: List<Result>){
        organizationList.clear()
        organizationList.addAll(organization)
        notifyDataSetChanged()
    }
}