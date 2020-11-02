package com.example.gtrrapp.admin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gtrrapp.MainActivity
import com.example.gtrrapp.R
import com.example.gtrrapp.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_admin_user_management.*
import kotlinx.android.synthetic.main.admin_delete_user_cardview.view.*

class AdminUserManagement : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_user_management)

        //RECYCLERVIEW LAYOUT
        recyclerview_manageUser.layoutManager= LinearLayoutManager(this)
        recyclerview_manageUser.setHasFixedSize(true)

        fetchUsers()
    }

    //FETCHING NEWS FROM THE FIREBASE DATABASE
    private fun fetchUsers(){
        val recId= FirebaseDatabase.getInstance().getReference("/RecycleLog")
        val ref= FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(data: DataSnapshot){

                //FUNCTION TO FETCH ALL THE DATA AVAILABLE FOR THE NEWS IN FIREBASE DATABASE
                val adapter = GroupAdapter<ViewHolder>()
                data.children.forEach{
                    val userdata = it.getValue(User::class.java)
                    if (userdata != null){
                        adapter.add(adminUM(userdata))
                    }
                }

                //FUNCTION FOR DELETING EACH NEWS DATA WHILE LONG PRESS ON THE CARDVIEW
                adapter.setOnItemLongClickListener(){item, view ->
                    val adminUserManItem = item as adminUM

                    //SHOW ALERT DIALOG WHEN LONG PRESS ON THE CARDVIEW
                    val builder = AlertDialog.Builder(this@AdminUserManagement)
                    builder.setTitle("Are You Sure You Want To Delete This User Account?")

                    //IF YES WAS SELECTED ON THE DIALOG ALERT
                    builder.setPositiveButton(android.R.string.yes){dialog, id ->
                        val userID = adminUserManItem.users.uid
                        ref.child("$userID").removeValue()
                        recId.child("$userID").removeValue()
                        val intent = Intent(this@AdminUserManagement, AdminUserManagement::class.java)
                        finish()
                        startActivity(intent)
                    }

                    //IF NO WAS SELECTED ON THE DIALOG ALERT
                    builder.setNegativeButton(android.R.string.no){dialog, id ->
                        dialog.dismiss()
                    }

                    //SHOW THE DIALOG AND ONCE DONE RETURN TO THE HOME PAGE
                    builder.show()
                    return@setOnItemLongClickListener true

                }
                recyclerview_manageUser.adapter = adapter
            }
            override fun onCancelled (error: DatabaseError){

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

//ALLOCATING NEWS DATA VALUE TO ITS CORRESPONDING ITEMVIEW
class adminUM(val users: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.userName.text = users.username
        viewHolder.itemView.userEmail.text = users.email
        Picasso.get().load(users.profileImageUrl).into(viewHolder.itemView.admin_UserManimageView)
    }

    override fun getLayout(): Int {
        return R.layout.admin_delete_user_cardview
    }

}