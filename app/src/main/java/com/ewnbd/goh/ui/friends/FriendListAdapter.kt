package com.ewnbd.goh.ui.friends

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.friendList.Result
import com.ewnbd.goh.ui.ProfileActivity
import com.ewnbd.goh.ui.friendProfile.FriendProfileActivity
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_friend.*

class FriendListAdapter: RecyclerView.Adapter<FriendListAdapter.FriendsHolder>() {
    lateinit var context:Context
    var organizationList = ArrayList<Result>(emptyList())
    class FriendsHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return FriendsHolder(
            layoutInflater.inflate(
                R.layout.item_friend,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.civProfilePic.load(organizationList[position].dp)
        holder.tvPersonName.text = organizationList[position].full_name

        holder.itemView.setOnClickListener {
            ConstValue.userId= organizationList[position].id.toString()
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