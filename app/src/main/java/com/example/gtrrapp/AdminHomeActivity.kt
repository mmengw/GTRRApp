package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gtrr.fragments.NewsItem
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_admin_home.*
import kotlinx.android.synthetic.main.admin_delete_news_cardview.view.*

class AdminHomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)


        recyclerview_adminFeed.layoutManager= LinearLayoutManager(this)
        recyclerview_adminFeed.setHasFixedSize(true)

        fetchNews()

        admin_addNews.setOnClickListener {
            val intent = Intent(this, AdminAddNews::class.java)
            startActivity(intent)
        }
    }
    private fun fetchNews(){
        val ref= FirebaseDatabase.getInstance().getReference("/News")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot){
                val adapter = GroupAdapter<ViewHolder>()
                data.children.forEach{
                    val news = it.getValue(News::class.java)
                    if (news != null){
                        adapter.add(newsItem(news))
                    }
                }

//                adapter.setOnItemClickListener{item, view ->
//                    val newsItem = item as NewsItem
//                    val intent = Intent(this@AdminHomeActivity,WebViewActivity::class.java)
//                    intent.putExtra("NEWS_KEY", newsItem.news.nlink)
//                    startActivity(intent)
//                }

                recyclerview_adminFeed.adapter = adapter
            }
            override fun onCancelled (error: DatabaseError){

            }
        })
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
class newsItem(val news:News): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.admin_title.text = news.ntitle
        Picasso.get().load(news.coverImgUrl).into(viewHolder.itemView.admin_imageView)
    }

    override fun getLayout(): Int {
        return R.layout.admin_delete_news_cardview
    }

}