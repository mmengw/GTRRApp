package com.example.gtrrapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.registerbtn
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //ACTION FOR THE REGISTER BUTTON
        registerbtn.setOnClickListener {
            val email = gtrrEmail.text.toString()
            val pass = gtrrPassword.text.toString()

            select_photobtn.setOnClickListener {
                Log.d("RegisterActivity","Show photo selector")
            }

            //IF EMAIL OR PASSWORD COLUMN IS LEFT BLANK SHOW TOAST
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter text in Email or Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("RegisterActivity", "Email is:" + email)
            Log.d("RegisterActivity", "Password: $pass")

            //FIREBASE AUTHENTICATION FOR CREATING NEW USERS
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)

                .addOnCompleteListener {
                    //IF NOT SUCCESSFUL
                    if (!it.isSuccessful) return@addOnCompleteListener

                    //ELSE IF SUCCESSFUL
                    Log.d("RegisterActivity", "Successfully created user with uid: ${it?.result?.user?.uid}")
                }
                .addOnFailureListener {
                    //DISPLAY ACTION IN LOGCAT
                    Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                    Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_SHORT).show()
                }

        }
    }
}