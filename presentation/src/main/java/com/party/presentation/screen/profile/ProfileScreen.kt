package com.party.presentation.screen.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.BottomNavigationBar
import com.party.presentation.screen.profile.component.ProfileScaffoldArea

@Composable
fun ProfileScreen(
    context: Context,
    navController: NavHostController,
) {
    ProfileScreenContent(
        context = context,
        navController = navController,
    )
}

@Composable
fun ProfileScreenContent(
    context: Context,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            ProfileScaffoldArea(
                onGoToAlarm = {},
                onGoSetting = {},
            )
        },
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenContentPreview() {
    ProfileScreenContent(
        context = LocalContext.current,
        navController = rememberNavController(),
    )
}