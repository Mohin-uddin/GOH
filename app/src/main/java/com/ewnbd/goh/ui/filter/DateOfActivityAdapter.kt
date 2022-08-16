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
import kotlinx.android.synthetic.main.bottom_sheet_category.view.*
import kotlinx.android.synthetic.main.item_filter_all.*
import kotlinx.android.synthetic.main.item_filter_all.view.*
import kotlinx.android.synthetic.main.item_interst.*
import kotlinx.android.synthetic.main.item_near_by_shop_category.*

class DateOfActivityAdapter : RecyclerView.Adapter<DateOfActivityAdapter.DateOfActivityHolder>() {
    var state = -1
    lateinit var context:Context
    class DateOfActivityHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateOfActivityHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return DateOfActivityHolder(
            layoutInflater.inflate(
                R.layout.item_filter_all,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateOfActivityHolder, position: Int) {
        when(position){
            0->{
                holder.tvNameOfItem.text="Today"
            }
            1->{
                holder.tvNameOfItem.text="Tomorrow"
            }
            2->{
                holder.tvNameOfItem.text="This Week"
            }
            3->{
                holder.tvNameOfItem.text="Next Week"
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
            state=position
            ConstValue.activity_date_slot = holder.itemView.tvNameOfItem.text.toString()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}