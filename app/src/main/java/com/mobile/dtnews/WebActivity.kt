package com.mobile.dtnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra(URL)
        val myWebView: WebView = findViewById(R.id.webView)
        url?.let {
            myWebView.loadUrl(url)
        }
    }

    companion object {
        const val URL = "URL"
    }
}