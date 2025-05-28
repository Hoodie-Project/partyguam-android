package com.party.presentation.screen.recover_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.Screens
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.recover_auth.component.AuthInfoArea
import com.party.presentation.screen.recover_auth.component.RecoverAuthScaffoldArea
import com.party.presentation.screen.recover_auth.component.RecoverBottomDescriptionArea
import com.party.presentation.screen.recover_auth.component.RecoverButton
import com.party.presentation.screen.recover_auth.component.RecoverDescriptionArea
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecoverAuthScreenRoute(
    navController: NavHostController,
    email: String,
    deletedAt: String,
    recoverAccessToken: String,
    recoverViewModel: RecoverViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        recoverViewModel.recoverAuthSuccess.collectLatest {
            navController.navigate(Screens.Home) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    RecoverAuthScreen(
        onNavigationClick = { navController.popBackStack() },
        onRecover = {
            recoverViewModel.recoverAuth(recoverAccessToken = "Bearer $recoverAccessToken")
        },
        email = email,
        deletedAt = deletedAt,
    )
}

@Composable
private fun RecoverAuthScreen(
    email: String,
    deletedAt: String,
    onNavigationClick: () -> Unit,
    onRecover: () -> Unit,
) {
    Scaffold(
        topBar = {
            RecoverAuthScaffoldArea(
                onNavigationClick = onNavigationClick
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
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                HeightSpacer(heightDp = 28.dp)
                RecoverDescriptionArea()
                HeightSpacer(heightDp = 32.dp)

                AuthInfoArea(
                    email = email,
                    deletedAt = deletedAt,
                )
                HeightSpacer(heightDp = 20.dp)

                RecoverBottomDescriptionArea()
            }

            RecoverButton(
                onClick = onRecover
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecoverAuthScreenPreview(modifier: Modifier = Modifier) {
    RecoverAuthScreen(
        email = "tmfrl1590@gmail.com",
        deletedAt = "",
        onNavigationClick = {},
        onRecover = {}
    )
}