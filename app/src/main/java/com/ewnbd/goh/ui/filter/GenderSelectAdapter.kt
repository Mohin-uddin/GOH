package com.ewnbd.goh.ui.filter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_filter_all.*
import kotlinx.android.synthetic.main.item_filter_all.view.*
import kotlinx.android.synthetic.main.item_near_by_shop_category.*

class GenderSelectAdapter : RecyclerView.Adapter<GenderSelectAdapter.GenderSelectHolder>() {
    var state=-1
    lateinit var context:Context
    class GenderSelectHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenderSelectHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return GenderSelectHolder(
            layoutInflater.inflate(
                R.layout.item_filter_all,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenderSelectHolder, position: Int) {
        when(position){
            0->{
                holder.tvNameOfItem.text="Any"
            }
            1->{
                holder.tvNameOfItem.text="Female"
            }
            2->{
                holder.tvNameOfItem.text="Male"
            }
        }


        if (state==position){
            holder.tvNameOfItem.background=context.getDrawable(R.drawable.curved_shape_btn_back_green)
            holder.tvNameOfItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.tvNameOfItem.background=context.getDrawable(R.drawable.curved_shape_btn_back_white)
            holder.tvNameOfItem.setTextColor(Color.parseColor("#080D15"))
        }


        holder.itemView.setOnClickListener {
            if (holder.itemView.tvNameOfItem.text.toString()=="Male"){
                ConstValue.activty_gender = "MALE"
            }
            else if(holder.itemView.tvNameOfItem.text.toString()=="Female"){
                ConstValue.activty_gender = "FEMALE"
            }
            else{
                ConstValue.activty_gender = "Any"
            }
            state=position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}