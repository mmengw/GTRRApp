package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

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

        //LOGIN BUTTON FUNCTION
        admin_login.setOnClickListener{
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        }
    }
}

