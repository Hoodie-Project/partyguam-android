package com.party.navigation

import androidx.compose.ui.graphics.painter.Painter

val bottomDestinations = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.State,
    BottomBarScreen.Profile,
)

sealed class BottomBarScreen (
    val screen: Screens,
    val name: String,
    val icon: Painter? = null,
){
    data object Home: BottomBarScreen(
        screen = Screens.Home,
        name = "홈",
    )
    data object State: BottomBarScreen(
        screen = Screens.State,
        name = "활동",
    )
    data object Profile: BottomBarScreen(
        screen = Screens.Profile,
        name = "프로필",
    )
}