package com.party.presentation.screen.join.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.ScreenExplainArea
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.common.Screens
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.utils.noRippleClickable
import com.party.presentation.screen.join.JoinScreenButton
import com.party.presentation.screen.join.JoinScreenInputField
import com.party.presentation.screen.join.email.component.JoinEmailScaffoldArea

@Composable
fun JoinEmailScreenRoute(
    navController: NavHostController,
    userEmail: String,
    signupAccessToken: String,
) {
    var email by rememberSaveable { mutableStateOf(userEmail) }
    val signUpToken by rememberSaveable { mutableStateOf(signupAccessToken) }

    var isShowDialog by remember { mutableStateOf(false) }

    JoinEmailScreen(
        navController = navController,
        userEmail = email,
        isShowDialog = isShowDialog,
        onClick = {
            navController.navigate(
                Screens.JoinNickName(
                userEmail = userEmail,
                signupAccessToken = signUpToken
            ))
        },
        onString = { email = it },
        onNavigationClick = { isShowDialog = it}
    )
}

@Composable
private fun JoinEmailScreen(
    navController: NavHostController,
    userEmail: String,
    isShowDialog: Boolean,
    onClick: () -> Unit,
    onString: (String) -> Unit,
    onNavigationClick: (Boolean) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .blur(
                radiusX = if (isShowDialog) 10.dp else 0.dp,
                radiusY = if (isShowDialog) 10.dp else 0.dp,
            ),
        topBar = {
            JoinEmailScaffoldArea(
                onNavigationClick = { onNavigationClick(!isShowDialog)}
            )
        }
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
                    mainExplain = stringResource(id = R.string.join_email1),
                    subExplain = stringResource(id = R.string.join_email2),
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
                    onString = onString
                )
            }

            JoinScreenButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(id = R.string.join_email3),
                buttonTextColor = if(userEmail.isNotEmpty()) BLACK else GRAY400,
                buttonContainerColor = if(userEmail.isNotEmpty()) PRIMARY else LIGHT400,
                buttonBorderColor = if(userEmail.isNotEmpty()) PRIMARY else  LIGHT200,
                fontSize = B2,
                fontWeight = FontWeight.Bold,
                onClick = onClick
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }

    if(isShowDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.7f))
                .noRippleClickable { onNavigationClick(false) }
        ) {
            TwoButtonDialog(
                dialogTitle = "나가기",
                description = "회원가입이 완료되지 않았습니다.\n나가시겠습니까?",
                cancelButtonText = "취소",
                confirmButtonText = "나가기",
                onCancel = {
                    onNavigationClick(false)
                },
                onConfirm = {
                    onNavigationClick(false)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JoinEmailScreenPreview() {
    JoinEmailScreenRoute(
        navController = rememberNavController(),
        userEmail = "test123@gmail.com",
        signupAccessToken = "123123",
    )
}

@Preview(showBackground = true)
@Composable
private fun JoinEmailScreenButtonValidPreview(){
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
private fun JoinEmailScreenButtonInvalidPreview(){
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