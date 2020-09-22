package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin_home.*

class AdminHomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val newsfeedList = generateDummyList(100)

        recycler_view.adapter = AdminHomeAdapter(newsfeedList)
        recycler_view.layoutManager= LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        admin_addNews.setOnClickListener {
            val intent = Intent(this, AdminAddNews::class.java)
            startActivity(intent)
        }
    }

    private fun generateDummyList(size :Int): List<adminItem>{
        val list = ArrayList<adminItem>()
        for (i in 0 until size) {
            val del = R.drawable.trash
            val drawable = when (i % 3){
                0 -> R.drawable.ic_user
                1 -> R.drawable.ic_news_feed
                else -> R.drawable.ic_log
            }
            val item = adminItem(drawable, "Recycling Title $i", del)
            list += item
        }
        return list
    }

    //FUNCTION FOR LOGOUT
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_LogOut->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent (this, MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
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