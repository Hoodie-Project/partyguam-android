package com.party.presentation.screen.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.ScreenExplainArea
import com.party.common.Screens
import com.party.common.utils.makeAccessToken
import com.party.common.utils.snackBarMessage
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.login.component.LoginButtonArea
import com.party.presentation.screen.login.component.LoginScaffoldArea
import com.party.presentation.screen.login.component.LoginScreenBottomArea
import com.party.presentation.screen.login.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreenRoute(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        loginViewModel.nextScreen.collectLatest {
            snackBarMessage(snackBarHostState, it.message)

            navController.navigate(
                Screens.JoinEmail(
                userEmail = it.userEmail ?: "",
                signupAccessToken = it.signupAccessToken
            ))
        }
    }

    LaunchedEffect(key1 = Unit) {
        loginViewModel.goToHomeScreen.collectLatest {
            navController.navigate(Screens.Home)
            //navController.navigate(Screens.SelectTendency1)
        }
    }

    LaunchedEffect(key1 = Unit) {
        loginViewModel.goToRecoverScreen.collectLatest { recoverInfo ->
            navController.navigate(
                Screens.RecoverAuth(
                    email = recoverInfo.email,
                    deletedAt = recoverInfo.deletedAt,
                    recoverAccessToken = recoverInfo.recoverAccessToken,
                )
            )
        }
    }

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                Log.e("KAKAO", "로그인 실패", error)
            }
            token != null -> loginWithKakaoNickName(context, token, loginViewModel)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        loginViewModel.googleSignIn(activityResult = it, context = context)
    }

    LoginScreen(
        context = context,
        kakaoCallback = kakaoCallback,
        launcher = launcher,
        onGotoTerms = { navController.navigate(Screens.Terms) },
        onGotoInquire = { navController.navigate(Screens.CustomerInquiries)},
        onPrivacyClick = { navController.navigate(Screens.PrivacyPolicy)}
    )
}

@Composable
private fun LoginScreen(
    context: Context,
    kakaoCallback: (OAuthToken?, Throwable?) -> Unit,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    onGotoTerms: () -> Unit,
    onGotoInquire: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Scaffold (
        topBar = {
            LoginScaffoldArea()
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            ScreenExplainArea(
                mainExplain = stringResource(id = R.string.login1),
                subExplain = stringResource(id = R.string.login2),
            )

            HeightSpacer(heightDp = 40.dp)

            LoginButtonArea(
                context = context,
                kakaoCallback = kakaoCallback,
                launcher = launcher,
            )

            LoginScreenBottomArea(
                onGotoInquire = onGotoInquire,
                onTermsClick = onGotoTerms,
                onPrivacyClick = onPrivacyClick,
            )
        }
    }
}

private fun loginWithKakaoNickName(context: Context, token: OAuthToken, loginViewModel: LoginViewModel){
    UserApiClient.instance.me { user, error ->
        when {
            error != null -> Log.e("KAKAO", "사용자 정보 요청 실패", error)
            user != null -> {
                //Log.i("KAKAO", "사용자 정보 요청 성공 ${token.accessToken} ${user.properties?.get("email").orEmpty()}")
                //Log.i("KAKAO", "사용자 정보 요청 성공 ${user.kakaoAccount?.email}")
                loginViewModel.serveToKakaoLogin(
                    userEmail = user.kakaoAccount?.email.orEmpty(),
                    accessToken = makeAccessToken(context = context, token = token.accessToken),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        context = LocalContext.current,
        kakaoCallback = { _, _ -> },
        launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
        },
        onGotoTerms = {},
        onGotoInquire = {},
        onPrivacyClick = {}
    )
}