package com.party.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.party.common.ScaffoldTitle

fun isVisibleTopBar(currentScreen: Screens): Boolean {
    return when (currentScreen) {
        is Screens.JoinComplete
        -> false
        else
        -> true
    }
}

@Composable
fun CustomTopBar(
    currentScreen: Screens,
    navController: NavHostController,
){
    when (currentScreen) {
        is Screens.Login,
        is Screens.JoinEmail,
        is Screens.JoinNickName,
        is Screens.JoinBirthDay,
        is Screens.JoinGender,
        -> CenterTopBar(
            currentScreen = currentScreen,
            navHostController = navController,
        )
        is Screens.JoinComplete,
            -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar(
    currentScreen: Screens,
    navHostController: NavHostController,
){
    CenterAlignedTopAppBar(
        navigationIcon = {
            SetNavigationIcon(
                currentScreen = currentScreen,
                navController = navHostController,
            )
        },
        title = { ScaffoldTitle(title = currentScreen.title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ),
        actions = {

        }
    )
}

@Composable
fun SetNavigationIcon(
    currentScreen: Screens,
    navController: NavHostController,
){
    when (currentScreen) {
        is Screens.JoinEmail,
        is Screens.JoinNickName,
        is Screens.JoinBirthDay,
        is Screens.JoinGender,
        -> BackNavigationIcon(navController = navController)

        is Screens.Login,
        is Screens.JoinComplete,
        -> {}
    }
}


@Composable
fun BackNavigationIcon(
    navController: NavHostController,
) {
    IconButton(
        onClick = {
            navController.popBackStack()
        }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = "back",
            tint = Color.Black
        )
    }
}