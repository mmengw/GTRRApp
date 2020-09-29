package com.example.gtrr.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gtrr2.newsItem
import com.example.gtrrapp.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_news_feed.*
import kotlinx.android.synthetic.main.newsfeed_cardview.*
import kotlinx.android.synthetic.main.newsfeed_cardview.view.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


open class newsFeedFragment : Fragment() { //OnNewsItemClickListener
    private var param1: String? = null
    private var param2: String? = null

//    private var url : String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        recyclerview_newsfeed.layoutManager=LinearLayoutManager(this.context)
        recyclerview_newsfeed.setHasFixedSize(true)

        fetchNews()

//        RL_Link.setOnClickListener{
//            val link = url
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data= Uri.parse(link)
//            startActivity(intent)
//        }

    }

//    override fun onItemClick(item: newsItem, position: Int) {
//
//    }

    private fun fetchNews(){
        val ref=FirebaseDatabase.getInstance().getReference("/News")
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(data: DataSnapshot){
                val adapter = GroupAdapter<ViewHolder>()
                data.children.forEach{
                    val news = it.getValue(News::class.java)
                    if (news != null){
                        adapter.add(NewsItem(news))
                    }
                }

                adapter.setOnItemClickListener{item, view ->
                    val newsItem = item as NewsItem

                    val intent = Intent(getActivity(),WebViewActivity::class.java)
                    intent.putExtra("NEWS_KEY", newsItem.news.nlink)
                    startActivity(intent)
                }

                recyclerview_newsfeed.adapter = adapter
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
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
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
class NewsItem(val news:News): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.news_title.text = news.ntitle
        Picasso.get().load(news.coverImgUrl).into(viewHolder.itemView.news_imageView)
    }

    override fun getLayout(): Int {
        return R.layout.newsfeed_cardview
    }

}