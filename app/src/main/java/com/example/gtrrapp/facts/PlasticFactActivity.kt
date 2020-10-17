package com.example.gtrrapp.facts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.activity_facts.*


class PlasticFactActivity : AppCompatActivity () {

    //SLIDE SHOW
    var sampleImages : IntArray = intArrayOf(
        R.drawable.pickplastic,
        R.drawable.plasticmountain,
        R.drawable.plastic
    )

    var plastics = arrayOf(
        "Plastic 1",
        "Plastic 2",
        "Plastic 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        Plastic_carousel.pageCount = plastics.size

        Plastic_carousel.setImageListener{ position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        Plastic_carousel.setImageClickListener{position ->

        }
        TextFact.text = "One recycled plastics bottle is able to save enough energy to power up to 60W light bulb for 6 hours"

    }
}
