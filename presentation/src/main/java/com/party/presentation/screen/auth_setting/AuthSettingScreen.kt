package com.party.presentation.screen.auth_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.auth_setting.component.AuthSettingScaffoldArea
import com.party.presentation.screen.auth_setting.component.LogoutAndDeletionArea
import com.party.presentation.screen.auth_setting.component.ManageAuthArea
import com.party.presentation.screen.auth_setting.component.TermsArea
import com.party.presentation.screen.auth_setting.viewmodel.AuthSettingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthSettingScreenRoute(
    navController: NavHostController,
    authSettingViewModel: AuthSettingViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        authSettingViewModel.successLogout.collectLatest {
            navController.navigate(Screens.Login) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    AuthSettingScreen(
        onNavigationBack = { navController.popBackStack() },
        onUserDelete = { navController.navigate(Screens.UserDelete)},
        onAction = { action ->
            when(action) {
                is AuthSettingAction.OnLogout -> authSettingViewModel.onAction(action)
                is AuthSettingAction.OnUserDelete -> authSettingViewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun AuthSettingScreen(
    onNavigationBack: () -> Unit,
    onUserDelete: () -> Unit,
    onAction: (AuthSettingAction) -> Unit,
) {
    Scaffold(
        topBar = {
            AuthSettingScaffoldArea(
                onNavigationBack = onNavigationBack
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
        ) {
            // 소셜 로그인 관리
            HeightSpacer(heightDp = 24.dp)
            ManageAuthArea(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )

            HeightSpacer(heightDp = 32.dp)

            HorizontalDivider(
                color = GRAY200
            )

            HeightSpacer(heightDp = 24.dp)

            // 이용약관 영역
            TermsArea()

            HeightSpacer(heightDp = 40.dp)

            LogoutAndDeletionArea(
                onLogout = {
                    onAction(AuthSettingAction.OnLogout)
                },
                onUserDelete = onUserDelete
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthSettingScreenPreview() {
    AuthSettingScreen(
        onNavigationBack = {},
        onUserDelete = {},
        onAction = {}
    )
}