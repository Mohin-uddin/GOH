package com.ewnbd.goh.ui.prefarence

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.InterestItem
import com.ewnbd.goh.data.model.response_all_class.interestList.Result
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_interst.*
import kotlinx.android.synthetic.main.item_interst.view.*

class InterestAdapter(val selectInterestList: InterestList) : RecyclerView.Adapter<InterestAdapter.InterestHolder>() {
    lateinit var context:Context
    var selectInterestHas = HashMap<String,String>()
    var listRating =ArrayList<Result>(emptyList())
    class InterestHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
        get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return InterestHolder(
            layoutInflater.inflate(
                R.layout.item_interst,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InterestHolder, position: Int) {
        holder.tvNamePre.text=listRating[position].interest_type
        if (!listRating[position].state) {
           unselect(position,holder.llInterest)
        }
        else{
            setBack(position,holder.llInterest)
        }
        holder.itemView.setOnClickListener {
            if (listRating[position].state) {
                listRating[position].state=false
                selectInterestHas[listRating[position].interest_type]=""
            }
            else{
                listRating[position].state=true
                selectInterestHas[listRating[position].interest_type]=listRating[position].id.toString()
            }
            selectInterestList.interestList(selectInterestHas)
            notifyDataSetChanged()
        }
    }
    private fun unselect(position: Int, llInterest: LinearLayoutCompat){
        when (position % 3) {
            0 -> {
                llInterest.setBackgroundResource(R.drawable.music_back)
                llInterest.ivCheck.load(R.drawable.ic_plus_black)
            }
            1 -> {
                llInterest.setBackgroundResource(R.drawable.sports_back)
                llInterest.ivCheck.load(R.drawable.ic_plus_black)
            }
            2 -> {
                llInterest.setBackgroundResource(R.drawable.ohoto_back)
                llInterest.ivCheck.load(R.drawable.ic_plus_black)
            }
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

    override fun getItemCount(): Int {
        return listRating.size
    }
    fun updateList(listUpdate : List<Result>){
        listRating.clear()
        listRating.addAll(listUpdate)
        notifyDataSetChanged()
    }
}