package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //ACTION FOR THE LOGIN BUTTON
        gtrr_loginbtn_Login.setOnClickListener {
            val email = gtrr_email_Login.text.toString()
            val pass = gtrr_password_Login.text.toString()

            //DISPLAY ACTION IN LOGCAT
            Log.d("LoginActivity","Attempt login with email/pw: $email/***")

            //FIREBASE AUTHENTICATION WHEN USER LOGIN
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener{
                    //IF LOGIN FAILED
                    if(!it.isSuccessful)
                        Toast.makeText(this, "Failed to Login Please try Again", Toast.LENGTH_SHORT).show()
                    if(!it.isSuccessful) return@addOnCompleteListener

                    //ELSE IF LOGIN SUCCESSFUL
                    Log.d("LoginActivity", "Successfully Login")
                    val intent = Intent(this, HomeActivity::class.java)
                    //CLEAR OFF ALL THE PREVIOUS ACTIVITY STACK SO THAT IT WONT BRING THE USER BACK TO THE REGISTER SCREEN
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    //DISPLAY ACTION IN LOGCAT
                    Log.d("LoginActivity", "Fail to Login: ${it.message}")
                }
        }
    }
}