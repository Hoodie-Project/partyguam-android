package com.party.presentation

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.ConnectivityObserver
import com.party.common.NetworkConnectivityObserver
import com.party.guam.design.GuamTheme
import com.party.presentation.firebase.initFcm
import com.party.presentation.screen.no_internet.NoInternetScreenRoute
import com.party.presentation.screen.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

private val Context.currentConnectivityState: ConnectivityObserver.Status
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        return if (activeNetwork != null) {
            ConnectivityObserver.Status.Available
        } else {
            ConnectivityObserver.Status.UnAvailable
        }
    }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 세로 고정
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
        initFcm(
            context = this,
            onSaveFcmToken = { token ->
                viewModel.saveFcmToken(token = token)
            }
        )

        setContent {
            GuamTheme {
                NetworkStatusWrapper {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
private fun NetworkStatusWrapper(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val connectivityObserver = remember { NetworkConnectivityObserver(context) }

    val status by connectivityObserver.getFlow().collectAsStateWithLifecycle(
        initialValue = context.currentConnectivityState
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()

        if (status != ConnectivityObserver.Status.Available) {
            NoInternetScreenRoute()
        }
    }
}