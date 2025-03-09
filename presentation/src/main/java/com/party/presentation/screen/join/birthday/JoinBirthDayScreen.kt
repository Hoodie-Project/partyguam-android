package com.party.presentation.screen.join.birthday

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.Screens
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.WHITE
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

    var isValidBirthDay by remember { mutableStateOf(true) }

    JoinBirthDayScreen(
        userBirthDay = userBirthDay,
        isValidUserBirthDay = isValidBirthDay,
        userNickName = userNickName,
        onString = {
            userBirthDay = it
        },
        onClick = {
            isValidBirthDay = isValidBirthdate(userBirthDay)
            if(isValidBirthDay){
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
    userNickName: String,
    onString: (String) -> Unit,
    onClick: () -> Unit,
    onNavigationClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            JoinBirthdayScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        },
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
                    mainExplain = "$userNickName${stringResource(id = R.string.join_birthday1)}" ,
                    subExplain = stringResource(id = R.string.join_birthday2),
                )

                HeightSpacer(heightDp = 40.dp)

                JoinScreenInputField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    visualTransformation = TimeTransFormation(),
                    keyboardType = KeyboardType.Number,
                    textColor = GRAY400,
                    containerColor = WHITE,
                    borderColor = when {
                        !isValidUserBirthDay -> RED
                        !isFocused -> GRAY200 // 처음에는 GRAY200
                        isFocused -> PRIMARY
                        else -> GRAY200
                    },
                    closeIcon = if(userBirthDay.isNotEmpty()) { painterResource(id = R.drawable.icon_close) } else null,
                    readOnly = false,
                    inputText = userBirthDay,
                    placeHolder = stringResource(id = R.string.join_birthday3),
                    onString = { newValue ->
                        if(newValue.all { it.isDigit() }){
                            onString(newValue)
                        }
                    }
                )

                if(!isValidUserBirthDay){
                    HeightSpacer(heightDp = 12.dp)
                    Text(
                        text = "생년월일을 다시 확인해 주세요.",
                        color = RED,
                        fontSize = B3,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }

            val keyboardController = LocalSoftwareKeyboardController.current

            JoinScreenButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                buttonText = stringResource(id = R.string.common1),
                buttonTextColor = if(userBirthDay.isNotEmpty()) BLACK else GRAY400,
                buttonContainerColor = if(userBirthDay.isNotEmpty()) PRIMARY else LIGHT400,
                buttonBorderColor = if(userBirthDay.isNotEmpty()) PRIMARY else  LIGHT200,
                fontSize = B2,
                fontWeight = FontWeight.Bold,
                onClick = {
                    keyboardController?.hide()
                    onClick()
                }
            )

            HeightSpacer(heightDp = 12.dp)
        }
    }
}

fun isValidBirthdate(birthdate: String): Boolean {
    val regex = Regex("^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$")
    if (!regex.matches(birthdate)) return false

    val year = birthdate.substring(0, 4).toInt()
    val month = birthdate.substring(4, 6).toInt()
    val day = birthdate.substring(6, 8).toInt()

    return isValidDay(year, month, day)
}

fun isValidDay(year: Int, month: Int, day: Int): Boolean {
    val daysInMonth = when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> return false
    }
    return day in 1..daysInMonth
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}


@Preview(showBackground = true)
@Composable
private fun JoinBirthDayScreenContentPreview() {
    JoinBirthDayScreen(
        userBirthDay = "19900131",
        isValidUserBirthDay = true,
        userNickName = "안드로이드맨",
        onString = {},
        onClick = {},
        onNavigationClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun JoinBirthDayScreenContentPreview2() {
    JoinBirthDayScreen(
        userBirthDay = "10000102",
        isValidUserBirthDay = false,
        userNickName = "안드로이드맨11",
        onString = {},
        onClick = {},
        onNavigationClick = {}
    )
}