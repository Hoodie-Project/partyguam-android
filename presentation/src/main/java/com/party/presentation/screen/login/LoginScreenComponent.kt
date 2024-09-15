package com.party.presentation.screen.login

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.party.common.WidthSpacer
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.navigation.Screens
import com.party.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SocialLoginButton(
    text: String,
    containerColor: Color,
    borderColor: Color,
    painterImage: Painter,
    contentDescription: String,
    navHostController: NavHostController,
    context: Context,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        loginViewModel.loginGoogle(activityResult = it)
    }

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
        onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                val googleSignInOptions = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("606792749750-61uarn8jqb51ht3pkpo3f9pc92skop0c.apps.googleusercontent.com")
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
                launcher.launch(googleSignInClient.signInIntent)
            }
            //navHostController.navigate(Screens.JoinEmail)
        }
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

fun makeAnnotatedString(
    text1: String,
    text2: String,
    text3: String,
): AnnotatedString {
    return buildAnnotatedString {
        append(text1)
        withStyle(
            SpanStyle(
                color = GRAY600,
                textDecoration = TextDecoration.Underline,
            )
        ){
            append(text2)
        }
        append(text3)
    }
}

@Composable
fun aasd(
    context: Context,
    loginViewModel: LoginViewModel,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
){


}

