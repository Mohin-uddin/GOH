package com.ewnbd.goh.ui.fragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.Result
import com.ewnbd.goh.ui.nearbyActivitys.CategoryWiseActivity
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_categories.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {
    lateinit var context : Context
    val newCategoryList = ArrayList<NearActivitiesCategoryListName>()
    var categoryList = ArrayList<Result>(emptyList())
    class CategoriesHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return CategoriesHolder(
            layoutInflater.inflate(
                R.layout.item_categories,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        holder.tvCategoryName.text = categoryList[position].cat_name
        Glide.with(context).load(categoryList[position].cat_icon)
            .placeholder(R.drawable.logo).into(holder.ivCategory)
        when(position%5){
            0 ->{

                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_tennies_color)

            }
            1 ->{
                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_badminton_color)

            }
            2->{

                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_baseball_color)

            }
            3->{

                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_golf_color)

            }
            4->{
                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_squash_color)

            }
        }
        holder.itemView.setOnClickListener{
            ConstValue.categoryWisedata = newCategoryList[position]
            context.startActivity(Intent(context, CategoryWiseActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
    fun categoryUpdateList(category: List<Result>){
        newCategoryList.clear()
        for (item in category){
            newCategoryList.add(NearActivitiesCategoryListName(item.cat_name,item.id))
        }
        categoryList.clear()
        categoryList.addAll(category)
        notifyDataSetChanged()
    }
}