package com.party.presentation.screen.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
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
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true // JavaScript 활성화
                settings.domStorageEnabled = true // 로컬 저장소 활성화
                webViewClient = WebViewClient() // 웹뷰 클라이언트 설정
                loadUrl(webViewUrl) // URL 로드
            }
        },
        update = { webView ->
            webView.loadUrl(webViewUrl) // URL 변경 시 갱신
        }
    )
}