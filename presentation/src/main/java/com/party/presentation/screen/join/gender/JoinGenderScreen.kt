package com.party.presentation.screen.join.gender

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.utils.LoadingProgressBar
import com.party.common.R
import com.party.common.utils.ScreenExplainArea
import com.party.common.UIState
import com.party.common.utils.makeAccessToken
import com.party.common.utils.snackBarMessage
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.LIGHT200
import com.party.guam.design.LIGHT400
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.common.Screens
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinViewModel
import com.party.presentation.screen.join.gender.component.JoinGenderScaffoldArea
import com.party.presentation.screen.join.gender.component.SelectGenderArea
import kotlinx.coroutines.flow.collectLatest

@Composable
fun JoinGenderScreenRoute(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    userEmail: String,
    signupAccessToken: String,
    userNickName: String,
    userBirthDay: String,
    joinViewModel: JoinViewModel = hiltViewModel()
) {

    val userSignUpState by joinViewModel.userSignUpState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        joinViewModel.joinSuccessState.collectLatest {
            navController.navigate(Screens.JoinComplete(userNickName = userNickName))
        }
    }

    LaunchedEffect(Unit) {
        joinViewModel.joinFailState.collectLatest {
            snackBarMessage(message = it, snackBarHostState = snackBarHostState)
        }
    }

    val email by rememberSaveable { mutableStateOf(userEmail) }
    val signUpToken by rememberSaveable { mutableStateOf(signupAccessToken) }
    val nickName by rememberSaveable { mutableStateOf(userNickName) }
    val birthday by rememberSaveable { mutableStateOf(userBirthDay) }

    var selectedGender by rememberSaveable {
        mutableStateOf("")
    }

    when(userSignUpState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {}
        is UIState.Error -> {}
        is UIState.Exception -> {
            snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
        }
    }

    JoinGenderScreen(
        context = context,
        selectedGender = selectedGender,
        onSelect = { selectedGender = it },
        onClick = {
            if(selectedGender.isNotEmpty()){
                joinViewModel.userSignUp(
                    signupAccessToken = makeAccessToken(context = context, token = signUpToken),
                    userSignUpRequest = UserSignUpRequest(
                        //email = email,
                        nickname = nickName,
                        birth = formatBirthDay(birthday),
                        gender = formatGender(selectedGender),
                    )
                )
            }
        },
        onNavigationClick = { navController.popBackStack()}
    )
}

@Composable
private fun JoinGenderScreen(
    context: Context,
    selectedGender: String,
    onSelect: (String) -> Unit,
    onClick: () -> Unit,
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            JoinGenderScaffoldArea(
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
                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.join_gender1),
                    subExplain = stringResource(id = R.string.join_gender2),
                )

                HeightSpacer(heightDp = 40.dp)

                SelectGenderArea(
                    context = context,
                    selectedGender = selectedGender,
                    onSelect = onSelect
                )
            }

            JoinScreenButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(id = R.string.common2),
                buttonTextColor = if(selectedGender.isNotEmpty()) BLACK else GRAY400,
                buttonContainerColor = if(selectedGender.isNotEmpty()) PRIMARY else LIGHT400,
                buttonBorderColor = if(selectedGender.isNotEmpty()) PRIMARY else  LIGHT200,
                fontSize = B2,
                fontWeight = FontWeight.Bold,
                onClick = onClick
            )

            HeightSpacer(heightDp = 12.dp)
        }
    }
}

private fun formatGender(selectedGender: String): String{
    return if (selectedGender == "남자") "M" else "F"
}

private fun formatBirthDay(birth: String): String {
    // 입력 값이 8자리인지 확인
    if (birth.length != 8) {
        throw IllegalArgumentException("생년월일은 8자리여야 합니다 (예: 19920329)")
    }

    // 년, 월, 일로 분리
    val year = birth.substring(0, 4)
    val month = birth.substring(4, 6)
    val day = birth.substring(6, 8)

    // 변환된 형식으로 반환
    return "$year-$month-$day"
}

@Preview(showBackground = true)
@Composable
private fun JoinGenderScreenContentPreview() {
    JoinGenderScreen(
        context = LocalContext.current,
        selectedGender = "",
        onSelect = {},
        onClick = {},
        onNavigationClick = {}
    )
}