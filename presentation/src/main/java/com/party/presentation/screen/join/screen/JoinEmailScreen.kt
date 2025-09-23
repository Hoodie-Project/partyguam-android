package com.party.presentation.screen.join.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ScreenExplainArea
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LIGHT200
import com.party.guam.design.LIGHT400
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.join.action.JoinAction
import com.party.presentation.screen.join.component.JoinScreenButton
import com.party.presentation.screen.join.component.JoinScreenInputField
import com.party.presentation.screen.join.component.JoinTitleSection
import com.party.presentation.screen.join.state.JoinState
import com.party.presentation.screen.join.viewmodel.JoinViewModel

@Composable
fun JoinEmailScreenRoute(
    navController: NavHostController,
    joinViewModel: JoinViewModel,
) {
    val joinState by joinViewModel.state.collectAsStateWithLifecycle()

    JoinEmailScreen(
        joinState = joinState,
        onGotoJoinNickName = { navController.navigate(Screens.JoinNickname) },
        onAction = { joinViewModel.onAction(action = it) },
        onConfirmDialog = { navController.popBackStack() }
    )
}


@Composable
fun JoinEmailScreen(
    joinState: JoinState,
    onGotoJoinNickName: () -> Unit = {},
    onAction: (JoinAction) -> Unit = {},
    onConfirmDialog: () -> Unit = {},
){
    Scaffold(
        modifier = Modifier
            .blur(
                radiusX = if (joinState.isShowCancelJoinDialog) 10.dp else 0.dp,
                radiusY = if (joinState.isShowCancelJoinDialog) 10.dp else 0.dp,
            )
        ,
        topBar = {
            JoinTitleSection(
                onNavigateBack = {
                    onAction(JoinAction.OnShowCancelJoinDialog(isShow = true))
                },
                actionIcon = {
                    Text(
                        text = "1/4",
                        fontSize = B2,
                        color = GRAY500,
                        modifier = Modifier.padding(end = 20.dp),
                    )
                }
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
                    mainExplain = "가입을 축하드려요!\n이메일 정보가 맞나요?",
                    subExplain = "나중에 변경할 수 없어요."
                )

                HeightSpacer(heightDp = 40.dp)

                JoinScreenInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                    ,
                    inputText = joinState.email,
                    placeHolder = "",
                    borderColor = GRAY100,
                    containerColor = GRAY100,
                    textColor = GRAY500,
                    closeIcon = null,
                    readOnly = false,
                    onString = {}

                )
            }

            JoinScreenButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                ,
                buttonText = "네, 맞아요",
                buttonTextColor = BLACK,
                buttonContainerColor = PRIMARY,
                buttonBorderColor = PRIMARY,
                fontSize = B2,
                fontWeight = FontWeight.Bold,
                onClick = onGotoJoinNickName
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }

    if(joinState.isShowCancelJoinDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.7f))
                .noRippleClickable {
                    onAction(JoinAction.OnShowCancelJoinDialog(isShow = false))
                }
        ) {
            TwoButtonDialog(
                dialogTitle = "나가기",
                description = "회원가입이 완료되지 않았습니다.\n나가시겠습니까?",
                cancelButtonText = "취소",
                confirmButtonText = "나가기",
                onCancel = {
                    onAction(JoinAction.OnShowCancelJoinDialog(isShow = false))
                },
                onConfirm = {
                    onAction(JoinAction.OnShowCancelJoinDialog(isShow = false))
                    onConfirmDialog()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JoinEmailScreenPreview() {
    JoinEmailScreen(
        joinState = JoinState()
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