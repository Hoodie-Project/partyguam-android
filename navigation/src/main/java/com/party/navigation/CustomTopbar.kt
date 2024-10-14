package com.party.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
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
        is Screens.JoinComplete,
        is Screens.SelectTendencyComplete,
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
    onWarningDialog: (Boolean) -> Unit,
){
    when (currentScreen) {
        is Screens.Login,
        is Screens.JoinEmail,
        is Screens.JoinNickName,
        is Screens.JoinBirthDay,
        is Screens.JoinGender,
        is Screens.DetailProfile,
        is Screens.DetailCarrier,
        is Screens.ChoiceCarrierPosition,
        is Screens.SelectTendency1,
        is Screens.SelectTendency2,
        is Screens.SelectTendency3,
        is Screens.SelectTendency4,
        -> CenterTopBar(
            currentScreen = currentScreen,
            navHostController = navController,
            joinActionText = joinActionText,
            onWarningDialog = onWarningDialog,
        )
        is Screens.Splash,
        is Screens.JoinComplete,
        is Screens.SelectTendencyComplete,
        is Screens.Home,
        is Screens.State,
        is Screens.Profile,
        -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar(
    currentScreen: Screens,
    navHostController: NavHostController,
    joinActionText: String? = null,
    onWarningDialog: (Boolean) -> Unit,
){
    CenterAlignedTopAppBar(
        navigationIcon = {
            SetNavigationIcon(
                currentScreen = currentScreen,
                navController = navHostController,
                onWarningDialog = onWarningDialog,
            )
        },
        title = { ScaffoldTitle(title = currentScreen.title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ),
        actions = {
            SetScaffoldRightCloseIcon(
                currentScreen = currentScreen,
                joinActionText = joinActionText,
            )
        }
    )
}

@Composable
fun SetScaffoldRightCloseIcon(
    currentScreen: Screens,
    joinActionText: String?,
) {
    when (currentScreen) {
        is Screens.JoinEmail,
        is Screens.JoinNickName,
        is Screens.JoinBirthDay,
        is Screens.JoinGender,
        -> ScaffoldActionAreaText(text = joinActionText ?: "")

        is Screens.DetailProfile,
        is Screens.DetailCarrier,
        is Screens.SelectTendency1,
        is Screens.SelectTendency2,
        is Screens.SelectTendency3,
        is Screens.SelectTendency4,
        -> ScaffoldActionCloseIcon()

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
fun ScaffoldActionCloseIcon() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
        )
    }
}

@Composable
fun SetNavigationIcon(
    currentScreen: Screens,
    navController: NavHostController,
    onWarningDialog: (Boolean) -> Unit,
){
    when (currentScreen) {
        is Screens.JoinEmail,
        is Screens.JoinNickName,
        is Screens.JoinBirthDay,
        is Screens.JoinGender,
        is Screens.DetailCarrier,
        is Screens.ChoiceCarrierPosition,
        is Screens.SelectTendency1,
        is Screens.SelectTendency2,
        is Screens.SelectTendency3,
        is Screens.SelectTendency4,
        -> BackNavigationIcon(
            currentScreen = currentScreen,
            navController = navController,
            onWarningDialog = onWarningDialog,
        )
        is Screens.Splash,
        is Screens.Login,
        is Screens.JoinComplete,
        is Screens.DetailProfile,
        is Screens.SelectTendencyComplete,
        is Screens.Home,
        is Screens.State,
        is Screens.Profile,
        -> {}
    }
}


@Composable
fun BackNavigationIcon(
    currentScreen: Screens,
    navController: NavHostController,
    onWarningDialog: (Boolean) -> Unit,
) {
    IconButton(
        onClick = {
            when (currentScreen) {
                is Screens.JoinNickName -> { onWarningDialog(true) }
                else -> { navController.popBackStack() }
            }
        }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = "back",
            tint = Color.Black
        )
    }
}