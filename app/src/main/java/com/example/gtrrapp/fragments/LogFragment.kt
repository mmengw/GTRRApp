package com.example.gtrrapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gtrr2.newsItem
import com.example.gtrrapp.*
import kotlinx.android.synthetic.main.fragment_log.*
import kotlinx.android.synthetic.main.fragment_news_feed.*
import kotlinx.android.synthetic.main.fragment_news_feed.recycler_view


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LogFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        add_new_Logbtn.setOnClickListener{
            val intent = Intent (getActivity(), AddNewRecycleLogActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        val recycleLogList = generateDummyList(100)

        recycler_view.adapter = RecycleLogAdapter(recycleLogList)
        recycler_view.layoutManager= LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

    private fun generateDummyList(size :Int): List<recycleLogItem>{
        val list = ArrayList<recycleLogItem>()

        for (i in 0 until size) {
            val item = recycleLogItem("Ttle $i", "Description $i","date and time")
            list += item
        }
        return list
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            LogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}