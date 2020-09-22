package com.example.gtrrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_admin_delete_news_cardview.view.*

class AdminHomeAdapter (private val adminFeedList: List<adminItem>) : RecyclerView.Adapter<AdminHomeAdapter.adminHomeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adminHomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_admin_delete_news_cardview, parent, false)

        return adminHomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adminHomeViewHolder, position: Int) {
        val currentItem = adminFeedList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.title
        holder.deleteView.setImageResource(currentItem.del)
    }

    override fun getItemCount() = adminFeedList.size

    class adminHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.admin_imageView
        val textView: TextView = itemView.admin_title
        val deleteView : ImageView = itemView.admin_deletebtn
    }

}