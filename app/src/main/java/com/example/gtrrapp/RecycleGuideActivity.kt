package com.example.gtrrapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gtrr.fragments.NewsItem
import com.google.gson.Gson
import edmt.dev.edmtdevcognitivevision.Contract.AnalysisResult
import edmt.dev.edmtdevcognitivevision.VisionServiceRestClient
import kotlinx.android.synthetic.main.activity_recycle_guide.*
import kotlinx.android.synthetic.main.fragment_achievements.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.NullPointerException
import java.util.Collections.rotate

class RecycleGuideActivity:AppCompatActivity() {

    companion object{
        val API_KEY="d14f8b0ed53d4f928addc6ec347e6ebd"
        val API_LINK="https://ra.cognitiveservices.azure.com/vision/v1.0"
        private var IMAGE_PICK_CODE = 180
    }

    lateinit var option: Spinner

    internal val visionServiceClient = VisionServiceRestClient(API_KEY, API_LINK)

    @SuppressLint("WrongThread")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_guide)

        select_image.setOnClickListener{
            pickImage()
        }

        option = findViewById(R.id.MaterialType) as Spinner

        val options = arrayOf("Plastic","Glass","Metal","Paper","Wood","Oil")
        option.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            var counter = 0
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (counter == 0){
                    counter += 1
                }else {
                    val clickLink = options.get(position)
                    val intent = Intent(this@RecycleGuideActivity, WebViewManualGuideActivity::class.java)
                    intent.putExtra("MGUIDE_KEY", "https://www.google.com/search?q=how+to+recycle+$clickLink&rlz=1C1CHBF_enMY893MY893&oq=how+to+recycle+$clickLink&aqs=chrome..69i57j0i22i30l7.8230j0j7&sourceid=chrome&ie=UTF-8")
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        ManualSrh.setOnClickListener{
            MaterialType.visibility=View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode:Int , resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                IMAGE_PICK_CODE ->{

                    //DIALOG WARNING BUILDER
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Rotate the image to have it facing up right to have a more accurate reading")
                    builder.setPositiveButton("OK"){dialog, id ->
                            dialog.dismiss()
                        }
                    //SHOW THE DIALOG AND ONCE DONE RETURN TO THE HOME PAGE
                    builder.show()

                    //SET SELECTED BITMAP IMAGE INTO THE IMAGE VIEW
                    val bitmap = getImageFromData(data)
                    val resizeBitmap = Bitmap.createScaledBitmap(bitmap!!,640,481,false)
                    bitmap?.apply{
                        img_process.setImageBitmap(rotate(seekBar.progress.toFloat()))
                        val outputStream = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG,50,outputStream)
                        val inputStream = ByteArrayInputStream(outputStream.toByteArray())

                        //ROATE BUTTON FUNCTION
                        if (bitmap != null){
                            rotatebtn.setOnClickListener{
                                if(seekBar.progress > 360) {
                                    seekBar.progress = 360
                                    return@setOnClickListener
                                }
                                if (seekBar.progress == 270) {
                                    seekBar.progress = 0
                                    // you can show notification that you are reach limit
                                    return@setOnClickListener
                                }
                                seekBar.progress = seekBar.progress + 90
                            }
                            // rotate bitmap on seek bar change
                            seekBar.setOnSeekBarChangeListener(
                                object: SeekBar.OnSeekBarChangeListener{
                                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                                        // show rotated bitmap in second image view
                                        img_process.setImageBitmap(rotate(progress.toFloat()))
                                    }

                                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                                    }

                                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                                    }
                                })

                            //IMAGE RECOGNITION FUNCTION
                            val onClickListener: Any = analysebtn.setOnClickListener {
                                val visionTask = @SuppressLint("StaticFieldLeak")
                                object : AsyncTask<InputStream, String, String>() {
                                    var progressDialog = ProgressDialog(this@RecycleGuideActivity)

                                    override fun onPreExecute() {
                                        progressDialog.show()
                                    }

                                    override fun onProgressUpdate(vararg values: String?) {
                                        progressDialog.setMessage(values[0])
                                    }

                                    @SuppressLint("StaticFieldLeak")
                                    override fun doInBackground(vararg params: InputStream?): String {
                                        try {
                                            publishProgress("Recognising Image...")
                                            val features = arrayOf("Description")
                                            val details = arrayOf<String>()

                                            val result = visionServiceClient.analyzeImage(params[0], features, details)

                                            return Gson().toJson(result)
                                        } catch (e: Exception) {
                                            if (e is NullPointerException){
                                                throw NoSuchElementException("No drawable on given view")
                                            }
                                            return ""
                                        }
                                    }

                                    override fun onPostExecute(result: String?) {
                                        progressDialog.dismiss()

                                        val result = Gson().fromJson<AnalysisResult>(result, AnalysisResult::class.java)
                                        val result_text = StringBuilder()

                                        for (caption in result.description.captions)
                                            result_text.append(caption.text)
                                        val recResult = result_text.toString()
                                        display_result.text = "Click Here To View Guide"
                                        display_result.setOnClickListener(){
                                            val intent = Intent(this@RecycleGuideActivity,WebViewGuideActivity::class.java)
                                            intent.putExtra("GUIDE_KEY", "https://www.google.com/search?q=how+to+recycle+$recResult&rlz=1C1CHBF_enMY893MY893&oq=how+to+recycle+$recResult&aqs=chrome..69i57j0i22i30l7.8230j0j7&sourceid=chrome&ie=UTF-8")
                                            startActivity(intent)
                                        }
                                    }

                                }
                                visionTask.execute(inputStream)
                            }
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode,resultCode,data)
    }

    private fun getImageFromData(data: Intent?):Bitmap?{
        val selectedImage = data?.data
        return MediaStore.Images.Media.getBitmap(
            this.contentResolver,
            selectedImage
        )
    }
    private fun pickImage() {
        val intent = Intent().apply{
            action = Intent.ACTION_PICK
            type = "image/*"
        }
        startActivityForResult(Intent.createChooser(intent, "Select Image"),IMAGE_PICK_CODE)
    }
}

// extension function to get bitmap from assets
fun Context.assetsToBitmap(fileName:String):Bitmap?{
    return try {
        val stream = assets.open(fileName)
        BitmapFactory.decodeStream(stream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}


// extension function to rotate bitmap
fun Bitmap.rotate(degrees:Float = 180F): Bitmap?{
    val matrix = Matrix()
    matrix.postRotate(degrees)

    return Bitmap.createBitmap(
        this, // source bitmap
        0, // x coordinate of the first pixel in source
        0, // y coordinate of the first pixel in source
        width, // The number of pixels in each row
        height, // The number of rows
        matrix, // Optional matrix to be applied to the pixels
        false // true if the source should be filtered
    )

}
