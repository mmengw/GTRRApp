package com.example.gtrrapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewGuideActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val MyWebView: WebView =findViewById(R.id.display_web)
        val intent = getIntent()
        val link = intent.getStringExtra("GUIDE_KEY")

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