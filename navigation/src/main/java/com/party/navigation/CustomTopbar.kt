package com.party.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.party.common.ScaffoldTitle
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE

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
    joinActionText: String? = null,
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
            joinActionText = joinActionText,
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
    joinActionText: String? = null,
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
            test(
                currentScreen = currentScreen,
                joinActionText = joinActionText,
            )
        }
    )
}

@Composable
fun test(
    currentScreen: Screens,
    joinActionText: String?,
) {
    when (currentScreen) {
        is Screens.JoinEmail,
        is Screens.JoinNickName,
        is Screens.JoinBirthDay,
        is Screens.JoinGender,
        -> {
            ScaffoldActionAreaText(text = joinActionText ?: "")
        }

        else -> {}
    }
}

@Composable
fun ScaffoldActionAreaText(
    text: String,
) {
    Text(
        text = text,
        fontSize = B2,
        color = GRAY500,
        modifier = Modifier
            .padding(end = MEDIUM_PADDING_SIZE)
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