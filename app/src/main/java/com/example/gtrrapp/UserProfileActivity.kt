//package com.example.gtrrapp
//
//import android.content.ClipData
//import android.os.Bundle
//import android.util.Log
//import android.widget.EditText
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.RecyclerView
//import com.example.gtrrapp.fragments.AchievementsFragment
//import com.example.gtrrapp.fragments.LogFragment
//import com.example.gtrrapp.fragments.NewsFeedFragment
//import com.example.gtrrapp.fragments.UserFragment
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import com.squareup.picasso.Picasso
//import com.xwray.groupie.GroupAdapter
//import com.xwray.groupie.Item
//import com.xwray.groupie.ViewHolder
//import kotlinx.android.synthetic.main.activity_home.*
//import kotlinx.android.synthetic.main.activity_userprofile.view.*
//import java.lang.ref.Reference
//
//class UserProfileActivity:AppCompatActivity() {
//    override fun onCreate(savedInstanceState:Bundle?){
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_userprofile)
//
////        val userImage = findViewById<ImageView>(R.id.user_profile_picture)
////        val userName = findViewById<EditText>(R.id.user_profile_name)
//
//        val newsFeedFragment = NewsFeedFragment()
//        val logFragment = LogFragment()
//        val achievementsFragment = AchievementsFragment()
//        val profileFragment = UserFragment()
//
//        fetchUsers()
//        makeCurrentFragment(profileFragment)
//
//        bottom_navigation.setOnNavigationItemSelectedListener {
//            when (it.itemId){
//                R.id.ic_newsfeed -> makeCurrentFragment(newsFeedFragment)
//                R.id.ic_Log -> makeCurrentFragment(logFragment)
//                R.id.ic_achievements -> makeCurrentFragment(achievementsFragment)
//                R.id.ic_user -> makeCurrentFragment(profileFragment)
//            }
//            true
//        }
//
////        val reference = FirebaseDatabase.getInstance().getReference("Users").child("uid")
////        reference.addValueEventListener(object:ValueEventListener{
////            override fun onDataChange(data: DataSnapshot) {
////                val name = data?.child("name")?.value!!.toString().trim()
////                val image = data?.child("image")?.value!!.toString().trim()
////
////                userName.setText(name)
////                Picasso.with(applicationContext).load(image).into(userImage)
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////
////            }
////        })
//    }
//    private fun fetchUsers(){
//        val ref = FirebaseDatabase.getInstance().getReference("/users")
//        ref.addListenerForSingleValueEvent(object:ValueEventListener{
//
//            override fun onDataChange(data: DataSnapshot) {
//                val adapter = GroupAdapter<ViewHolder>()
//
//                data.children.forEach{
//                    Log.d("NewMessage",it.toString())
//                    val user=it.getValue(User::class.java)
//                    if (user != null){
//                        adapter.add(UserItem(user))
//                    }
//                }
//                //recyclerview_newmessage.adapter=adapter
//            }
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }
//    //Display current
//    private fun makeCurrentFragment (fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply{
//            replace(R.id.fl_wrapper,fragment)
//            commit()
//        }
//}
////GET USER INFORMATION
//class UserItem(val user:User): Item<ViewHolder>(){
//    override fun bind(viewHolder: ViewHolder, position: Int) {
//        //viewHolder.itemView.user_name.text=user.username
//
//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.user_profile_picture)
//    }
//}
//
