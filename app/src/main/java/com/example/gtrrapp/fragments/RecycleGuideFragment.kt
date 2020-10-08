package com.example.gtrrapp.fragments

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gtrrapp.R
import com.example.gtrrapp.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import edmt.dev.edmtdevcognitivevision.Contract.AnalysisResult
import edmt.dev.edmtdevcognitivevision.VisionServiceRestClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recycle_guide.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
import org.w3c.dom.Text
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RecycleGuideFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private var param1: String? = null
    private var param2: String? = null

    //INITIALIZE THE CONNECTION
    internal val visionServiceClient = VisionServiceRestClient(API_KEY, API_LINK)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.plastic_bottle2)
        img_process.setImageBitmap(bitmap)

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        val inputStream = ByteArrayInputStream(outputStream.toByteArray())

        analysebtn.setOnClickListener{
            val visionTask = object: AsyncTask<InputStream, String, String>(){
                var progressDialog = ProgressDialog(activity)

                override  fun onPreExecute(){
                    progressDialog.show()
                }

                override fun onProgressUpdate(vararg values: String?) {
                    progressDialog.setMessage(values[0])
                }

                override fun doInBackground(vararg params: InputStream?): String {
                    try{
                        publishProgress("Recognising...")
                        val features = arrayOf("Description")
                        val details = arrayOf<String>()

                        val result = visionServiceClient.analyzeImage(params[0],features,details)

                        return Gson().toJson(result)
                    }catch (e:Exception){

                        return ""
                    }
                }
                override fun onPostExecute(result: String?) {
                    progressDialog.dismiss()

                    val result = Gson().fromJson<AnalysisResult>(result, AnalysisResult::class.java)
                    val result_text = StringBuilder()
                    for (caption in result.description.captions)
                        result_text.append(caption.text)
                    display_result.text=result_text.toString()
                }

            }

            visionTask.execute(inputStream)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_recycle_guide, container, false)
        return viewOfLayout

    }

    companion object {
        val API_KEY="e5a0a35d72d342dc9d5fb0a8745b38fc"
        val API_LINK="https://gtrr.cognitiveservices.azure.com/vision/v1.0"
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}