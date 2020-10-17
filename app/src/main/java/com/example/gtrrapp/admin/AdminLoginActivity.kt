package com.example.gtrrapp.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin_login.*

class AdminLoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        //LOGIN BUTTON FUNCTION
        admin_loginbtn_Login.setOnClickListener {
            val email = gtrr_email_Login.text.toString()
            val pass = gtrr_password_Login.text.toString()

            //IF EMAIL OR PASSWORD COLUMN IS EMPTY
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter text in Email or Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //IF EMAIL IS THE ADMIN EMAIL
            if (email != "gtrradmin@email.com"){
                Toast.makeText(this, "you're not authorised to access", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //DISPLAY ACTION IN LOGCAT
            Log.d("AdminLogin","Attempt login with email/pw: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass)

                 // IF SUCCESSFUL THAN REDIRECT ADMIN TO THE HOME PAGE
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        Log.d("AdminLogin", "Successfully Login")
                        val intent = Intent(this, AdminHomeActivity::class.java)
                        //CLEAR OFF ALL THE PREVIOUS ACTIVITY STACK SO THAT IT WONT BRING THE USER BACK TO THE REGISTER SCREEN
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }else {
                        Toast.makeText(this, "Failed to Login Please try Again", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }
                .addOnFailureListener{
                    //DISPLAY ACTION IN LOGCAT
                    Log.d("AdminLogin", "Fail to Login: ${it.message}")
                }

        }
    }

}