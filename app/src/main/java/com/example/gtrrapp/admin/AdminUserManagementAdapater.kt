package com.example.gtrrapp.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.admin_delete_user_cardview.view.*

class AdminUserManagementAdapater (private val adminUserManList: List<adminUserManItem>) : RecyclerView.Adapter<AdminUserManagementAdapater.adminUserManViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adminUserManViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.admin_delete_user_cardview, parent, false)

        return adminUserManViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adminUserManViewHolder, position: Int) {
        val currentItem = adminUserManList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.userName
        holder.textView2.text = currentItem.userEmail
        holder.deleteView.text = currentItem.userEmail
    }

    override fun getItemCount() = adminUserManList.size

    class adminUserManViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.admin_UserManimageView
        val textView1: TextView = itemView.userName
        val textView2: TextView = itemView.userEmail
        val deleteView : TextView = itemView.admin_deletebtn
    }

}