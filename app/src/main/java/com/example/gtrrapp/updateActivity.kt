package com.example.gtrrapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_update_settings.*
import java.util.*

class UpdateActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_settings)

        //ACTION FOR THE SELECT PHOTO BUTTON
        update_photobtn.setOnClickListener {
            Log.d("updateActivity","Show photo selector")

            //REDIRECT USER TO SELECT PICTURE FROM THE DOWNLOAD FOLDER
            val intent= Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, 0)
        }

        //ACTION FOR THE Update BUTTON
        updateBtn.setOnClickListener {
            uploadImageToFirebaseStrorage()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }



    var selectedPhotoUri: Uri? = null

    //FUNCTION TO STORE IMAGE TO FIREBASE
    private fun uploadImageToFirebaseStrorage(){
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener{
                Log.d("updateActivity", "Successfully uploaded image")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("updateActivity", "File Location: $it")

                    saveUserToFriebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                Log.d("updateActivity","Fail to upload image")
            }
    }

    //STORE USER INFORMATION TO FIREBASE DATABASE
    private fun saveUserToFriebaseDatabase(profileImageUrl: String){

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                val dob = data.child("dob").value as String
                val gender = data.child("gender").value as String
                val email = data.child("email").value as String
                val user=User(uid, update_userName.text.toString() , profileImageUrl, dob, email, gender, user_bio.text.toString())
                ref.setValue(user)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


    //DISPLAYING THE SELECTED PHOTO
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data !=null){

            //PROCEED TO CHECK WHAT THE SELECTED IMAGE WAS
            Log.d("updateActivity", "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            update_photobtn.setBackgroundDrawable(bitmapDrawable)
        }
    }
}