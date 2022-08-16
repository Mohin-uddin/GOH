package com.ewnbd.goh.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.profile.Interest
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_categories.*
import kotlinx.android.synthetic.main.item_interest.*

class UserInterestAdapter: RecyclerView.Adapter<UserInterestAdapter.UserInterestHolder>() {
    lateinit var context:Context
    var interestList = ArrayList<Interest>(emptyList())
    class UserInterestHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInterestHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return UserInterestHolder(
            layoutInflater.inflate(
                R.layout.item_interest,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserInterestHolder, position: Int) {
        holder.tvNameInterest.text = interestList[position].interest_type

        when(position%5){
            0 ->{

                holder.llInterest.background=context.getDrawable(R.drawable.item_tennies_color)

            }
            1 ->{
                holder.llInterest.background=context.getDrawable(R.drawable.item_badminton_color)

            }
            2->{

                holder.llInterest.background=context.getDrawable(R.drawable.item_baseball_color)

            }
            3->{

                holder.llInterest.background=context.getDrawable(R.drawable.item_golf_color)

            }
            4->{
                holder.llInterest.background=context.getDrawable(R.drawable.item_squash_color)

            }
        }
    }

    override fun getItemCount(): Int {
        return interestList.size
    }

    fun interestUpdateList(interest: List<Interest>){
        interestList.clear()
        interestList.addAll(interest)
        notifyDataSetChanged()
    }


}