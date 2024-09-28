package com.party.presentation.screen.join.email

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.party.common.R
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.navigation.Screens
import com.party.presentation.screen.join.JoinScreenButton

@Preview(showBackground = true)
@Composable
fun JoinEmailScreenPreview() {
    JoinEmailScreen(
        navController = rememberNavController(),
        userEmail = "test123@gmail.com",
        signupAccessToken = "123123",
        setActionText = {}
    )
}

@Preview(showBackground = true)
@Composable
fun JoinEmailScreenButtonValidPreview(){
    val email = "tmfrl1590@gmail.com"
    JoinScreenButton(
        modifier = Modifier.fillMaxWidth(),
        buttonText = stringResource(id = R.string.join_email3),
        buttonTextColor = if(email.isNotEmpty()) BLACK else GRAY400,
        buttonContainerColor = if(email.isNotEmpty()) PRIMARY else LIGHT400,
        buttonBorderColor = if(email.isNotEmpty()) PRIMARY else  LIGHT200,
        fontSize = B2,
        fontWeight = FontWeight.Bold,
        onClick = {

        }
    )
}

@Preview(showBackground = true)
@Composable
fun JoinEmailScreenButtonInvalidPreview(){
    val email = ""
    JoinScreenButton(
        modifier = Modifier.fillMaxWidth(),
        buttonText = stringResource(id = R.string.join_email3),
        buttonTextColor = if(email.isNotEmpty()) BLACK else GRAY400,
        buttonContainerColor = if(email.isNotEmpty()) PRIMARY else LIGHT400,
        buttonBorderColor = if(email.isNotEmpty()) PRIMARY else  LIGHT200,
        fontSize = B2,
        fontWeight = FontWeight.Bold,
        onClick = {

        }
    )
}