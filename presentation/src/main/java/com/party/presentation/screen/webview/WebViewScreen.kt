package com.party.presentation.screen.webview

import android.os.Build
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreenRoute(
    webViewUrl: String,
) {
    WebViewScreen(
        webViewUrl = webViewUrl
    )
}

@Composable
private fun WebViewScreen(
    webViewUrl: String,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                    context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true

                    settings.builtInZoomControls = false
                    settings.displayZoomControls = false

                    webChromeClient = WebChromeClient()
                    webViewClient = WebViewClient()

                    // ✅ 가끔 필요: 혼합 콘텐츠 허용 (HTTP 콘텐츠)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    }

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    loadUrl(webViewUrl) // URL 로드
                }
            },
            update = { webView ->
                webView.loadUrl(webViewUrl) // URL 변경 시 갱신
            }
        )
    }

}