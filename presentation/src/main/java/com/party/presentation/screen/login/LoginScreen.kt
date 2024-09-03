package com.party.presentation.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.AnnotatedTextComponent
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.KakaoLoginColor
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.presentation.R


@Composable
fun LoginScreen(
    navController: NavHostController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeightSpacer(heightDp = 32.dp)

        TextComponent(
            text = stringResource(id = R.string.login1),
            fontWeight = FontWeight.Bold,
            fontSize = T2,
        )

        HeightSpacer(heightDp = 12.dp)

        TextComponent(
            text = stringResource(id = R.string.login2),
            fontSize = T3,
        )

        HeightSpacer(heightDp = 40.dp)

        SocialLoginButton(
            text = stringResource(id = R.string.login3),
            containerColor = KakaoLoginColor,
            borderColor = KakaoLoginColor,
            painterImage = painterResource(id = R.drawable.kakao),
            contentDescription = "kakao",
            navHostController = navController,
        )

        HeightSpacer(heightDp = 8.dp)

        SocialLoginButton(
            text = stringResource(id = R.string.login4),
            containerColor = WHITE,
            borderColor = GRAY200,
            painterImage = painterResource(id = R.drawable.google),
            contentDescription = "google",
            navHostController = navController,
        )

        HeightSpacer(heightDp = 24.dp)

        AnnotatedTextComponent(
            annotatedString = makeAnnotatedString(
                text1 = stringResource(id = R.string.login5),
                text2 = stringResource(id = R.string.login6),
                text3 = stringResource(id = R.string.login7),
            ),
            textColor = GRAY600,
            fontSize = B2,
        )

        HeightSpacer(heightDp = 40.dp)

        TextComponent(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login8),
            textColor = GRAY500,
            textAlign = Alignment.Center,
            fontSize = B2,
            textDecoration = TextDecoration.Underline,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}