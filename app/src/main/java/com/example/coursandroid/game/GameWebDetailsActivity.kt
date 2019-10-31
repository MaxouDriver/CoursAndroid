package com.example.coursandroid.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.coursandroid.R
import kotlinx.android.synthetic.main.activity_game_web_details.*

class GameWebDetailsActivity : AppCompatActivity() {
    private var link: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_web_details)

        link = intent.getStringExtra("link")

        webView.webViewClient = WebViewClient()
        webView.loadUrl(link)
    }
}
