package com.ewnbd.goh.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewnbd.goh.R
import kotlinx.android.extensions.LayoutContainer

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {
    lateinit var context:Context
    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView),LayoutContainer
    {
        override val containerView: View?
            get() = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context=parent.context
        return ChatHolder(
            layoutInflater.inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

}