package com.ewnbd.goh.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import kotlinx.android.extensions.LayoutContainer

class NearActivitiesAdapter : RecyclerView.Adapter<NearActivitiesAdapter.NearActivitiesHolder>() {

    lateinit var context: Context
    class NearActivitiesHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearActivitiesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return NearActivitiesHolder(
            layoutInflater.inflate(
                R.layout.item_activies,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NearActivitiesHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }
}