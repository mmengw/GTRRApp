package com.example.gtrrapp.admin

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.MainActivity
import com.example.gtrrapp.R
import com.example.gtrrapp.RecycleLog
import com.example.gtrrapp.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_admin_analytics.*

class AdminAnalytics : AppCompatActivity (){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_analytics)
        fetchUser()
        fetchRecycle()
    }

    private fun fetchUser(){
        val count = userNum
        val ref = FirebaseDatabase.getInstance().getReference("users")

        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                data.children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null){
                        val child = data.childrenCount
                        val animator = ValueAnimator.ofInt(0, child.toInt())
                        animator.setDuration(5000)
                        animator.addUpdateListener { animation ->
                            count.text = animation.animatedValue.toString()
                        }
                        animator.start()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    private fun fetchRecycle(){
        val count = recycleNum
        val ref = FirebaseDatabase.getInstance().getReference("RecycleLog")

        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                data.children.forEach {
                    val recycleLog = it.getValue(RecycleLog::class.java)
                    if (recycleLog != null){
                        val child = data.childrenCount
                        val animator = ValueAnimator.ofInt(0, child.toInt())
                        animator.setDuration(5000)
                        animator.addUpdateListener { animation ->
                            count.text = animation.animatedValue.toString()
                        }
                        animator.start()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //FUNCTION FOR MENU NAVIGATION
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_Home ->{
                val intent = Intent (this, AdminHomeActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_UserDel ->{
                val intent = Intent (this, AdminUserManagement::class.java)
                startActivity(intent)
            }
            R.id.menu_Analytics ->{
                val intent = Intent (this, AdminAnalytics::class.java)
                startActivity(intent)
            }
            R.id.menu_LogOut ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent (this, MainActivity::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    //DISPLAY LOG OUT BUTTON ON THE TOP NAV MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
}