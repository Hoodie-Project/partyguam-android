package com.party.presentation.screen.login.component

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.guam.design.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.guam.design.GRAY200
import com.party.guam.design.KakaoLoginColor
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.BuildConfig

@Composable
fun LoginButtonArea(
    context: Context,
    kakaoCallback: (OAuthToken?, Throwable?) -> Unit,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
) {
    SocialLoginButton(
        text = stringResource(id = R.string.login3),
        containerColor = KakaoLoginColor,
        borderColor = KakaoLoginColor,
        painterImage = painterResource(id = R.drawable.kakao),
        contentDescription = "kakao",
        onClick = {
            loginWithKakao(context, kakaoCallback)
        }
    )

    HeightSpacer(heightDp = 8.dp)

    SocialLoginButton(
        text = stringResource(id = R.string.login4),
        containerColor = WHITE,
        borderColor = GRAY200,
        painterImage = painterResource(id = R.drawable.google),
        contentDescription = "google",
        onClick = {
            // google 로그인
            val googleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_KEY)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
            launcher.launch(googleSignInClient.signInIntent)
        }
    )

    HeightSpacer(heightDp = 24.dp)
}

@Composable
fun SocialLoginButton(
    text: String,
    containerColor: Color,
    borderColor: Color,
    painterImage: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .background(Color.White),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        border = BorderStroke(1.dp, borderColor),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = MEDIUM_PADDING_SIZE)
        ) {
            Icon(
                painter = painterImage,
                contentDescription = contentDescription,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )

            WidthSpacer(widthDp = 12.dp)

            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}

private fun loginWithKakao(context: Context, kakaoCallback: (OAuthToken?, Throwable?) -> Unit){
    if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)){
        // 카카오톡 설치되있는거
        UserApiClient.instance.loginWithKakaoTalk(context){ token, error ->
            if(error != null){
                Log.e("KAKAO", "로그인 실패", error)
            }
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled){
                return@loginWithKakaoTalk
            }

            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    }else {
        // 카카오톡 설치 안 되있는거
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
    }
}