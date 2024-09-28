package com.party.presentation.screen.join.email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.navigation.Screens
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinScreenInputField

@Composable
fun JoinEmailScreen(
    navController: NavHostController,
    userEmail: String,
    signupAccessToken: String,
    setActionText: (String) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        setActionText("1/4")
    }
    var email by rememberSaveable { mutableStateOf(userEmail) }
    val signUpToken by rememberSaveable { mutableStateOf(signupAccessToken) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            ScreenExplainArea(
                mainExplain = stringResource(id = R.string.join_email1),
                subExplain = stringResource(id = R.string.join_email2),
            )

            JoinScreenInputField(
                textColor = GRAY500,
                containerColor = GRAY100,
                borderColor = GRAY100,
                closeIcon = null,
                readOnly = true,
                inputText = email,
                placeHolder = "",
                onString = { email = it }
            )
        }

        JoinScreenButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = stringResource(id = R.string.join_email3),
            buttonTextColor = if(email.isNotEmpty()) BLACK else GRAY400,
            buttonContainerColor = if(email.isNotEmpty()) PRIMARY else LIGHT400,
            buttonBorderColor = if(email.isNotEmpty()) PRIMARY else  LIGHT200,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            onClick = {
                navController.navigate(Screens.JoinNickName(
                    userEmail = userEmail,
                    signupAccessToken = signUpToken
                ))
            }
        )
        HeightSpacer(heightDp = 12.dp)
    }
}