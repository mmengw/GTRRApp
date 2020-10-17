package com.example.gtrrapp.facts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.activity_facts.*

class OilFactActivity : AppCompatActivity() {

    //SLIDE SHOW
    var sampleImages: IntArray = intArrayOf(
        R.drawable.oil1,
        R.drawable.oil2,
        R.drawable.oil3
    )

    var Oil = arrayOf(
        "Oil 1",
        "Oil 2",
        "Oil 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        Plastic_carousel.pageCount = Oil.size

        Plastic_carousel.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        Plastic_carousel.setImageClickListener { position ->

        }
        TextFact.text = "- Recycling used oil would save the U.S. 1.3 million barrels of oil per day.\n" +
                "\n" +
                "- The world’s largest waste oil processing plant is located in East Chicago, Indiana. The facility is to recycle 75 million gallons per year of crankcase and industrial oil and 20 million gallons per year of oily wastewater.\n" +
                "\n" +
                "- One gallon of used oil provides the same 2.5 quarts of high quality lubricating oil as 42 gallons of crude oil."

    }
}