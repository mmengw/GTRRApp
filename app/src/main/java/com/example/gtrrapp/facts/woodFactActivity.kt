package com.example.gtrrapp.facts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.activity_facts.*

class woodFactActivity : AppCompatActivity() {

    //SLIDE SHOW
    var sampleImages: IntArray = intArrayOf(
        R.drawable.wood1,
        R.drawable.wood2,
        R.drawable.wood3
    )

    var Wood = arrayOf(
        "Wood 1",
        "Wood 2",
        "Wood 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        Plastic_carousel.pageCount = Wood.size

        Plastic_carousel.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        Plastic_carousel.setImageClickListener { position ->

        }
        TextFact.text = "Wood waste is the second-largest component of construction and demolition (C&D) debris after concrete. It contributes 20 percent to 30 percent of the building-related C&D total. Overall, wood accounts for around 10 percent of all material deposited in landfills annually."

    }
}