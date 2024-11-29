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
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.makeAccessToken
import com.party.common.snackBarMessage
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.login.component.LoginButtonArea
import com.party.presentation.screen.login.component.LoginScaffoldArea
import com.party.presentation.screen.login.component.LoginScreenBottomArea
import com.party.presentation.screen.login.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        loginViewModel.nextScreen.collectLatest {
            snackBarMessage(snackBarHostState, it.message)

            navController.navigate(Screens.JoinEmail(
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

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> Log.e("KAKAO", "로그인 실패", error)
            token != null -> loginWithKakaoNickName(context, token, loginViewModel)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        loginViewModel.googleSignIn(activityResult = it, context = context)
    }

    LoginScreenContent(
        context = context,
        kakaoCallback = kakaoCallback,
        launcher = launcher,
    )
}

@Composable
fun LoginScreenContent(
    context: Context,
    kakaoCallback: (OAuthToken?, Throwable?) -> Unit,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
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

            LoginButtonArea(
                context = context,
                kakaoCallback = kakaoCallback,
                launcher = launcher,
            )

            LoginScreenBottomArea()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        context = LocalContext.current,
        kakaoCallback = { _, _ -> },
        launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
        }
    )
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