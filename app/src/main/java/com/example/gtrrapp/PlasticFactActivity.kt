package com.example.gtrrapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_plastic.*
import kotlinx.android.synthetic.main.activity_userprofile.*
import kotlinx.android.synthetic.main.fragment_user.*


class PlasticFactActivity : AppCompatActivity () {

    //SLIDE SHOW
    var sampleImages : IntArray = intArrayOf(
        R.drawable.pickplastic,
        R.drawable.plasticmountain,
        R.drawable.plastic
    )

    var cities = arrayOf(
        "Plastic 1",
        "Plastic 2",
        "Plastic 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plastic)

        Plastic_carousel.pageCount = cities.size

        Plastic_carousel.setImageListener{ position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        Plastic_carousel.setImageClickListener{position ->

        }

    }
}
