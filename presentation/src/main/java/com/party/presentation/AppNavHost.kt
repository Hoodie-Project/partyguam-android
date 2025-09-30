package com.party.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.party.common.ConnectivityObserver
import com.party.common.NetworkConnectivityObserver
import com.party.common.Screens
import com.party.common.hasInternetConnection
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.detailProfileGraph
import com.party.presentation.screen.guide_permission.GuidePermissionScreenRoute
import com.party.presentation.screen.info_center.infoCenterGraph
import com.party.presentation.screen.join.joinGraph
import com.party.presentation.screen.login.LoginScreenRoute
import com.party.presentation.screen.main.MainScreen
import com.party.presentation.screen.no_internet.NoInternetScreenRoute
import com.party.presentation.screen.recover_auth.RecoverAuthScreenRoute
import com.party.presentation.screen.splash.SplashScreenRoute
import kotlinx.coroutines.delay

const val ANIMATION_DURATION = 200

@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }

    val statusFlow = NetworkConnectivityObserver(context).getFlow()
    val status: ConnectivityObserver.Status by statusFlow.collectAsStateWithLifecycle(ConnectivityObserver.Status.Init)

    // 앱이 처음 실행되었는지를 추적하여 앱 버전 체크를 한 번만 수행하기 위한 플래그
    var isFirstVersionCheck by remember { mutableStateOf(true) }

    var showNoInternet by remember { mutableStateOf(false) }
    LaunchedEffect(status) {
        when (status) {
            ConnectivityObserver.Status.Losing -> {
                delay(500)
                showNoInternet = true
            }

            ConnectivityObserver.Status.UnAvailable -> {
                delay(1000) // 잠깐 기다려보고
                if (status == ConnectivityObserver.Status.UnAvailable) {
                    showNoInternet = true
                }
            }

            ConnectivityObserver.Status.Lost -> {
                showNoInternet = true
            }

            ConnectivityObserver.Status.Available -> {
                //onConnectedNetwork(true)
                showNoInternet = false
            }
            ConnectivityObserver.Status.Init -> {
                delay(300) // 약간의 대기 (네트워크 세팅 시간 고려)
                val hasInternet = hasInternetConnection()
                showNoInternet = !hasInternet
            }
        }
    }

    if(showNoInternet){
        NoInternetScreenRoute()
    } else {
        NavHost(
            navController = navController,
            startDestination = Screens.Splash,
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .systemBarsPadding(),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = ANIMATION_DURATION)
                )
            }
        ){
            composable<Screens.Splash> {
                SplashScreenRoute(
                    navController = navController,
                )
            }
            composable<Screens.GuidePermission> {
                GuidePermissionScreenRoute(
                    context = context,
                    navController = navController,
                )
            }
            composable<Screens.Login> {
                LoginScreenRoute(
                    navController = navController,
                    context = context,
                )
            }
            infoCenterGraph(
                context = context,
                navController = navController,
            )

            joinGraph(
                navController = navController,
                snackBarHostState = snackBarHostState,
            )

            detailProfileGraph(
                navController = navController,
                snackBarHostState = snackBarHostState,
            )

            composable<Screens.Main> {
                MainScreen(
                    snackBarHostState = snackBarHostState,
                    isFirstVersionCheck = isFirstVersionCheck,
                    onChangeFirstVersionCheck = { isFirstVersionCheck = it },
                    onGotoLogin = {
                        navController.navigate(Screens.Login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<Screens.RecoverAuth> { backStackEntry ->
                val email = backStackEntry.toRoute<Screens.RecoverAuth>().email
                val deletedAt = backStackEntry.toRoute<Screens.RecoverAuth>().deletedAt
                val recoverAccessToken = backStackEntry.toRoute<Screens.RecoverAuth>().recoverAccessToken
                RecoverAuthScreenRoute(
                    navController = navController,
                    email = email,
                    deletedAt = deletedAt,
                    recoverAccessToken = recoverAccessToken,
                )
            }
        }
    }
}