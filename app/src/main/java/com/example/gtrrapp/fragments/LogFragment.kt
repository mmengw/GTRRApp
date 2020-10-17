package com.example.gtrrapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gtrr.fragments.NewsItem
import com.example.gtrr2.newsItem
import com.example.gtrrapp.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_log.*
import kotlinx.android.synthetic.main.fragment_news_feed.*
import kotlinx.android.synthetic.main.log_cardview.view.*
import kotlinx.android.synthetic.main.newsfeed_cardview.view.*
import kotlinx.android.synthetic.main.newsfeed_cardview.view.news_title


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

        //ADD NEW RECYCLELOG BUTTON FUNCTION
        add_new_Logbtn.setOnClickListener{
            val intent = Intent (getActivity(), AddNewRecycleLogActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        //RECYLERVIEW FUNCTION
        recyclerview_logfeed.layoutManager=LinearLayoutManager(this.context)
        recyclerview_logfeed.setHasFixedSize(true)

        fetchLog()
    }

    //FETCHING USER LOG FROM FIREBASE
    private fun fetchLog(){

        val count = log_record
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref= FirebaseDatabase.getInstance().getReference("RecycleLog/$uid")

        //GET VALUE FROM FIREBASE OF EACH CHILD
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(data: DataSnapshot){
                val adapter = GroupAdapter<ViewHolder>()

                data.children.forEach{
                    val log = it.getValue(RecycleLog::class.java)
                    if (log != null){
                        adapter.add(RecycleLogItem(log))
                        val child = data.childrenCount
                        count.setText(child.toString())
                    }
                }
                recyclerview_logfeed.adapter = adapter
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
        return inflater.inflate(R.layout.fragment_log, container, false)
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

//ASSIGN THE VALUE TAKEN FROM FIREBASE AND PLACE IT TO THE RESPECTIVE VIEWS
class RecycleLogItem(val log:RecycleLog): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.recycle_log_title.text = log.rtitle
        viewHolder.itemView.recycle_log_description.text = log.rdes
        viewHolder.itemView.post_timedate.text = log.dateTime
    }

    override fun getLayout(): Int {
        return R.layout.log_cardview
    }

}