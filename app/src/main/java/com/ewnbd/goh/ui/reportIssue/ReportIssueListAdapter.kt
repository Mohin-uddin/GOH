package com.ewnbd.goh.ui.reportIssue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.Result
import com.ewnbd.goh.ui.prefarence.InterestAdapter
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_issue_list.*

class ReportIssueListAdapter  :
    RecyclerView.Adapter<ReportIssueListAdapter.ReportIssueListHolder>() {
    lateinit var context:Context
    var statePos = -1
    var listRating =ArrayList<Result>(emptyList())
    class ReportIssueListHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportIssueListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return ReportIssueListHolder(
            layoutInflater.inflate(
                R.layout.item_issue_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReportIssueListHolder, position: Int) {
        holder.rbReportIssue.isChecked = statePos==position

        holder.rbReportIssue.text = listRating[position].issue_cat
        holder.rbReportIssue.setOnClickListener{
            if (statePos==position){
                ConstValue.issueStateCategoryId = ""
                statePos=-1
            }
            else {
                ConstValue.issueStateCategoryId = listRating[position].id.toString()
                    statePos = position
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return  listRating.size
    }
    fun updateList(listUpdate : List<Result>){
        listRating.clear()
        listRating.addAll(listUpdate)
        notifyDataSetChanged()
    }
}