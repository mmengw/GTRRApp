//package com.example.gtrrapp
//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.*
//import kotlinx.android.synthetic.main.activity_userprofile.*
//import kotlinx.android.synthetic.main.fragment_user.*
//
//
//class UserProfileActivity : AppCompatActivity () {
//
//    //Firebase references
//    private var mDatabaseReference: DatabaseReference? = null
//    private var mDatabase: FirebaseDatabase? = null
//    private var mAuth: FirebaseAuth? = null
//
//    //UI elements
//    private var username: TextView? = null
//    //private var tvLastName: TextView? = null
//    //private var tvEmail: TextView? = null
//    //private var tvEmailVerifiied: TextView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_user)
//
//        initialise()
//    }
//
//    private fun initialise() {
//        mDatabase = FirebaseDatabase.getInstance()
//        mDatabaseReference = mDatabase!!.reference!!.child("users")
//        mAuth = FirebaseAuth.getInstance()
//
//        username = findViewById<View>(R.id.tv_first_name) as TextView
//        //tvLastName = findViewById<View>(R.id.tv_last_name) as TextView
//        //tvEmail = findViewById<View>(R.id.tv_email) as TextView
//        //tvEmailVerifiied = findViewById<View>(R.id.tv_email_verifiied) as TextView
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//        val mUser = mAuth!!.currentUser
//        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
//
//        //tvEmail!!.text = mUser.email
//        //tvEmailVerifiied!!.text = mUser.isEmailVerified.toString()
//
//        mUserReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                username!!.text = snapshot.child("username").value as String
//                //tvLastName!!.text = snapshot.child("lastName").value as String
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
//    }
//}
