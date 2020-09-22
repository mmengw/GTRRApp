package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_login.*

class AdminLoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        admin_loginbtn_Login.setOnClickListener {
            val intent = Intent(this, AdminHomeActivity::class.java)
            startActivity(intent)
        }
    }

}