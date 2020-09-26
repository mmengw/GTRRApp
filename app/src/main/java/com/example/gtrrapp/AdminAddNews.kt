package com.example.gtrrapp

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_recyclelog.*
import kotlinx.android.synthetic.main.activity_add_recyclelog.updateBtn
import kotlinx.android.synthetic.main.activity_admin_addnews.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.register_email
import kotlinx.android.synthetic.main.activity_register.register_photobtn
import kotlinx.android.synthetic.main.activity_register.register_userName
import kotlinx.android.synthetic.main.activity_update_settings.*
import java.util.*

class AdminAddNews : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_addnews)

        //ACTION FOR THE SELECT PHOTO BUTTON
        cover_imagebtn.setOnClickListener {

            //REDIRECT USER TO SELECT PICTURE FROM THE DOWNLOAD FOLDER
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        //ACTION FOR THE UPDATE BUTTON
        uploadBtn.setOnClickListener {
                uploadImageToFirebaseStorage()
        }
    }

    var selectedPhotoUri: Uri? = null

    private fun uploadImageToFirebaseStorage(){
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener{
                Log.d("AddNewsActivity", "Successfully uploaded image")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("AddNewsActivity", "File Location: $it")

                    savNewsToFriebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                Log.d("AddNewsActivity","Fail to upload image")
            }
    }


    private fun savNewsToFriebaseDatabase(NewsCoverImageUrl: String){

        ref.setValue(news)
            .addOnSuccessListener {
                Log.d("AddNewsActivity", "Saved news Data to Firebase")

            }
            .addOnFailureListener{
                Log.d("AddNewsActivity","Fail to upload news data")
            }
    }
}

class News(val title:String, val link:String, val CoverImgUrl:String)