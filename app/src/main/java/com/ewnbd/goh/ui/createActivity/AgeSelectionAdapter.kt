package com.ewnbd.goh.ui.createActivity

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.age.Result
import com.ewnbd.goh.ui.fragment.adapter.CategoriesAdapter
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_days_filter.*
import kotlinx.android.synthetic.main.item_interst.*
import kotlinx.android.synthetic.main.item_interst.view.*
import kotlinx.android.synthetic.main.item_time_slot.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AgeSelectionAdapter : RecyclerView.Adapter<AgeSelectionAdapter.AgeSelectionHolder>() {

    val ageList = ArrayList<Result>(emptyList())
    lateinit var context: Context

    class AgeSelectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeSelectionHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return AgeSelectionHolder(
            layoutInflater.inflate(
                R.layout.item_interst,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AgeSelectionHolder, position: Int) {
        holder.tvNamePre.text = ageList[position].min_range.toString()+"-"+ageList[position].max_range.toString()
        if (ConstValue.ageState==position){
            holder.llInterest.background=context.getDrawable(R.drawable.curved_shape_btn_back_green)
            holder.tvNamePre.setTextColor(Color.parseColor("#FFFFFF"))
            holder.ivCheck.load(R.drawable.check_icon)
        }else{
            holder.llInterest.background=context.getDrawable(R.drawable.curved_shape_btn_back_grey)
            holder.tvNamePre.setTextColor(Color.parseColor("#000000"))
            holder.ivCheck.load(R.drawable.ic_plus_black)
        }
        holder.itemView.setOnClickListener {
            ConstValue.ageState = if (ConstValue.ageState==position){
                -1
            }else{
                position
            }

            ConstValue.age_range =  ageList[position].id
            ConstValue.age_range_id = RequestBody.create(MultipartBody.FORM, ageList[position].id.toString())
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
       return ageList.size
    }

    fun updateAge(updateAge: List<Result>){
        ageList.clear()
        ageList.addAll(updateAge)
        notifyDataSetChanged()
    }

    private fun setBack(position: Int, llInterest: LinearLayoutCompat) {
        when(position%3){
            0->{
                llInterest.setBackgroundResource(R.drawable.curved_shape_btn_back_green)
                llInterest.ivCheck.load(R.drawable.check_icon)
            }
            1->{
                llInterest.setBackgroundResource(R.drawable.sports_border)
                llInterest.ivCheck.load(R.drawable.check_icon)
            }
            2->{
                llInterest.setBackgroundResource(R.drawable.ohoto_border)
                llInterest.ivCheck.load(R.drawable.check_icon)
            }
        }
    }
}