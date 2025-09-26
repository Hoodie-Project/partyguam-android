package com.party.presentation.screen.main

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.party.common.MainTab
import com.party.common.Screens
import com.party.common.component.BottomNavigationBar
import com.party.common.component.homeTopTabList
import com.party.common.component.toMainTab
import com.party.guam.design.WHITE
import com.party.presentation.ANIMATION_DURATION
import com.party.presentation.screen.home.HomeScreenRoute
import com.party.presentation.screen.profile.ProfileScreenRoute
import com.party.presentation.screen.state.StateScreenRoute

@Composable
fun MainScreen(
    tabName: String,
    isFirstActiveFunc: Boolean,
    onChangeFirstFunc: (Boolean) -> Unit,
    onGotoSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onClickBanner: (String) -> Unit,
    onGotoRecruitmentDetail: (Int, Int) -> Unit,
    onGotoPartyDetail: (Int) -> Unit,
    onGoPartyCreate: () -> Unit,
    onGotoDetailProfile: () -> Unit,
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentMainTab = backStackEntry.toMainTab()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE)
    ){
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)

            ,
            bottomBar = {
                BottomNavigationBar(
                    currentMainTab = currentMainTab,
                    navController = navController,
                    onTabClick = { bottomBarScreen ->
                        navController.navigate(bottomBarScreen.screen){
                            popUpTo(navController.graph.findStartDestination().route!!){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            },
        ) { paddingValues ->
            BottomBarGraph(
                context = context,
                isFirstActiveFunc = isFirstActiveFunc,
                onChangeFirstFunc = onChangeFirstFunc,
                snackBarHostState = snackBarHostState,
                tabName = tabName,
                navController = navController,
                paddingValues = paddingValues,
                onGotoSearch = onGotoSearch,
                onGotoNotification = onGotoNotification,
                onClickBanner = onClickBanner,
                onGotoRecruitmentDetail = onGotoRecruitmentDetail,
                onGotoPartyDetail = onGotoPartyDetail,
                onGoPartyCreate = onGoPartyCreate,
                onGotoDetailProfile = onGotoDetailProfile,
            )
        }
    }
}

@Composable
private fun BottomBarGraph(
    context: Context,
    isFirstActiveFunc: Boolean,
    onChangeFirstFunc: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    tabName: String,
    navController: NavHostController,
    paddingValues: PaddingValues,
    onGotoSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onClickBanner: (String) -> Unit,
    onGotoRecruitmentDetail: (Int, Int) -> Unit,
    onGotoPartyDetail: (Int) -> Unit,
    onGoPartyCreate: () -> Unit,
    onGotoDetailProfile: () -> Unit,
){
    LaunchedEffect(tabName) {
        val target = when (tabName) {
            MainTab.Home.name -> Screens.Home
            MainTab.Active.name -> Screens.State
            MainTab.Profile.name -> Screens.Profile
            else -> Screens.Home
        }
        navController.navigate(target) {
            popUpTo(Screens.Home) { inclusive = false; saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        modifier = Modifier
            .background(WHITE)
            .padding(paddingValues)
        ,
        navController = navController,
        startDestination = Screens.Main(tabName = MainTab.Home.name),
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
        navigation<Screens.Main>(
            startDestination = Screens.Home,
        ){
            composable<Screens.Home> {
                HomeScreenRoute(
                    context = context,
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    homeTopTabList = homeTopTabList,
                    isFirstActiveFunc = isFirstActiveFunc,
                    onChangeFirstFunc = { onChangeFirstFunc(false)},
                    onRecruitmentItemClick = { partyRecruitmentId, partyId -> navController.navigate(Screens.RecruitmentDetail(partyRecruitmentId = partyRecruitmentId, partyId = partyId)) },
                    onGotoSearch = onGotoSearch,
                    onGotoNotification = onGotoNotification,
                    onClickBanner = onClickBanner,
                    onGotoRecruitmentDetail = onGotoRecruitmentDetail,
                    onGotoPartyDetail = onGotoPartyDetail,
                    onGoPartyCreate = onGoPartyCreate,
                    onGotoDetailProfile = onGotoDetailProfile,
                )
            }
            composable<Screens.State> {
                StateScreenRoute(
                    navController = navController,
                )
            }
            composable<Screens.Profile> {
                ProfileScreenRoute(
                    navController = navController,
                )
            }
        }
    }
}