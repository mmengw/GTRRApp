package com.example.gtrrapp.fragments

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.gtrrapp.*
import com.example.gtrrapp.admin.AdminHomeActivity
import com.example.gtrrapp.facts.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_achievements.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AchievementsFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Plasticbtn.setOnClickListener{
            val intent = Intent (getActivity(), PlasticFactActivity::class.java)
            getActivity()?.startActivity(intent)
        }
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("To unlock this achievement you have to recycle this material")
            Glassbtn.setOnClickListener{
                //IF YES WAS SELECTED ON THE DIALOG ALERT
                builder.setPositiveButton("OK"){dialog, id ->
                    dialog.dismiss()
                }
                //SHOW THE DIALOG AND ONCE DONE RETURN TO THE HOME PAGE
                builder.show()
            }
            Metalbtn.setOnClickListener{
                //IF YES WAS SELECTED ON THE DIALOG ALERT
                builder.setPositiveButton("OK"){dialog, id ->
                    dialog.dismiss()
                }
                //SHOW THE DIALOG AND ONCE DONE RETURN TO THE HOME PAGE
                builder.show()
            }
            Paperbtn.setOnClickListener{
                //IF YES WAS SELECTED ON THE DIALOG ALERT
                builder.setPositiveButton("OK"){dialog, id ->
                    dialog.dismiss()
                }
                //SHOW THE DIALOG AND ONCE DONE RETURN TO THE HOME PAGE
                builder.show()
            }
            Woodbtn.setOnClickListener{
                //IF YES WAS SELECTED ON THE DIALOG ALERT
                builder.setPositiveButton("OK"){dialog, id ->
                    dialog.dismiss()
                }
                //SHOW THE DIALOG AND ONCE DONE RETURN TO THE HOME PAGE
                builder.show()
            }
            Oilbtn.setOnClickListener{
                //IF YES WAS SELECTED ON THE DIALOG ALERT
                builder.setPositiveButton("OK"){dialog, id ->
                    dialog.dismiss()
                }
                //SHOW THE DIALOG AND ONCE DONE RETURN TO THE HOME PAGE
                builder.show()
            }
        fetchRType()
    }

    private fun fetchRType(){
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref= FirebaseDatabase.getInstance().getReference("RecycleLog/$uid")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot){
                data.children.forEach{
                    val log = it.getValue(RecycleLog::class.java)
                    val type = log?.rtype
                    if ( type == "Glass"){
                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.glass)
                        Glassbtn.setImageBitmap(bitmap)
                        Glassbtn.setOnClickListener{
                            val intent = Intent (getActivity(), GlassFactActivity::class.java)
                            getActivity()?.startActivity(intent)
                        }
                    }else if ( type == "Metal"){
                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.metal)
                        Metalbtn.setImageBitmap(bitmap)
                        Metalbtn.setOnClickListener{
                            val intent = Intent (getActivity(), MetalFactActivity::class.java)
                            getActivity()?.startActivity(intent)
                        }
                    }else if ( type == "Paper"){
                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.paper)
                        Paperbtn.setImageBitmap(bitmap)
                        Paperbtn.setOnClickListener{
                            val intent = Intent (getActivity(), PaperFactActivity::class.java)
                            getActivity()?.startActivity(intent)
                        }
                    }else if ( type == "Wood"){
                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.wood)
                        Woodbtn.setImageBitmap(bitmap)
                        Woodbtn.setOnClickListener{
                            val intent = Intent (getActivity(), woodFactActivity::class.java)
                            getActivity()?.startActivity(intent)
                        }
                    }else if ( type == "Oil"){
                        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.oil)
                        Oilbtn.setImageBitmap(bitmap)
                        Oilbtn.setOnClickListener{
                            val intent = Intent (getActivity(), OilFactActivity::class.java)
                            getActivity()?.startActivity(intent)
                        }
                    }
                }
            }
            override fun onCancelled (error: DatabaseError){
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_achievements, container, false)
        return viewOfLayout
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            AchievementsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

