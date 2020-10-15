package com.example.gtrrapp

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WebViewManualGuideActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val MyWebView: WebView =findViewById(R.id.display_web)
        val intent = getIntent()
        val link = intent.getStringExtra("MGUIDE_KEY")

        //LOAD URL TO WEBVIEW
        MyWebView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url:String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        MyWebView.loadUrl(link)
        MyWebView.settings.javaScriptEnabled=true
        MyWebView.settings.allowContentAccess=true
        MyWebView.settings.domStorageEnabled=true
        MyWebView.settings.useWideViewPort=true
        MyWebView.settings.setAppCacheEnabled(true)


    }
}