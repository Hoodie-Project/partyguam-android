package com.party.presentation.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreenRoute(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        splashViewModel.getAccessToken().join()
    }

    val accessToken by splashViewModel.accessToken.collectAsStateWithLifecycle()
    val firstLaunchFlag by splashViewModel.firstLaunchFlag.collectAsStateWithLifecycle()

    LaunchedEffect(accessToken, firstLaunchFlag) {
        delay(1000L)
        when {
            firstLaunchFlag -> {
                navController.navigate(Screens.GuidePermission){
                    popUpTo(Screens.Splash) { inclusive = true }
                }
            }
            accessToken.isNotEmpty() -> {
                navController.navigate(Screens.Main) {
                    popUpTo(Screens.Splash) { inclusive = true }
                }
            }
            accessToken.isEmpty() -> {
                navController.navigate(Screens.Login) {
                    popUpTo(Screens.Splash) { inclusive = true }
                }
            }
        }
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            println("splash screen")
            //Text(text = "파티괌 인트로 페이지", fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenContentPreview() {
    SplashScreen()
}