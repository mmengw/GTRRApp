package com.example.gtrrapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrr.fragments.newsFeedFragment
import com.example.gtrrapp.fragments.LogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddNewRecycleLogActivity : AppCompatActivity(){

    lateinit var logTitle : TextView
    lateinit var logDes : TextView
    lateinit var updateButton : Button

    lateinit var option: Spinner
    lateinit var result: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recyclelog)

        option = findViewById(R.id.RecycleType) as Spinner

        logTitle = findViewById(R.id.recycleTitle)
        logDes = findViewById(R.id.recycleDescription)
        updateButton = findViewById(R.id.updateBtn)
        result = findViewById(R.id.resultType)

        val options = arrayOf("plastic","Glass","Metal","Paper","Wood","Oil")

        option.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                result.text = options.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //ACTION FOR THE UPDATE BUTTON
        updateButton.setOnClickListener {

            val title = logTitle.text.toString()
            val des = logDes.text.toString()

            //TO CHECK IF THESE COLUMN IS EMPTY
            if (title.isEmpty()) {
                logTitle.error = "Please enter a log title"
                return@setOnClickListener
            }else if (des.isEmpty()){
                logDes.error = "Please enter a log description"
                return@setOnClickListener
            }else{
                saveRecycleLogToFirebaseDatabase()
                Toast.makeText(this, "Successfully Uploaded Recycling Log", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }


    //FUNCTION FOR SAVING RECYCLE LOG INTO THE FIREBASE
    private fun saveRecycleLogToFirebaseDatabase(){

        val dateTime = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm")

        val title = logTitle.text.toString()
        val des = logDes.text.toString()
        val type = result.text.toString()
        val date = formatter.format(dateTime)


        //UPLOAD DATA TO FIREBASE TO THE DESIRED PATH
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("RecycleLog/$uid")
        val logId = ref.push().key
        val log = RecycleLog (logId!!,title,des,type,date)
        ref.child(logId!!).setValue(log)
            .addOnSuccessListener {
                Log.d("AddNewRecycleLogActivity", "Saved Log Data to Firebase")

            }
            .addOnFailureListener{
                Log.d("AddNewRecycleLogActivity","Fail to upload Log data")
            }
    }
}

//RECYCLELOG MODULE
class RecycleLog(val logId: String, val rtitle:String, val rdes:String, val rtype:String, val dateTime: String){
    constructor() : this("","","","","")
}