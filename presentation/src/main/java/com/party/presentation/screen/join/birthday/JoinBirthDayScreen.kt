package com.party.presentation.screen.join.birthday

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.R
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinScreenInputField
import com.party.presentation.screen.join.nickname.validNickNameInputField

@Composable
fun JoinBirthDayScreen(
    navController: NavHostController,
) {
    var userBirthDay by rememberSaveable { mutableStateOf("") }
    val isValid by rememberSaveable {
        mutableStateOf(false)
    }.apply { value = isValidBirthdate(userBirthDay) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            HeightSpacer(heightDp = 32.dp)

            TextComponent(
                text = stringResource(id = R.string.join_birthday1),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.join_birthday2),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 40.dp)

            JoinScreenInputField(
                keyboardType = KeyboardType.Number,
                textColor = GRAY400,
                containerColor = WHITE,
                borderColor = if(userBirthDay.isEmpty()) GRAY200 else if(isValidBirthdate(userBirthDay)) DARK100 else RED,
                closeIcon = if(userBirthDay.isNotEmpty()) { painterResource(id = R.drawable.close) } else null,
                readOnly = false,
                inputText = userBirthDay,
                placeHolder = stringResource(id = R.string.join_birthday3),
                onString = { userBirthDay = it }
            )
        }

        JoinScreenButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = stringResource(id = R.string.common1),
            buttonTextColor = if(isValid) BLACK else GRAY400,
            buttonContainerColor = if(isValid) PRIMARY else LIGHT400,
            buttonBorderColor = if(isValid) PRIMARY else  LIGHT200,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            navController = navController,
            routeScreen = Screens.JoinGender
        )
    }
}

fun isValidBirthdate(birthdate: String): Boolean {
    // 생년월일 정규식 패턴
    val regex = Regex("^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")
    return regex.matches(birthdate)
}