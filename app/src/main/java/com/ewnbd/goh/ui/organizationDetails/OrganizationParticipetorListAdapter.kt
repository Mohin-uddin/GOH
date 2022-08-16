package com.ewnbd.goh.ui.organizationDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.organizationParticipetorList.Result
import com.ewnbd.goh.ui.createActivity.SelectCategoryname
import com.ewnbd.goh.utils.gone
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_joint_request_cancel.*

class OrganizationParticipetorListAdapter(val selectParti: SelectCategoryname):
    RecyclerView.Adapter<OrganizationParticipetorListAdapter.OrganizationPartiHolder>() {
    lateinit var context:Context
    var listRequest =ArrayList<Result>(emptyList())
    class OrganizationPartiHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer
    {
        override val containerView: View
            get() = itemView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrganizationPartiHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return OrganizationPartiHolder(
            layoutInflater.inflate(
                R.layout.item_joint_request_cancel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: OrganizationPartiHolder,
        position: Int
    ) {

        holder.tvAcceptRequest.gone()
        holder.tvName.text= listRequest[position].participator.full_name
        holder.civChatPerson.load(listRequest[position].participator.dp)
        holder.tvDetails.text = listRequest[position].participator.email

        holder.ivCancelRequest.setOnClickListener {

            selectParti.categoryName(listRequest[position].participator.id.toString())
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