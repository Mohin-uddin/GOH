package com.ewnbd.goh.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ewnbd.goh.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_categories.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {
    lateinit var context : Context
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

        when(position){
            1 ->{
                Glide.with(context).load(R.drawable.tennis)
                    .placeholder(R.drawable.logo).into(holder.ivCategory)
                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_tennies_color)
                holder.tvCategoryName.text = "Tennis"
            }
            2 ->{
                Glide.with(context).load(R.drawable.feather)
                    .placeholder(R.drawable.logo).into(holder.ivCategory)
                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_badminton_color)
                holder.tvCategoryName.text = "Badminton"
            }
            3->{
                Glide.with(context).load(R.drawable.baseball)
                    .placeholder(R.drawable.logo).into(holder.ivCategory)
                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_baseball_color)
                holder.tvCategoryName.text = "Baseball"
            }
            4->{
                Glide.with(context).load(R.drawable.golf)
                    .placeholder(R.drawable.logo).into(holder.ivCategory)
                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_golf_color)
                holder.tvCategoryName.text = "Golf"
            }
            5->{
                Glide.with(context).load(R.drawable.squash)
                    .placeholder(R.drawable.logo).into(holder.ivCategory)
                holder.llCategoryBack.background=context.getDrawable(R.drawable.item_squash_color)
                holder.tvCategoryName.text = "Squash"
            }
        }
    }

    override fun getItemCount(): Int {
        return 6
    }
}