package com.ewnbd.goh.ui.organizationDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.organizationRequestList.Result
import com.ewnbd.goh.ui.activitiesDetails.AccptRequestSelect
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_accept_friend.*
import kotlinx.android.synthetic.main.item_joint_request_cancel.*

class OrganizationRequestListAdapter (var accptRequestSelect: AccptRequestSelect) :
    RecyclerView.Adapter<OrganizationRequestListAdapter.OrganizationRequestListHolder>() {
    var listRequest =ArrayList<Result>(emptyList())
    lateinit var context: Context
    class OrganizationRequestListHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrganizationRequestListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return OrganizationRequestListHolder(
            layoutInflater.inflate(
                R.layout.item_joint_request_cancel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrganizationRequestListHolder, position: Int) {
        holder.tvName.text= listRequest[position].request_sender.full_name
        holder.civChatPerson.load(listRequest[position].request_sender.dp)
        holder.tvDetails.text = listRequest[position].request_sender.email

        holder.tvAcceptRequest.setOnClickListener {
            accptRequestSelect.selectActivityRequest(listRequest[position].request_sender.id.toString())
            listRequest.removeAt(position)
            notifyDataSetChanged()
        }

        holder.ivCancelRequest.setOnClickListener {
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