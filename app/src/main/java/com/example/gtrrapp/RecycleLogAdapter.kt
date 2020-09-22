package com.example.gtrrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.log_cardview.view.*

class RecycleLogAdapter (private val recycleLogList:List<recycleLogItem>): RecyclerView.Adapter<RecycleLogAdapter.RecycleLogViewwHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleLogViewwHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.log_cardview, parent, false)

        return RecycleLogViewwHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecycleLogViewwHolder, position: Int) {
        val currentItem = recycleLogList[position]

        holder.textView1.text= currentItem.title
        holder.textView2.text = currentItem.des
        holder.textView3.text = currentItem.date
    }

    override fun getItemCount() = recycleLogList.size

    class RecycleLogViewwHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textView1 : TextView = itemView.recycle_log_title
        val textView2: TextView = itemView.recycle_log_description
        val textView3: TextView = itemView.post_timedate
    }

}