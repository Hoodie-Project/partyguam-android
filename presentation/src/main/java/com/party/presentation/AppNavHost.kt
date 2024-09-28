package com.party.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.CustomTopBar
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
import com.party.presentation.screen.join.birthday.JoinBirthDayScreen
import com.party.presentation.screen.join.complete.JoinCompleteScreen
import com.party.presentation.screen.join.email.JoinEmailScreen
import com.party.presentation.screen.join.gender.JoinGenderScreen
import com.party.presentation.screen.join.nickname.JoinNickNameScreen
import com.party.presentation.screen.login.LoginScreen

const val ANIMATION_DURATION = 500

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry.value.fromRoute()

    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    var joinActionText by remember {
        mutableStateOf("")
    }

    var isShowWarningDialog by remember {
        mutableStateOf(false)
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
        }
    ){
        NavHost(
            navController = navController,
            startDestination = Screens.Login,
            modifier = Modifier
                .fillMaxSize()
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
                    navController = navController,
                    userEmail = userEmail,
                    signupAccessToken = signupAccessToken,
                    context = context,
                    setActionText = { text ->
                        joinActionText = text
                    },
                    isShowWarningDialog = isShowWarningDialog,
                    onClose = { close ->
                        isShowWarningDialog = close
                    }
                )
            }
            composable<Screens.JoinBirthDay> {
                JoinBirthDayScreen(
                    navController = navController,
                    setActionText = { text ->
                        joinActionText = text
                    }
                )
            }
            composable<Screens.JoinGender> {
                JoinGenderScreen(
                    navController = navController,
                    context = context,
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
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                    context = context
                )
            }
            composable<Screens.DetailCarrier> {
                DetailCarrierScreen(
                    navController = navController,
                )
            }
            composable<Screens.ChoiceCarrierPosition> {
                ChoiceCarrierPositionScreen(
                    navController = navController,
                )
            }
            composable<Screens.SelectTendency1> {
                SelectTendencyScreen1(
                    navController = navController,
                    snackBarHostState = snackBarHostState
                )
            }
            composable<Screens.SelectTendency2> {
                SelectTendencyScreen2(
                    navController = navController,
                )
            }
            composable<Screens.SelectTendency3> {
                SelectTendencyScreen3(
                    navController = navController,
                    snackBarHostState = snackBarHostState
                )
            }
            composable<Screens.SelectTendency4> {
                SelectTendencyScreen4(
                    navController = navController,
                    snackBarHostState = snackBarHostState
                )
            }
            composable<Screens.SelectTendencyComplete> {
                SelectTendencyCompleteScreen(
                    navController = navController,
                )
            }
        }
    }
}