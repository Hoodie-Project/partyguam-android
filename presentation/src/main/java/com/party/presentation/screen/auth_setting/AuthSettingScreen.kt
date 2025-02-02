package com.party.presentation.screen.auth_setting

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.party.common.HeightSpacer
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.makeAccessToken
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
import kotlinx.coroutines.flow.collectLatest

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
        onGotoServiceIntroduce = { navController.navigate(Screens.ServiceIntroduce) },
        onGotoCustomerInquiries = { navController.navigate(Screens.CustomerInquiries) },
        onGotoTerms = { navController.navigate(Screens.Terms) },
        onGotoPrivacyPolicy = { navController.navigate(Screens.PrivacyPolicy) },
        onLinkKakao = { token -> authSettingViewModel.linkKakao(oauthAccessToken = token) },
        onLinkGoogle = { token -> authSettingViewModel.linkGoogle(accessToken = token) }
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
    onLinkKakao: (String) -> Unit,
    onLinkGoogle: (String) -> Unit,
) {
    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                Log.e("KAKAO", "로그인 실패", error)
            }
            token != null -> {
                loginWithKakaoNickName(
                    context = context,
                    token = token,
                    onLink = onLinkKakao,
                )
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        googleSignIn(
            activityResult = it,
            onLinkGoogle = onLinkGoogle
        )
    }

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
                        .padding(horizontal = 20.dp),
                    authSettingState = authSettingState,
                    onLinkKakao = {
                        loginWithKakao(context, kakaoCallback)
                    },
                    onLinkGoogle = {
                        // google 로그인
                        val googleSignInOptions = GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("697659482179-ish7bgg19g5le6b1urrss97tjgasi91f.apps.googleusercontent.com")
                            .requestEmail()
                            .build()

                        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
                        launcher.launch(googleSignInClient.signInIntent)
                    }
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

private fun loginWithKakaoNickName(
    context: Context,
    token: OAuthToken,
    onLink: (String) -> Unit,
){
    UserApiClient.instance.me { user, error ->
        when {
            error != null -> Log.e("KAKAO", "사용자 정보 요청 실패", error)
            user != null -> {
                //Log.i("KAKAO", "사용자 정보 요청 성공 ${token.accessToken} ${user.properties?.get("email").orEmpty()}")
                //Log.i("KAKAO", "사용자 정보 요청 성공 ${user.kakaoAccount?.email}")
                /*loginViewModel.serveToKakaoLogin(
                    userEmail = user.kakaoAccount?.email.orEmpty(),
                    accessToken = makeAccessToken(context = context, token = token.accessToken),
                )*/

                // 연결하기 api
                onLink(token.accessToken)
            }
        }
    }
}

private fun loginWithKakao(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit){
    if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)){
        // 카카오톡 설치되있는거
        UserApiClient.instance.loginWithKakaoTalk(context){ token, error ->
            if(error != null){
                Log.e("KAKAO", "로그인 실패", error)
            }
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled){
                return@loginWithKakaoTalk
            }

            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    }else {
        // 카카오톡 설치 안 되있는거
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
    }
}

private fun googleSignIn(
    activityResult: ActivityResult,
    onLinkGoogle: (String) -> Unit,
){
    val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
    task.addOnCompleteListener { completedTask ->
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken
            onLinkGoogle(idToken ?: "")
        } catch (e: ApiException) {
            Log.e("Google Login Exception", "${e.message} ${e.statusCode}")
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
        onGotoPrivacyPolicy = {},
        onLinkKakao = {},
        onLinkGoogle = {}
    )
}