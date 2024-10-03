package com.party.presentation.screen.join.gender

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.navigation.Screens
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.UIState
import com.party.common.snackBarMessage
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinViewModel

@Composable
fun JoinGenderScreen(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    userEmail: String,
    signupAccessToken: String,
    userNickName: String,
    userBirthDay: String,
    setActionText: (String) -> Unit,
    joinViewModel: JoinViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        setActionText("4/4")
    }

    val userSignUpState by joinViewModel.userSignUpState.collectAsState()
    val userSignUpResult = userSignUpState.data

    val email by rememberSaveable { mutableStateOf(userEmail) }
    val signUpToken by rememberSaveable { mutableStateOf(signupAccessToken) }
    val nickName by rememberSaveable { mutableStateOf(userNickName) }
    var birthday by rememberSaveable { mutableStateOf(userBirthDay) }

    var selectedGender by rememberSaveable {
        mutableStateOf("")
    }

    when(userSignUpState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            navController.navigate(Screens.JoinComplete)
        }
        is UIState.Error -> {

        }
        is UIState.Exception -> {
            snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            ScreenExplainArea(
                mainExplain = stringResource(id = R.string.join_gender1),
                subExplain = stringResource(id = R.string.join_gender2),
            )

            SelectGenderArea(
                context = context,
                selectedGender = selectedGender,
                onSelect = {
                    selectedGender = it
                }
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
            onClick = {
                if(selectedGender.isNotEmpty()){
                    joinViewModel.userSignUp(
                        signupAccessToken = "${context.getString(R.string.common5)} $signUpToken",
                        userSignUpRequest = UserSignUpRequest(
                            email = email,
                            nickname = nickName,
                            birth = formatBirthDay(birthday),
                            gender = formatGender(selectedGender),
                        )
                    )
                }
            }
        )

        HeightSpacer(heightDp = 12.dp)
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