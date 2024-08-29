package com.party.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.party.common.ScaffoldTitle

fun isVisibleTopBar(currentScreen: Screens): Boolean {
    return when (currentScreen) {
        is Screens.Login,
            -> true
    }
}

@Composable
fun CustomTopBar(
    currentScreen: Screens,
    navController: NavHostController,
    title: String,
){
    when (currentScreen) {
        is Screens.Login,
        -> CenterTopBar(
            currentScreen = currentScreen,
            navHostController = navController,
            title = title,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar(
    currentScreen: Screens,
    navHostController: NavHostController,
    title: String,
){
    CenterAlignedTopAppBar(
        navigationIcon = {
            
        },
        title = { ScaffoldTitle(title = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ),
        actions = {

        }
    )
}