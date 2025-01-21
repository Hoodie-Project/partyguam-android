package com.party.presentation.screen.auth_setting

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.party.common.HeightSpacer
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.auth_setting.component.AuthSettingScaffoldArea
import com.party.presentation.screen.auth_setting.component.LogoutAndDeletionArea
import com.party.presentation.screen.auth_setting.component.ManageAuthArea
import com.party.presentation.screen.auth_setting.component.TermsArea
import com.party.presentation.screen.auth_setting.viewmodel.AuthSettingViewModel
import com.party.presentation.screen.home.HomeAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AuthSettingScreenRoute(
    context: Context,
    navController: NavHostController,
    authSettingViewModel: AuthSettingViewModel = hiltViewModel()
) {
    val authSettingState by authSettingViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        authSettingViewModel.successLogout.collectLatest {
            navController.navigate(Screens.Login) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    AuthSettingScreen(
        context = context,
        authSettingState = authSettingState,
        onNavigationBack = { navController.popBackStack() },
        onUserDelete = { navController.navigate(Screens.UserDelete)},
        onAction = { action ->
            when(action) {
                is AuthSettingAction.OnShowLogoutDialog -> authSettingViewModel.onAction(action)
                is AuthSettingAction.OnLogout -> authSettingViewModel.onAction(action)
            }
        },
        onGotoServiceIntroduce = {
            navController.navigate(Screens.ServiceIntroduce)
        },
        onGotoCustomerInquiries = {
            navController.navigate(Screens.CustomerInquiries)
        },
        onGotoTerms = {
            navController.navigate(Screens.Terms)
        },
        onGotoPrivacyPolicy = {
            navController.navigate(Screens.PrivacyPolicy)
        }
    )
}

@Composable
private fun AuthSettingScreen(
    context: Context,
    authSettingState: AuthSettingState,
    onNavigationBack: () -> Unit,
    onUserDelete: () -> Unit,
    onAction: (AuthSettingAction) -> Unit,
    onGotoServiceIntroduce: () -> Unit,
    onGotoCustomerInquiries: () -> Unit,
    onGotoTerms: () -> Unit,
    onGotoPrivacyPolicy: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .blur(
                    radiusX = if (authSettingState.isShowLogoutDialog) 10.dp else 0.dp,
                    radiusY = if (authSettingState.isShowLogoutDialog) 10.dp else 0.dp,
                ),
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
                TermsArea(
                    onGotoServiceIntroduce = onGotoServiceIntroduce,
                    onGotoCustomerInquiries = onGotoCustomerInquiries,
                    onGotoTerms = onGotoTerms,
                    onGotoPrivacyPolicy = onGotoPrivacyPolicy,
                )

                HeightSpacer(heightDp = 40.dp)

                LogoutAndDeletionArea(
                    onLogout = {
                        googleLogout(context = context)
                        kakaoLogout()
                        onAction(AuthSettingAction.OnShowLogoutDialog(true))
                    },
                    onUserDelete = onUserDelete
                )
            }
        }

        if(authSettingState.isShowLogoutDialog){
            TwoButtonDialog(
                dialogTitle = "로그아웃",
                description = "로그아웃을 하면 알림을 받지 못해요\n그래도 로그아웃 하시겠어요?",
                cancelButtonText = "취소",
                confirmButtonText = "로그아웃",
                onCancel = {
                    onAction(AuthSettingAction.OnShowLogoutDialog(false))
                },
                onConfirm = {
                    onAction(AuthSettingAction.OnShowLogoutDialog(false))
                    onAction(AuthSettingAction.OnLogout)
                }
            )
        }

        if(authSettingState.isShowLogoutDialog){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.2f))
                    .noRippleClickable { onAction(AuthSettingAction.OnShowLogoutDialog(false)) }
                    .zIndex(0f)
            )
        }
    }
}

private fun kakaoLogout(){
    UserApiClient.instance.logout { error ->
        if (error != null) {
            Log.d("kakaoLogout", "logoutKakao: $error")
        }else {
            Log.d("kakaoLogout", "logoutKakao: 로그아웃 성공")
        }
    }
}

private fun googleLogout(context: Context){
    // GoogleSignInOptions 설정
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    googleSignInClient.signOut()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 로그아웃 성공
                Log.d("googleLogout", "googleLogout: 성공")
            } else {
                // 로그아웃 실패
                Log.d("googleLogout", "googleLogout: 실패")
            }
        }
}

@Preview(showBackground = true)
@Composable
private fun AuthSettingScreenPreview() {
    AuthSettingScreen(
        context = LocalContext.current,
        authSettingState = AuthSettingState(),
        onNavigationBack = {},
        onUserDelete = {},
        onAction = {},
        onGotoServiceIntroduce = {},
        onGotoCustomerInquiries = {},
        onGotoTerms = {},
        onGotoPrivacyPolicy = {}
    )
}