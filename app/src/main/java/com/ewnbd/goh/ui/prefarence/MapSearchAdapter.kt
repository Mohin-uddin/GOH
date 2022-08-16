package com.ewnbd.goh.ui.prefarence

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import com.ewnbd.goh.data.model.response_all_class.mapSearch.Prediction
import com.google.android.material.transition.Hold
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_location.*

class MapSearchAdapter(val clickData: OnItemClickListener) : RecyclerView.Adapter<MapSearchAdapter.MaoSearchHolder>() {

    private var oldMapListList = emptyList<Prediction>()
    class MaoSearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        override val containerView: View
            get() = itemView
    }
    class MapSearchDiffUtil(
        private val oldMapResultItem : List<Prediction>,
        private val newMapResultItem : List<Prediction>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return  oldMapResultItem.size
        }

        override fun getNewListSize(): Int {
            return newMapResultItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMapResultItem[oldItemPosition].place_id == newMapResultItem[newItemPosition].place_id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMapResultItem == newMapResultItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaoSearchHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return MaoSearchHolder(
            layoutInflater.inflate(
                R.layout.item_location,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MaoSearchHolder, position: Int) {
        holder.tvLocationName.text= oldMapListList[position].structured_formatting.main_text
        holder.tvDetails.text= oldMapListList[position].description
        holder.itemView.setOnClickListener {
            clickData.itemClickListener(oldMapListList[position])
        }
    }

    override fun getItemCount(): Int {
        return oldMapListList.size
    }
    fun setUpdateData(newCustomerList : List<Prediction>){
        val diffUtil = MapSearchDiffUtil(oldMapListList,newCustomerList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        oldMapListList = newCustomerList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}