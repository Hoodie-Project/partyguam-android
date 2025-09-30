package com.party.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.party.common.ConnectivityObserver
import com.party.common.NetworkConnectivityObserver
import com.party.common.Screens
import com.party.common.component.floating.NavigateUpFloatingButton
import com.party.common.component.floating.PartyCreateFloatingButton
import com.party.common.component.homeTopTabList
import com.party.common.hasInternetConnection
import com.party.common.utils.noRippleClickable
import com.party.guam.design.BLACK
import com.party.guam.design.WHITE
import com.party.presentation.screen.app.AppEvent
import com.party.presentation.screen.app.AppState
import com.party.presentation.screen.app.AppViewModel
import com.party.presentation.screen.detail.detailProfileGraph
import com.party.presentation.screen.guide_permission.GuidePermissionScreenRoute
import com.party.presentation.screen.home.component.HomeFloatingArea
import com.party.presentation.screen.home.viewmodel.HomeEvent
import com.party.presentation.screen.info_center.infoCenterGraph
import com.party.presentation.screen.join.joinGraph
import com.party.presentation.screen.login.LoginScreenRoute
import com.party.presentation.screen.main.MainScreen
import com.party.presentation.screen.main.event.MainEvent
import com.party.presentation.screen.no_internet.NoInternetScreenRoute
import com.party.presentation.screen.recover_auth.RecoverAuthScreenRoute
import com.party.presentation.screen.splash.SplashScreenRoute
import com.party.presentation.screen.state.viewmodel.StateEvent
import kotlinx.coroutines.delay

const val ANIMATION_DURATION = 200

@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }

    val appViewModel: AppViewModel = hiltViewModel()
    val state by appViewModel.state.collectAsStateWithLifecycle()

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
        Box(
            modifier = Modifier
                .fillMaxSize()
            ,
        ){
            NavHost(
                navController = navController,
                startDestination = Screens.Splash,
                modifier = Modifier
                    .fillMaxSize()
                    .background(WHITE)
                    .systemBarsPadding()
                    .blur(
                        radiusX = if(state.isShowBlackBackground) 10.dp else 0.dp,
                        radiusY = if(state.isShowBlackBackground) 10.dp else 0.dp
                    )
                ,
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
                        state = state,
                        isFirstVersionCheck = isFirstVersionCheck,
                        onChangeFirstVersionCheck = { isFirstVersionCheck = it },
                        onGotoLogin = {
                            navController.navigate(Screens.Login) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onTabClick = { tab -> appViewModel.selectTab(tab)},
                        onCurrentScreen = { appViewModel.setCurrentScreen(currentScreen = it)},
                        onStartScrollParty = { flag -> appViewModel.startScrollParty(flag) },
                        onStartScrollRecruitment = { flag -> appViewModel.startScrollRecruitment(flag) },
                        onStartScroll = { flag -> appViewModel.startScroll(flag) }
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

            if(state.isShowBlackBackground){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BLACK.copy(alpha = 0.7f))
                        .noRippleClickable { AppEvent.emit(isShow = false) }
                        .zIndex(0f)
                )
            }

            HomeFloatingArea(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = (getNavigationBarHeight() + 80).dp, end = 20.dp)
                    .zIndex(1f),
                isExpandedFloatingButton = state.isShowBlackBackground,
                partyCreateFloating = {
                    val shouldShowButton = when(state.currentScreen) {
                        "Home" -> state.selectedTabText == homeTopTabList[0] || state.selectedTabText == homeTopTabList[1]
                        "State" -> true
                        else -> false
                    }

                    if (shouldShowButton) {
                        PartyCreateFloatingButton(
                            isExpandedFloatingButton = state.isShowBlackBackground,
                            onClick = {
                                AppEvent.emit(isShow = !state.isShowBlackBackground)
                            }
                        )
                    }
                },
                navigateUpFloating = {
                    val shouldShowButton = when(state.currentScreen) {
                        "Home" -> (state.selectedTabText == homeTopTabList[1] || state.selectedTabText == homeTopTabList[2]) && state.isScrollStart || state.isScrollStartParty || state.isScrollStartRecruitment
                        "State" -> state.isScrollStart
                        else -> false
                    }
                    NavigateUpFloatingButton(
                        isShowNavigateUpFloatingButton = shouldShowButton,
                        isExpandedFloatingButton = state.isShowBlackBackground,
                        onClick = { handleScrollToTop(state) }
                    )
                },
                onGoPartyCreate = {
                    MainEvent.gotoPartyCreate()
                    AppEvent.emit(isShow = false)
                }
            )
        }
    }
}

private fun handleScrollToTop(state: AppState) {
    when {
        state.currentScreen == "State" -> StateEvent.scrollToUp()
        state.selectedTabText == homeTopTabList[1] -> HomeEvent.scrollToUpParty()
        else -> HomeEvent.scrollToUpRecruitment()
    }
}

@Composable
fun getNavigationBarHeight(): Int {
    val density = LocalDensity.current
    val navigationBarsInsets = WindowInsets.navigationBars
    val heightPx = navigationBarsInsets.getBottom(density)
    val heightDp = with(density) { heightPx.toDp() }

    return heightDp.value.toInt()
}