package com.party.presentation.screen.join.email

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
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
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.R
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinScreenInputField

@Composable
fun JoinEmailScreen(
    navController: NavHostController,
) {
    var userEmail by rememberSaveable { mutableStateOf("tmfrl1590@gmail.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            HeightSpacer(heightDp = 32.dp)

            TextComponent(
                text = stringResource(id = R.string.join_email1),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.join_email2),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 40.dp)

            JoinScreenInputField(
                textColor = GRAY500,
                containerColor = GRAY100,
                borderColor = GRAY100,
                closeIcon = null,
                readOnly = true,
                inputText = userEmail,
                placeHolder = "",
                onString = { userEmail = it }
            )
        }

        JoinScreenButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = stringResource(id = R.string.join_email3),
            buttonTextColor = BLACK,
            buttonContainerColor = PRIMARY,
            buttonBorderColor = PRIMARY,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            navController = navController,
            routeScreen = Screens.JoinNickName
        )

        HeightSpacer(heightDp = 12.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    JoinEmailScreen(navController = rememberNavController())
}
