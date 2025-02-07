package com.party.presentation.screen.join.birthday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.WHITE
import com.party.common.Screens
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinScreenInputField
import com.party.presentation.screen.join.TimeTransFormation
import com.party.presentation.screen.join.birthday.component.JoinBirthdayScaffoldArea

@Composable
fun JoinBirthDayScreenRoute(
    navController: NavHostController,
    userEmail: String,
    signupAccessToken: String,
    userNickName: String,
) {

    val email by rememberSaveable { mutableStateOf(userEmail) }
    val signUpToken by rememberSaveable { mutableStateOf(signupAccessToken) }
    val nickName by rememberSaveable { mutableStateOf(userNickName) }
    var userBirthDay by rememberSaveable { mutableStateOf("") }
    val isValidUserBirthDay by rememberSaveable {
        mutableStateOf(false)
    }.apply { value = isValidBirthdate(userBirthDay) }

    JoinBirthDayScreen(
        userBirthDay = userBirthDay,
        isValidUserBirthDay = isValidUserBirthDay,
        onString = {
            userBirthDay = it
        },
        onClick = {
            if(isValidUserBirthDay){
                navController.navigate(Screens.JoinGender(userEmail = email, signupAccessToken = signUpToken, userNickName = nickName, userBirthDay = userBirthDay))
            }
        },
        onNavigationClick = { navController.popBackStack() }
    )
}

@Composable
private fun JoinBirthDayScreen(
    userBirthDay: String,
    isValidUserBirthDay: Boolean,
    onString: (String) -> Unit,
    onClick: () -> Unit,
    onNavigationClick: () -> Unit
) {
    Scaffold(
        topBar = {
            JoinBirthdayScaffoldArea(
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
                    mainExplain = stringResource(id = R.string.join_birthday1),
                    subExplain = stringResource(id = R.string.join_birthday2),
                )

                JoinScreenInputField(
                    visualTransformation = TimeTransFormation(),
                    keyboardType = KeyboardType.Number,
                    textColor = GRAY400,
                    containerColor = WHITE,
                    borderColor = if(userBirthDay.isEmpty()) GRAY200 else if(isValidBirthdate(userBirthDay)) DARK100 else RED,
                    closeIcon = if(userBirthDay.isNotEmpty()) { painterResource(id = R.drawable.icon_close) } else null,
                    readOnly = false,
                    inputText = userBirthDay,
                    placeHolder = stringResource(id = R.string.join_birthday3),
                    onString = onString
                )
            }

            JoinScreenButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(id = R.string.common1),
                buttonTextColor = if(isValidUserBirthDay) BLACK else GRAY400,
                buttonContainerColor = if(isValidUserBirthDay) PRIMARY else LIGHT400,
                buttonBorderColor = if(isValidUserBirthDay) PRIMARY else  LIGHT200,
                fontSize = B2,
                fontWeight = FontWeight.Bold,
                onClick = onClick
            )
        }
    }
}

fun isValidBirthdate(birthdate: String): Boolean {
    // 생년월일 정규식 패턴
    val regex = Regex("^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$")
    return regex.matches(birthdate)
}

@Preview(showBackground = true)
@Composable
private fun JoinBirthDayScreenContentPreview() {
    JoinBirthDayScreen(
        userBirthDay = "19900101",
        isValidUserBirthDay = true,
        onString = {},
        onClick = {},
        onNavigationClick = {}
    )
}