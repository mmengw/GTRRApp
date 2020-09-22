package com.example.gtrr2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gtrr2.newsItem
import com.example.gtrrapp.NewsFeedAdapter
import com.example.gtrrapp.R
import kotlinx.android.synthetic.main.fragment_news_feed.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class newsFeedFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val newsfeedList = generateDummyList(200)

        recycler_view.adapter = NewsFeedAdapter(newsfeedList)
        recycler_view.layoutManager=LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    private fun generateDummyList(size :Int): List<newsItem>{
        val list = ArrayList<newsItem>()

        for (i in 0 until size) {
            val drawable = when (i % 3){
                0 -> R.drawable.ic_user
                1 -> R.drawable.ic_news_feed
                else -> R.drawable.ic_log
            }
            val item = newsItem(drawable, "Recycling Title $i")
            list += item
        }
        return list
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            newsFeedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}