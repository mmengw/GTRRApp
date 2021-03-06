package com.example.gtrrapp.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.admin_delete_news_cardview.view.*

class AdminHomeAdapter (private val adminFeedList: List<adminItem>) : RecyclerView.Adapter<AdminHomeAdapter.adminHomeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adminHomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.admin_delete_news_cardview, parent, false)

        return adminHomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adminHomeViewHolder, position: Int) {
        val currentItem = adminFeedList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.title
        holder.deleteView.text = currentItem.title
    }

    override fun getItemCount() = adminFeedList.size

    class adminHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.admin_imageView
        val textView: TextView = itemView.admin_title
        val deleteView : TextView = itemView.admin_deletebtn
    }

}