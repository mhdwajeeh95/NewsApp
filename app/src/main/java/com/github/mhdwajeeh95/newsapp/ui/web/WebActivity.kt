package com.github.mhdwajeeh95.newsapp.ui.web

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.github.mhdwajeeh95.newsapp.R
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        webview.webViewClient = WebViewClient()
        webview.loadUrl(intent.extras?.getString("url"))
    }
}