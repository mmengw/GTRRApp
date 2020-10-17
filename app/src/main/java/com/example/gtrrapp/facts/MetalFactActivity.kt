package com.example.gtrrapp.facts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.activity_facts.*

class MetalFactActivity : AppCompatActivity() {

    //SLIDE SHOW
    var sampleImages: IntArray = intArrayOf(
        R.drawable.metal1,
        R.drawable.metal2,
        R.drawable.metal3
    )

    var Metal = arrayOf(
        "Metal 1",
        "Metal 2",
        "Metal 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        Plastic_carousel.pageCount = Metal.size

        Plastic_carousel.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        Plastic_carousel.setImageClickListener { position ->

        }
        TextFact.text =
            "Did you know manufacturing recycled metal items requires an estimated 17 " +
                    "times less energy than manufacturing the same items from newly mined metal? Keep this " +
                    "in mind when replacing washing machines, cookers, ovens or hobs. They all contain metal which can be recycled. " +
                    "Recycle IT breakdown and recycle large household appliances daily. These include washing machines, dishwashers, and tumble dryers."

    }
}