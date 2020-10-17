package com.example.gtrrapp.facts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.activity_facts.*

class GlassFactActivity: AppCompatActivity () {

    //SLIDE SHOW
    var sampleImages : IntArray = intArrayOf(
        R.drawable.glass1,
        R.drawable.glass2,
        R.drawable.glass3
    )

    var glass = arrayOf(
        "glass 1",
        "glass 2",
        "glass 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        Plastic_carousel.pageCount = glass.size

        Plastic_carousel.setImageListener{ position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        Plastic_carousel.setImageClickListener{position ->

        }
        TextFact.text =
            "Glass takes 1,000,000 years to fully degrade in a landfill.\n" +
                    "\n" +
                    "Recycling glass takes 30% of the energy required to produce glass from raw materials.\n" +
                    "\n" +
                    "The United States throws away enough glass every week to fill a 1,350-foot building.\n" +
                    "\n" +
                    "Recycling one glass bottle saves enough energy to light a 100-watt lightbulb for four hours.\n" +
                    "\n" +
                    "Glass never wears out and can be recycled forever."
    }
}