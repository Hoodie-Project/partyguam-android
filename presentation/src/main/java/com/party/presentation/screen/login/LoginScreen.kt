package com.party.presentation.screen.login

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.AnnotatedTextComponent
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.TextComponent
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.navigation.Screens
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScreenExplainArea(
            mainExplain = stringResource(id = R.string.login1),
            subExplain = stringResource(id = R.string.login2),
        )

        LoginButtonArea(
            context = context,
            navController = navController,
            loginViewModel = loginViewModel,
        )

        LoginScreenBottomArea(
            navController = navController,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        context = LocalContext.current,
        snackBarHostState = SnackbarHostState(),
    )
}