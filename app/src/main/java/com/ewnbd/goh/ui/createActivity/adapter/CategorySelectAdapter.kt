package com.ewnbd.goh.ui.createActivity.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.NearActivitiesCategoryListName
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.Result
import com.ewnbd.goh.ui.createActivity.SelectCategoryname
import com.ewnbd.goh.utils.ConstValue
import com.ewnbd.goh.utils.gone
import com.ewnbd.goh.utils.show
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category_select.*
import kotlinx.android.synthetic.main.item_time_slot.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CategorySelectAdapter(val select: SelectCategoryname): RecyclerView.Adapter<CategorySelectAdapter.CategorySelectHolder>() {
    lateinit var context : Context
    var categoryList = ArrayList<Result>(emptyList())
    var state = -1
    class CategorySelectHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySelectHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return CategorySelectHolder(
            layoutInflater.inflate(
                R.layout.item_category_select,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategorySelectHolder, position: Int) {
        holder.tvCategorySelectName.text = categoryList[position].cat_name
        if (position==ConstValue.state){
            holder.tvCategorySelectName.setTextColor(Color.parseColor("#41B06B"))
            holder.ivCheck.show()
        }
        else{
            holder.tvCategorySelectName.setTextColor(Color.parseColor("#101E16"))
            holder.ivCheck.gone()
        }
        holder.itemView.setOnClickListener {
            select.categoryName(categoryList[position].cat_name)
            ConstValue.state=position
            ConstValue.categoryId=categoryList[position].id.toString()
            ConstValue.category_id = RequestBody.create(MultipartBody.FORM,categoryList[position].id.toString() )
            notifyDataSetChanged()
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