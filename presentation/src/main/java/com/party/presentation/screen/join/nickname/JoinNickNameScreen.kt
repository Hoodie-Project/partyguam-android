package com.party.presentation.screen.join.nickname

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
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

@Composable
fun JoinNickNameScreen(
    navController: NavHostController,
) {
    var userNickName by rememberSaveable { mutableStateOf("") }
    val isValid by rememberSaveable {
        mutableStateOf(validInputField(userNickName))
    }.apply { value = validInputField(userNickName) }

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
                text = stringResource(id = R.string.join_nickname1),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.join_nickname2),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 40.dp)

            JoinScreenInputField(
                textColor = GRAY400,
                containerColor = WHITE,
                borderColor = setInputFieldBorderColor(text = userNickName),
                closeIcon = if(userNickName.isNotEmpty()) { painterResource(id = R.drawable.close) } else null,
                readOnly = false,
                inputText = userNickName,
                placeHolder = stringResource(id = R.string.join_nickname3),
                onString = { userNickName = it }
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                modifier = Modifier.fillMaxWidth(),
                text = "${userNickName.length}/${stringResource(id = R.string.join_nickname4)}",
                fontSize = B3,
                textColor = GRAY400,
                textAlign = Alignment.CenterEnd,
            )
        }

        JoinScreenButton(
            buttonText = stringResource(id = R.string.common1),
            buttonTextColor = if(isValid) BLACK else GRAY400,
            buttonContainerColor = if(isValid) PRIMARY else LIGHT400,
            buttonBorderColor = if(isValid) PRIMARY else  LIGHT200,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            navController = navController,
            routeScreen = Screens.JoinBirthDay
        )

        HeightSpacer(heightDp = 12.dp)
    }
}

fun setInputFieldBorderColor(text: String): Color{
    return if(text.isEmpty()) GRAY200 else if(text.length <= 15) DARK100 else RED
}

fun validInputField(text: String): Boolean{
    return text.isNotEmpty() && text.length <= 15
}