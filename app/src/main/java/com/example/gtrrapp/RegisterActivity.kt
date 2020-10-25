package com.example.gtrrapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.icu.util.LocaleData
import android.net.MailTo.parse
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintAttribute.parse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.registerbtn
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.gtrrPassword
import kotlinx.android.synthetic.main.activity_register.register_email
import kotlinx.android.synthetic.main.activity_register.register_photobtn
import kotlinx.android.synthetic.main.activity_register.register_userName
import kotlinx.android.synthetic.main.activity_update_settings.*
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.HttpUrl.parse
import java.net.HttpCookie.parse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Duration.parse
import java.time.Instant.parse
import java.util.*
import java.util.Date.parse
import java.util.Locale.LanguageRange.parse
import java.util.logging.Level.parse

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //ACTION FOR THE SELECT PHOTO BUTTON
        register_photobtn.setOnClickListener {
            Log.d("RegisterActivity","Show photo selector")

            //REDIRECT USER TO SELECT PICTURE FROM THE DOWNLOAD FOLDER
            val intent= Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, 0)
        }

        //ACTION FOR THE REGISTER BUTTON
        registerbtn.setOnClickListener {
            val email = register_email.text.toString()
            val pass = gtrrPassword.text.toString()
            val username = register_userName.text.toString()

            //IF EMAIL OR PASSWORD COLUMN IS LEFT BLANK SHOW TOAST
            if (email.isEmpty() || pass.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please Do Not Leave Any Column Blank", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //STORE SELECTED IMAGE TO FIREBASE
            if (selectedPhotoUri == null){
                Toast.makeText(this, "Please Select a Profile Image", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else {
                uploadImageToFirebaseStrorage()
            }

            Log.d("RegisterActivity", "Email is:" + email)
            Log.d("RegisterActivity", "Password: $pass")

            //FIREBASE AUTHENTICATION FOR CREATING NEW USERS
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)

                .addOnCompleteListener {
                    //IF NOT SUCCESSFUL
                    if(!it.isSuccessful)
                        Toast.makeText(this, "Failed to register Please try Again", Toast.LENGTH_LONG).show()
                    if (!it.isSuccessful) return@addOnCompleteListener

                    //ELSE IF SUCCESSFUL
                    Log.d("RegisterActivity", "Successfully created user with uid: ${it?.result?.user?.uid}")
                    Toast.makeText(this, "Successfully Registered a New User Account", Toast.LENGTH_SHORT).show()
                }

                .addOnFailureListener {
                    //DISPLAY ACTION IN LOGCAT
                    Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                    Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    //FUNCTION FOR UPLOAD IMAGE TO FIREBASE STORAGE
    private fun uploadImageToFirebaseStrorage(){
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener{
                Log.d("RegisterActivity", "Successfully uploaded image")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("RegisterActivity", "File Location: $it")

                    saveUserToFriebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                Log.d("RegisterActivity","Fail to upload image")
            }
    }

    //STORE USER INFORMATION TO FIREBASE DATABASE
    private fun saveUserToFriebaseDatabase(profileImageUrl: String){
        val radio : RadioButton = findViewById(GendeRradioGrp.checkedRadioButtonId)
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user=User(uid, register_userName.text.toString(), profileImageUrl,register_email.text.toString(),"${radio.text}",register_bio.text.toString())
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Saved user Data to Firebase")
                val intent = Intent(this, HomeActivity::class.java)
                //CLEAR OFF ALL THE PREVIOUS ACTIVITY STACK SO THAT IT WONT BRING THE USER BACK TO THE REGISTER SCREEN
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener{
                Log.d("RegisterActivity","Fail to upload user data")
            }
    }

    var selectedPhotoUri: Uri? = null

    //DISPLAYING THE SELECTED PHOTO
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data !=null){

            //PROCEED TO CHECK WHAT THE SELECTED IMAGE WAS
            Log.d("RegisterActivity", "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            register_photobtn.setBackgroundDrawable(bitmapDrawable)
        }
    }
}

//CREATING A USER CLASS TO IDENTIFY WHAT TO STORE IN THE DATABASE
class User(val uid:String, val username:String, val profileImageUrl: String, val email:String, val gender:String, val bio:String){
    constructor() : this("","","","","","")
}