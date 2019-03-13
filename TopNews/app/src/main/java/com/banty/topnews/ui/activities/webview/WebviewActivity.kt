package com.banty.topnews.ui.activities.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.*
import com.banty.topnews.R

class WebviewActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        val INTENT_KEY_NEWS_ARTICLE_URL = "intent.key.news.article.url"
    }

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        setupWebview()
        loadNewsPage(intent)
    }

    private fun loadNewsPage(intent: Intent?) {
        if(intent != null && intent.extras != null && intent.extras.containsKey(INTENT_KEY_NEWS_ARTICLE_URL)) {
            webView.loadUrl(intent.getStringExtra(INTENT_KEY_NEWS_ARTICLE_URL))
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebview() {
        // set javascript and zoom and some other settings
        webView = findViewById<WebView>(R.id.webView)
        val settings = webView.settings
        settings.setAppCacheEnabled(true)
        settings.databaseEnabled = true
        settings.domStorageEnabled = true

        settings.loadsImagesAutomatically = true
        settings.defaultTextEncodingName = "UTF-8"
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.builtInZoomControls = false
        settings.useWideViewPort = true

        // 3RD party plugins (on older devices)
        settings.pluginState = WebSettings.PluginState.ON

        webView.setInitialScale(100)
        webView.setBackgroundColor(resources.getColor(R.color.transparent_background))
        webView.isVerticalScrollBarEnabled = true
        webView.isHorizontalScrollBarEnabled = false

        webView.isFocusable = true
        webView.isFocusableInTouchMode = true
        webView.isSaveEnabled = true

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.requestFocus()

        CookieManager.getInstance().setAcceptCookie(true)
    }
}
