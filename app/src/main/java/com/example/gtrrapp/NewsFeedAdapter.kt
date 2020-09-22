package com.example.gtrrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.gtrr2.newsItem
import kotlinx.android.synthetic.main.newsfeed_cardview.view.*

class NewsFeedAdapter(private val newsFeedList: List<newsItem>) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.newsfeed_cardview, parent, false)

        return NewsFeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        val currentItem = newsFeedList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView.text = currentItem.title
    }

    override fun getItemCount() = newsFeedList.size

    class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.news_imageView
        val textView: TextView = itemView.news_title
    }

}