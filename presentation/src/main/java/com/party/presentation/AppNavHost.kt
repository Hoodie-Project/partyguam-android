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
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.CustomTopBar
import com.party.navigation.Screens
import com.party.navigation.fromRoute
import com.party.navigation.isVisibleTopBar
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

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            if (isVisibleTopBar(currentScreen)) {
                CustomTopBar(
                    currentScreen = currentScreen,
                    navController = navController,
                    joinActionText = joinActionText,
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
                LoginScreen(navController = navController)
            }
            composable<Screens.JoinEmail> {
                JoinEmailScreen(
                    navController = navController,
                    setActionText = { text ->
                        joinActionText = text
                    }
                )
            }
            composable<Screens.JoinNickName> {
                JoinNickNameScreen(
                    navController = navController,
                    context = context,
                    setActionText = { text ->
                        joinActionText = text
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
                JoinCompleteScreen(navController = navController,)
            }
        }
    }
}