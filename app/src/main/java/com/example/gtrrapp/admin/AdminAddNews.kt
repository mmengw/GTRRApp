package com.example.gtrrapp.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_admin_addnews.*
import java.util.*

class AdminAddNews : AppCompatActivity() {

    lateinit var newsTitle : TextView
    lateinit var newsLink : TextView
    lateinit var uploadButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_addnews)

        newsTitle = findViewById(R.id.news_title)
        newsLink = findViewById(R.id.news_link)
        uploadButton = findViewById(R.id.uploadBtn)

        //ACTION FOR THE SELECT PHOTO BUTTON
        cover_imagebtn.setOnClickListener {

            //REDIRECT USER TO SELECT PICTURE FROM THE DOWNLOAD FOLDER
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        //ACTION FOR THE UPDATE BUTTON
        uploadButton.setOnClickListener {
            uploadImageToFirebaseStorage()
            val intent = Intent(this, AdminAddNews::class.java)
            startActivity(intent)
        }
    }

    var selectedPhotoUri: Uri? = null

    //UPLOADING SELECTED IMAGE TO FIREBASE STORAGE FUNCTION
    private fun uploadImageToFirebaseStorage(){
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/News/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener{
                Log.d("AddNewsActivity", "Successfully uploaded image")

                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("AddNewsActivity", "File Location: $it")

                    saveNewsToFriebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                Log.d("AddNewsActivity","Fail to upload image")
            }
    }

    //SAVING ALL THE DATA TO FIREBASE FUNCTION
    private fun saveNewsToFriebaseDatabase(NewsCoverImageUrl: String){

        val title = newsTitle.text.toString()
        val link = newsLink.text.toString()

        if (title.isEmpty() || link.isEmpty()){
            newsTitle.error = "Please enter title and link"
        }

        val ref = FirebaseDatabase.getInstance().getReference("News")
        val newsId = ref.push().key
        val news = News (newsId,title,link,NewsCoverImageUrl)
        ref.child(newsId!!).setValue(news)
            .addOnSuccessListener {
                Log.d("AddNewsActivity", "Saved news Data to Firebase")

            }
            .addOnFailureListener{
                Log.d("AddNewsActivity","Fail to upload news data")
            }
    }

    //TO CHECK IF IMAGE WAS SELECTED
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data !=null){

            //PROCEED TO CHECK WHAT THE SELECTED IMAGE WAS
            Log.d("AddNewsActivity", "Photo was selected")

            selectedPhotoUri = data.data

        }
    }
}

//NEWS MODULE
class News(val newsid: String?, val ntitle:String, val nlink:String, val coverImgUrl:String){
    constructor() : this("","","","")
}

