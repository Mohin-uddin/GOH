package com.ewnbd.goh.ui.nearbyActivitys.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.ui.nearbyActivitys.CategorySelection
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_near_by_shop_category.*

class NearActivitiesItemCategoryAdapter(val categorySelection: CategorySelection) :
    RecyclerView.Adapter<NearActivitiesItemCategoryAdapter.NearActivitiesItemCategoryHolder>() {
    var listNearCategory =ArrayList<NearActivitiesCategoryListName>(emptyList())
    lateinit var context:Context
    class NearActivitiesItemCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NearActivitiesItemCategoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return NearActivitiesItemCategoryHolder(
            layoutInflater.inflate(
                R.layout.item_near_by_shop_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NearActivitiesItemCategoryHolder, position: Int) {
        holder.tvCategoryActivitiesName.text=listNearCategory[position].name

        if(position==0){
            holder.tvCategoryActivitiesName.background=context.getDrawable(R.drawable.curved_shape_btn_back_green)
            holder.tvCategoryActivitiesName.setTextColor(Color.parseColor("#FFFFFF"))
        }
        else
        {
            holder.tvCategoryActivitiesName.background=context.getDrawable(R.drawable.curved_shape_btn_back_white)
            holder.tvCategoryActivitiesName.setTextColor(Color.parseColor("#080D15"))
        }
        holder.itemView.setOnClickListener {
            categorySelection.selectPosition(listNearCategory[position])
        }
    }

    override fun getItemCount(): Int {
       return listNearCategory.size
    }

    fun categoryUpdateList(category: List<NearActivitiesCategoryListName>){
        listNearCategory.clear()
        listNearCategory.addAll(category)
        notifyDataSetChanged()
    }
}