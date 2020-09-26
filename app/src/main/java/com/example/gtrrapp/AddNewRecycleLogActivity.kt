package com.example.gtrrapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_recyclelog.*
import kotlinx.android.synthetic.main.activity_register.*
import org.w3c.dom.Text

class AddNewRecycleLogActivity : AppCompatActivity(){

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    lateinit var option: Spinner
    lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recyclelog)

        option = findViewById(R.id.RecycleType) as Spinner

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
    initialise()
    }
    private fun initialise(){
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.getReference("RecycleLog")
        mAuth = FirebaseAuth.getInstance()
    }
    override fun onStart(){
        super.onStart()

        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

        updateBtn.setOnClickListener {
            val recycleLog = RecycleLog("$mUserReference",recycleTitle.text.toString(),recycleDescription.text.toString(),"${result}")
            mDatabaseReference!!.setValue(recycleLog)
        }

    }
}

class RecycleLog(val uid: String, val RTitle:String, val RDes:String, val RType:String)