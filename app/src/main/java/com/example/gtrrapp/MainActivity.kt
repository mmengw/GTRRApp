package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //LOGIN BUTTON FUNCTION
        to_login.setOnClickListener {
            Log.d("MainActivity", "Try show login activity")

            //LAUNCH LOGIN ACTIVITY SCREEN
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //REGISTER BUTTON FUNCTION
        to_register.setOnClickListener {
            //DISPLAY ACTION IN LOGCAT
            Log.d("MainActivity", "Try show register activity")

            //LAUNCH REGISTER ACTIVITY SCREEN
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

