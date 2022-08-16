package com.ewnbd.goh.ui.friendProfile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.friendList.Result
import com.ewnbd.goh.ui.createActivity.SelectCategoryname
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_friend_share.*


class FriendShareAdapter(val selectCategoryname: SelectCategoryname) : RecyclerView.Adapter<FriendShareAdapter.FriendShareHolder>() {
    var friendList = ArrayList<Result>(emptyList())
    lateinit var context: Context
    class FriendShareHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendShareHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return FriendShareHolder(
            layoutInflater.inflate(
                R.layout.item_friend_share,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendShareHolder, position: Int) {

        holder.civFriendSharePerson.load(friendList[position].dp)
        holder.tvShareName.text = friendList[position].full_name

    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    fun friendListUpdateList(organization: List<Result>){
        friendList.clear()
        friendList.addAll(organization)
        notifyDataSetChanged()
    }
}