package com.example.gtrrapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gtrrapp.R
import com.example.gtrrapp.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import org.w3c.dom.Text


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private var param1: String? = null
    private var param2: String? = null

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //UI elements
    private var username: TextView? = null
    private var email: TextView? = null
    private var gender : TextView? = null
    private var DOB: TextView? = null
    private var bio: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        initialise()
    }

    //INITIALISE THE VALUES
    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("users")
        mAuth = FirebaseAuth.getInstance()


        username = view!!.findViewById(R.id.Display_Username) as TextView
        email = view!!.findViewById(R.id.Display_email) as TextView
        gender = view!!.findViewById(R.id.Display_gender) as TextView
        DOB = view!!.findViewById(R.id.Display_dob) as TextView
        bio = view!!.findViewById(R.id.Display_bio) as TextView
    }

    //FETCH CURRENT USER FROM FIREBASE
    override fun onStart() {
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        //ASSIGN EACH ATTAINED VALUE TO CORRESPONDING VIEWS
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                username!!.text = snapshot.child("username").value as String
                email!!.text = snapshot.child("email").value as String
                gender!!. text = snapshot.child("gender").value as String
                DOB!!. text = snapshot.child("dob").value as String
                bio!!.text = snapshot.child("bio").value as String

                val image = snapshot.child("profileImageUrl")?.value!!.toString()
                Picasso.get().load(image).into(ib_profile_pic)
                Log.d("UserFrag", image)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_user, container, false)
        return viewOfLayout

    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}