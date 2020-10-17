package com.example.gtrrapp.facts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.activity_facts.*

class PaperFactActivity : AppCompatActivity() {

    //SLIDE SHOW
    var sampleImages: IntArray = intArrayOf(
        R.drawable.paper1,
        R.drawable.paper2,
        R.drawable.paper3
    )

    var Paper = arrayOf(
        "Paper 1",
        "Paper 2",
        "Paper 3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts)

        Plastic_carousel.pageCount = Paper.size

        Plastic_carousel.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        Plastic_carousel.setImageClickListener { position ->

        }
        TextFact.text = "- Recycling 1 ton of paper saves around 682.5 gallons of oil, 26,500 liters of water and 17 trees.\n"+
                "\n" +
                "- U.S offices use 12.1 trillion sheets of paper a year.\n" +
                "\n" +
                "- Paper accounts for 25% of landfill waste and 33% of municipal waste.\n" +
                "\n" +
                "- With all the paper we waste each year, we can build a 12 foot high wall of paper from New York to California!"

    }
}