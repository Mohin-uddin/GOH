package com.ewnbd.goh.ui.organizationDetails


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.organizationRequestList.Result
import com.ewnbd.goh.ui.activitiesDetails.AccptRequestSelect
import com.ewnbd.goh.ui.friendProfile.FriendProfileActivity
import com.ewnbd.goh.ui.nearbyActivitys.adapter.NearActivitiesItemCategoryAdapter
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_accept_friend.*

class OrganizationAcceptingAdapter (var accptRequestSelect: AccptRequestSelect) :
    RecyclerView.Adapter<OrganizationAcceptingAdapter.OrganizationAcceptingHolder>() {
    var listRequest =ArrayList<Result>(emptyList())
    lateinit var context: Context
    class OrganizationAcceptingHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationAcceptingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return OrganizationAcceptingHolder(
            layoutInflater.inflate(
                R.layout.item_accept_friend,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrganizationAcceptingHolder, position: Int) {
        holder.ivRequestPersonPic.load(listRequest[position].request_sender.dp)
        holder.tvRequestPersonName.text = listRequest[position].request_sender.full_name

        holder.tvRequestPersonName.setOnClickListener {
            ConstValue.userId = listRequest[position].request_sender.id.toString()
            context.startActivity(Intent(context,FriendProfileActivity::class.java))
        }
        holder.tvAcceptMember.setOnClickListener {
            accptRequestSelect.selectActivityRequest(listRequest[position].request_sender.id.toString())
            listRequest.removeAt(position)
            notifyDataSetChanged()
        }
        holder.tvCancelRequest.setOnClickListener {
            accptRequestSelect.cancelActivityRequest(listRequest[position].request_sender.id.toString())
            listRequest.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listRequest.size
    }

    fun updateRequestList ( list : List<Result>){
        listRequest.clear()
        listRequest.addAll(list)
        notifyDataSetChanged()
    }
}