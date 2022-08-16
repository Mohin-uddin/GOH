package com.ewnbd.goh.ui.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.Result
import com.ewnbd.goh.ui.fragment.adapter.CategoriesAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_categories.*
import kotlinx.android.synthetic.main.item_interst.*
import kotlinx.android.synthetic.main.item_interst.view.*

class FilterCategoryAdapter(val selectCategoryInterface: SelectCategoryInterface) : RecyclerView.Adapter<FilterCategoryAdapter.FilterCategoryHolder>() {
    lateinit var context : Context
    var categoryList = ArrayList<Result>(emptyList())
    var selectedItem = HashMap<Int,Boolean>()
    class FilterCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCategoryHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return FilterCategoryHolder(
            layoutInflater.inflate(
                R.layout.iteam_category_filter,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterCategoryHolder, position: Int) {
        holder.tvNamePre.text=categoryList[position].cat_name
        if (selectedItem[position]!=null){
            if (selectedItem[position]!!){
                setBack(position,holder.llInterest)
            }else{
                unSelect(position,holder.llInterest)
            }
        }else{
            selectedItem[position]=false
            unSelect(position,holder.llInterest)
        }
        when(position%3){
            0->{
                holder.llInterest.setBackgroundResource(R.drawable.music_back)
            }
            1->{
                holder.llInterest.setBackgroundResource(R.drawable.sports_back)
            }
            2->{
                holder.llInterest.setBackgroundResource(R.drawable.ohoto_back)
            }
        }
        holder.itemView.setOnClickListener {
            if (selectedItem[position]!=null){
                if (selectedItem[position]!!){
                    selectedItem[position]=false
                    unSelect(position,holder.llInterest)
                }else{
                    selectedItem[position]=true
                    setBack(position,holder.llInterest)
                }
            }else{
                selectedItem[position]=true
                setBack(position,holder.llInterest)
            }
            selectCategoryInterface.selectString(selectedItem)
            notifyDataSetChanged()

        }
    }
    private fun setBack(position: Int, llInterest: LinearLayoutCompat) {
        when(position%3){
            0->{
                llInterest.setBackgroundResource(R.drawable.music_border)
                llInterest.ivCheck.load(R.drawable.ic_tic)
            }
            1->{
                llInterest.setBackgroundResource(R.drawable.sports_border)
                llInterest.ivCheck.load(R.drawable.ic_tic)
            }
            2->{
                llInterest.setBackgroundResource(R.drawable.ohoto_border)
                llInterest.ivCheck.load(R.drawable.ic_tic)
            }
        }
    }
    private fun unSelect(position: Int, llInterest: LinearLayoutCompat) {
        when(position%3){
            0->{
                llInterest.setBackgroundResource(R.drawable.music_back)
                llInterest.ivCheck.load(R.drawable.ic_plus_black)
            }
            1->{
                llInterest.setBackgroundResource(R.drawable.sports_back)
                llInterest.ivCheck.load(R.drawable.ic_plus_black)
            }
            2->{
                llInterest.setBackgroundResource(R.drawable.ohoto_back)
                llInterest.ivCheck.load(R.drawable.ic_plus_black)
            }
        }
    }
    override fun getItemCount(): Int {
        return categoryList.size
    }
    fun categoryUpdateList(category: List<Result>){
        categoryList.clear()
        categoryList.addAll(category)
        notifyDataSetChanged()
    }
}