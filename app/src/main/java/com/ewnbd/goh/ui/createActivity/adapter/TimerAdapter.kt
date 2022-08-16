package com.ewnbd.goh.ui.createActivity.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.timeListResponse.Result
import com.ewnbd.goh.utils.ConstValue
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_filter_all.*
import kotlinx.android.synthetic.main.item_near_by_shop_category.*
import kotlinx.android.synthetic.main.item_time_slot.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.sql.Date
import java.text.ParseException
import java.text.SimpleDateFormat

class TimerAdapter : RecyclerView.Adapter<TimerAdapter.TimerHolder>() {
    lateinit var context : Context
    var timeList = ArrayList<Result>(emptyList())
    var state = -1
    class TimerHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return TimerHolder(
            layoutInflater.inflate(
                R.layout.item_time_slot,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TimerHolder, position: Int) {
         holder.tvTime.text = Convert24to12(timeList[position].start_time)+"-"+Convert24to12(timeList[position].end_time)
        if (state==position){
            holder.tvTime.background=context.getDrawable(R.drawable.curved_shape_btn_back_green)
            holder.tvTime.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            holder.tvTime.background=context.getDrawable(R.drawable.curved_shape_btn_back_grey)
            holder.tvTime.setTextColor(Color.parseColor("#080D15"))
        }

        holder.itemView.setOnClickListener {
            if (state==position){
                state=-1
            }else
                state=position
            ConstValue.startTime = RequestBody.create(MultipartBody.FORM, timeList[position].id.toString())
            ConstValue.activity_timeId =timeList[position].id
            ConstValue.activity_time = holder.tvTime.text.toString()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    fun timeUpdateList(time: List<Result>){
        Log.e("dateSetCheck", "timeUpdateList: ${time.toString()}" )
        timeList.clear()
        timeList.addAll(time)
        notifyDataSetChanged()
    }

    fun Convert24to12(time: String?): String? {
        var convertedTime = ""
        try {
            val displayFormat = SimpleDateFormat("hha")
            val parseFormat = SimpleDateFormat("HH:mm:ss")
            val date: java.util.Date? = parseFormat.parse(time)
            convertedTime = displayFormat.format(date)
            println("convertedTime : $convertedTime")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertedTime
        //Output will be 10:23 PM
    }
}