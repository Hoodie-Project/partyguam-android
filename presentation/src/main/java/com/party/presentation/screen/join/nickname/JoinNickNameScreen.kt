package com.party.presentation.screen.join.nickname

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.makeAccessToken
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.common.Screens
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinScreenInputField
import com.party.presentation.screen.join.JoinViewModel
import com.party.presentation.screen.join.nickname.component.JoinNickNameScaffoldArea
import com.party.presentation.screen.join.nickname.component.WarningArea
import com.party.presentation.screen.join.nickname.component.setInputFieldBorderColor
import com.party.presentation.screen.join.nickname.component.validNickNameInputField
import com.skydoves.sandwich.StatusCode
import kotlinx.coroutines.flow.collectLatest

@Composable
fun JoinNickNameScreenRoute(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    userEmail: String,
    signupAccessToken: String,
    joinViewModel: JoinViewModel = hiltViewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val checkUserNickNameState by joinViewModel.checkNickNameState.collectAsStateWithLifecycle()
    val checkUserNickNameResult = checkUserNickNameState.data

    LaunchedEffect(Unit) {
        joinViewModel.joinFailState.collectLatest {
            snackBarMessage(message = it, snackBarHostState = snackBarHostState)
        }
    }

    val email by rememberSaveable { mutableStateOf(userEmail) }
    val signUpToken by rememberSaveable { mutableStateOf(signupAccessToken) }
    var userNickName by rememberSaveable { mutableStateOf("") }
    val isValidUserNickName by rememberSaveable {
        mutableStateOf(false)
    }.apply { value = validNickNameInputField(userNickName) }

    var isValidUserNickNameServerResponse by rememberSaveable { mutableStateOf(false) } // 서버에서 받은 닉네임 중복체크 결과

    if(!isValidUserNickName){
        isValidUserNickNameServerResponse = false
        joinViewModel.resetCheckNickNameState()
    }

    when(checkUserNickNameState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = checkUserNickNameResult as SuccessResponse<String>
            isValidUserNickNameServerResponse = true
            val message = successResult.data as String
            snackBarMessage(message = message, snackBarHostState = snackBarHostState)
        }
        is UIState.Error -> {
            isValidUserNickNameServerResponse = false
            val errorResult = checkUserNickNameResult as ErrorResponse<String>

            when(errorResult.statusCode){
                StatusCode.Conflict.code -> {
                    snackBarMessage(message = stringResource(id = R.string.join_nickname7), snackBarHostState = snackBarHostState)
                }
                else -> { // 기타 에러
                    snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
                }
            }
        }
        is UIState.Exception -> {
            snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
        }
    }

    JoinNickNameScreen(
        context = context,
        snackBarHostState = snackBarHostState,
        userNickName = userNickName,
        isValidUserNickName = isValidUserNickName,
        isValidUserNickNameServerResponse = isValidUserNickNameServerResponse,
        onString = { userNickName = it },
        onNavigateBack = { navController.popBackStack() },
        onClick = {
            if(aaa(isValidUserNickNameServerResponse, isValidUserNickName)){
                joinViewModel.resetCheckNickNameState()
                isValidUserNickNameServerResponse = false
                navController.navigate(Screens.JoinBirthDay(userEmail = email, signupAccessToken = signUpToken, userNickName = userNickName))
            }else {
                if(isValidUserNickName){
                    // 키보드 내리기
                    keyboardController?.hide()
                    joinViewModel.checkNickName(
                        signupAccessToken = makeAccessToken(context = context, token = signupAccessToken),
                        nickname = userNickName
                    )
                }
            }
        }
    )
}

@Composable
private fun JoinNickNameScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    userNickName: String,
    isValidUserNickName: Boolean,
    isValidUserNickNameServerResponse: Boolean,
    onString: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onClick: () -> Unit,
) {
    var isShowWarningDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            JoinNickNameScaffoldArea(
                onNavigationClick = onNavigateBack
            )
        }
    ) {
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
                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.join_nickname1),
                    subExplain = stringResource(id = R.string.join_nickname2),
                )

                HeightSpacer(heightDp = 40.dp)

                JoinScreenInputField(
                    textColor = GRAY400,
                    containerColor = WHITE,
                    borderColor = setInputFieldBorderColor(text = userNickName),
                    closeIcon = if(userNickName.isNotEmpty()) { painterResource(id = R.drawable.icon_close) } else null,
                    readOnly = false,
                    inputText = userNickName,
                    placeHolder = stringResource(id = R.string.join_nickname3),
                    onString = onString
                )

                HeightSpacer(heightDp = 12.dp)

                WarningArea(
                    context = context,
                    userNickName = userNickName,
                    isValid = isValidUserNickName
                )
            }

            JoinScreenButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = if(aaa(isValidUserNickNameServerResponse, isValidUserNickName)) stringResource(id = R.string.common1) else stringResource(id = R.string.common7),
                buttonTextColor = if(isValidUserNickName) BLACK else GRAY400,
                buttonContainerColor = if(isValidUserNickName) PRIMARY else LIGHT400,
                buttonBorderColor = if(isValidUserNickName) PRIMARY else  LIGHT200,
                fontSize = B2,
                fontWeight = FontWeight.Bold,
                onClick = onClick
            )

            HeightSpacer(heightDp = 12.dp)
        }

        if(isShowWarningDialog){
            TwoButtonDialog(
                dialogTitle = "",
                description = "",
                cancelButtonText = "",
                confirmButtonText = "",
                onCancel = { isShowWarningDialog = false },
                onConfirm = { isShowWarningDialog = true }
            )
        }
    }
}

fun aaa(isValidUserNickNameServerResponse: Boolean, isValidUserNickName: Boolean): Boolean{
    return isValidUserNickName && isValidUserNickNameServerResponse
}

@Preview(showBackground = true)
@Composable
private fun JoinNickNameScreenContentPreview() {
    JoinNickNameScreen(
        context = LocalContext.current,
        snackBarHostState = SnackbarHostState(),
        userNickName = "",
        isValidUserNickName = true,
        isValidUserNickNameServerResponse = true,
        onString = {},
        onNavigateBack = {},
        onClick = {}
    )
}