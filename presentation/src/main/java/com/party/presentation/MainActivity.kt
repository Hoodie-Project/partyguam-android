package com.party.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.party.common.ui.theme.GuamTheme
import com.party.presentation.firebase.initFcm
import com.party.presentation.screen.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        enableEdgeToEdge()

        // 딥링크 데이터 확인
        intent?.data?.let { uri ->
            val scheme = uri.scheme // "ys_weather"
            val host = uri.host // "testlink"
            Log.d("DeepLink", "Scheme: $scheme, Host: $host")
        }

        // FCM 초기화
        initFcm(context = this)

        setContent {
            GuamTheme {
                AppNavHost()
            }
        }
    }
}