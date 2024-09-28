package com.party.presentation.screen.join.nickname

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.TextComponent
import com.party.common.WarningDialog
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinScreenInputField

@Composable
fun JoinNickNameScreen(
    navController: NavHostController,
    context: Context,
    userEmail: String,
    signupAccessToken: String,
    setActionText: (String) -> Unit,
    isShowWarningDialog: Boolean,
    onClose: (Boolean) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        setActionText("2/4")
    }

    var email by rememberSaveable { mutableStateOf(userEmail) }
    val signUpToken by rememberSaveable { mutableStateOf(signupAccessToken) }
    var userNickName by rememberSaveable { mutableStateOf("") }
    val isValidUserNickName by rememberSaveable {
        mutableStateOf(false)
    }.apply { value = validNickNameInputField(userNickName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            ScreenExplainArea(
                mainExplain = stringResource(id = R.string.join_nickname1),
                subExplain = stringResource(id = R.string.join_nickname2),
            )

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

            WarningArea(
                context = context,
                userNickName = userNickName,
                isValid = isValidUserNickName
            )
        }

        JoinScreenButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = stringResource(id = R.string.common1),
            buttonTextColor = if(isValidUserNickName) BLACK else GRAY400,
            buttonContainerColor = if(isValidUserNickName) PRIMARY else LIGHT400,
            buttonBorderColor = if(isValidUserNickName) PRIMARY else  LIGHT200,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            onClick = {

            }
        )

        HeightSpacer(heightDp = 12.dp)
    }

    if(isShowWarningDialog){
        WarningDialog(
            onClose = { onClose(false) }
        )
    }
}