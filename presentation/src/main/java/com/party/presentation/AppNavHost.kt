package com.party.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.party.common.R
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.BottomNavigationBar
import com.party.navigation.CustomTopBar
import com.party.navigation.FloatingButton
import com.party.navigation.Screens
import com.party.navigation.fromRoute
import com.party.navigation.isVisibleTopBar
import com.party.presentation.screen.detail.choice_carrier_position.ChoiceCarrierPositionScreen
import com.party.presentation.screen.detail.detail_carrier.DetailCarrierScreen
import com.party.presentation.screen.detail.detail_profile.DetailProfileScreen
import com.party.presentation.screen.detail.select_tendency.SelectTendencyCompleteScreen
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen1
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen2
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen3
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen4
import com.party.presentation.screen.home.HomeScreen
import com.party.presentation.screen.join.birthday.JoinBirthDayScreen
import com.party.presentation.screen.join.complete.JoinCompleteScreen
import com.party.presentation.screen.join.email.JoinEmailScreen
import com.party.presentation.screen.join.gender.JoinGenderScreen
import com.party.presentation.screen.join.nickname.JoinNickNameScreen
import com.party.presentation.screen.login.LoginScreen
import com.party.presentation.screen.profile.ProfileScreen
import com.party.presentation.screen.splash.SplashScreen
import com.party.presentation.screen.state.StateScreen

const val ANIMATION_DURATION = 500

@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromRoute()

    val snackBarHostState = remember { SnackbarHostState() }

    var joinActionText by remember {
        mutableStateOf("")
    }

    var isShowWarningDialog by remember {
        mutableStateOf(false)
    }

    var isExpandedFloatingButton by remember {
        mutableStateOf(false)
    }

    val homeTopTabList = listOf(
        stringResource(id = R.string.home_top_tab1),
        stringResource(id = R.string.home_top_tab2),
        stringResource(id = R.string.home_top_tab3)
    )

    var selectedTabText by remember {
        mutableStateOf(homeTopTabList[0])
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            if (isVisibleTopBar(currentScreen)) {
                CustomTopBar(
                    currentScreen = currentScreen,
                    navController = navController,
                    joinActionText = joinActionText,
                    onWarningDialog = {
                        isShowWarningDialog = it
                    }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
                isExpandedFloatingButton = isExpandedFloatingButton,
                onUnExpandedFloatingButton = { isExpandedFloatingButton = it }
            )
        },
        floatingActionButton = { if(currentScreen == Screens.Home)
            FloatingButton(
                isExpandedFloatingButton = isExpandedFloatingButton,
                selectedTabText = selectedTabText,
                onExpanded = { isExpandedFloatingButton = it })
        }
    ){
        NavHost(
            navController = navController,
            startDestination = Screens.Splash,
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radiusX = if (isExpandedFloatingButton) 10.dp else 0.dp,
                    radiusY = if (isExpandedFloatingButton) 10.dp else 0.dp,
                )
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE),

            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(ANIMATION_DURATION)
                )
            },
        ){
            composable<Screens.Splash> {
                SplashScreen(
                    navController = navController,
                )
            }
            composable<Screens.Login> {
                LoginScreen(
                    navController = navController,
                    context = context,
                    snackBarHostState = snackBarHostState
                )
            }
            composable<Screens.JoinEmail> { backStackEntry ->
                val userEmail = backStackEntry.toRoute<Screens.JoinEmail>().userEmail
                val signupAccessToken = backStackEntry.toRoute<Screens.JoinEmail>().signupAccessToken
                JoinEmailScreen(
                    navController = navController,
                    userEmail = userEmail,
                    signupAccessToken = signupAccessToken,
                    setActionText = { text ->
                        joinActionText = text
                    },
                )
            }
            composable<Screens.JoinNickName> { backStackEntry ->
                val userEmail = backStackEntry.toRoute<Screens.JoinEmail>().userEmail
                val signupAccessToken = backStackEntry.toRoute<Screens.JoinEmail>().signupAccessToken
                JoinNickNameScreen(
                    context = context,
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                    userEmail = userEmail,
                    signupAccessToken = signupAccessToken,
                    setActionText = { text ->
                        joinActionText = text
                    },
                    isShowWarningDialog = isShowWarningDialog,
                    onClose = { close ->
                        isShowWarningDialog = close
                    }
                )
            }
            composable<Screens.JoinBirthDay> { backStackEntry ->
                val userEmail = backStackEntry.toRoute<Screens.JoinBirthDay>().userEmail
                val signupAccessToken = backStackEntry.toRoute<Screens.JoinBirthDay>().signupAccessToken
                val userNickName = backStackEntry.toRoute<Screens.JoinBirthDay>().userNickName
                JoinBirthDayScreen(
                    navController = navController,
                    userEmail = userEmail,
                    signupAccessToken = signupAccessToken,
                    userNickName = userNickName,
                    setActionText = { text ->
                        joinActionText = text
                    }
                )
            }
            composable<Screens.JoinGender> { backStackEntry ->
                val userEmail = backStackEntry.toRoute<Screens.JoinGender>().userEmail
                val signupAccessToken = backStackEntry.toRoute<Screens.JoinGender>().signupAccessToken
                val userNickName = backStackEntry.toRoute<Screens.JoinGender>().userNickName
                val userBirthDay = backStackEntry.toRoute<Screens.JoinGender>().userBirthDay
                JoinGenderScreen(
                    context = context,
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                    userEmail = userEmail,
                    signupAccessToken = signupAccessToken,
                    userNickName = userNickName,
                    userBirthDay = userBirthDay,
                    setActionText = { text ->
                        joinActionText = text
                    }
                )
            }
            composable<Screens.JoinComplete> {
                JoinCompleteScreen(navController = navController)
            }
            composable<Screens.DetailProfile> {
                DetailProfileScreen(
                    context = context,
                    snackBarHostState = snackBarHostState,
                    navController = navController
                )
            }
            composable<Screens.DetailCarrier> {
                DetailCarrierScreen(
                    context = context,
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                )
            }
            composable<Screens.ChoiceCarrierPosition> { backStackEntry ->
                val isMain = backStackEntry.toRoute<Screens.ChoiceCarrierPosition>().isMain
                ChoiceCarrierPositionScreen(
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    isMain = isMain,
                )
            }
            composable<Screens.SelectTendency1> {
                SelectTendencyScreen1(
                    context = context,
                    navController = navController,
                    snackBarHostState = snackBarHostState
                )
            }
            composable<Screens.SelectTendency2> {
                SelectTendencyScreen2(
                    context = context,
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.SelectTendency3> {
                SelectTendencyScreen3(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.SelectTendency4> {
                SelectTendencyScreen4(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.SelectTendencyComplete> {
                SelectTendencyCompleteScreen(
                    navController = navController,
                )
            }
            composable<Screens.Home> {
                HomeScreen(
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    selectedTabText = selectedTabText,
                    homeTopTabList = homeTopTabList,
                    onTabClick = { selectedText -> selectedTabText = selectedText },
                    onGoRecruitment = { selectedTabText = homeTopTabList[2] }
                )
            }
            composable<Screens.State> {
                StateScreen()
            }
            composable<Screens.Profile> {
                ProfileScreen()
            }
        }

        if(isExpandedFloatingButton){
            Box(modifier = Modifier.fillMaxSize().background(BLACK.copy(alpha = 0.7f)).noRippleClickable { isExpandedFloatingButton = false })
        }
    }
}