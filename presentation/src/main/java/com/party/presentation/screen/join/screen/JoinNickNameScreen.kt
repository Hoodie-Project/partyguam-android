package com.party.presentation.screen.join.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.Screens
import com.party.common.component.button.CustomButton
import com.party.common.component.snackbar.CustomSnackBar
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ScreenExplainArea
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.B3
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LIGHT200
import com.party.guam.design.LIGHT400
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.RED
import com.party.guam.design.WHITE
import com.party.presentation.screen.join.action.JoinAction
import com.party.presentation.screen.join.component.JoinScreenInputField
import com.party.presentation.screen.join.component.JoinTitleSection
import com.party.presentation.screen.join.component.setInputFieldBorderColor
import com.party.presentation.screen.join.state.JoinState
import com.party.presentation.screen.join.viewmodel.JoinViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun JoinNickNameScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    joinViewModel: JoinViewModel,
) {
    val joinState by joinViewModel.state.collectAsStateWithLifecycle()

    // 중복체크 성공하면 생년월일 화면 이동
    LaunchedEffect(key1 = Unit){
        joinViewModel.goToBirthDayScreen.collectLatest {
            navController.navigate(Screens.JoinBirthDay)
        }
    }

    JoinNickNameScreen(
        joinState = joinState,
        snackBarHostState = snackBarHostState,
        onNavigateBack = { navController.popBackStack() },
        onAction = { joinViewModel.onAction(action = it) },
    )
}

@Composable
private fun JoinNickNameWarningSection(
    userNickName: String,
    warningText: String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 6.dp),
            text = warningText,
            fontSize = fs(B3),
            color = RED,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${userNickName.length}",
                color = if(userNickName.length > 15 || userNickName.length < 2) RED else GRAY400
            )
            Text(
                text = " / ",
                color = GRAY400
            )
            Text(
                text = "15",
                color = GRAY400
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JoinNickNameScreen(
    joinState: JoinState,
    snackBarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit = {},
    onAction: (JoinAction) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    CustomSnackBar(
                        message = data.visuals.message
                    )
                }
            )
        },
        topBar = {
            JoinTitleSection(
                onNavigateBack = onNavigateBack,
                actionIcon = {
                    Text(
                        text = "2/4",
                        fontSize = fs(B2),
                        color = GRAY500,
                        modifier = Modifier.padding(end = 20.dp),
                    )
                }
            )
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .background(WHITE)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus()
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MEDIUM_PADDING_SIZE)
            ) {
                Column(
                    modifier = Modifier.
                    weight(1f)
                ) {
                    ScreenExplainArea(
                        mainExplain = "어떻게 불러드리면 될까요?\n닉네임을 입력해 주세요.",
                        subExplain = "닉네임은 나중에 변경할 수 없어요."
                    )

                    HeightSpacer(40.dp)

                    JoinScreenInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                        ,
                        inputText = joinState.userNickName,
                        placeHolder = "15자 이내로 입력해 주세요. (영문/숫자/한글)",
                        borderColor = setInputFieldBorderColor(joinState.userNickName),
                        containerColor = WHITE,
                        textColor = if (joinState.userNickName.isNotEmpty()) BLACK else GRAY400,
                        closeIcon = null,
                        readOnly = false,
                        onString = {
                            onAction(JoinAction.OnInputNickName(it))
                        }

                    )
                    HeightSpacer(12.dp)

                    JoinNickNameWarningSection(
                        warningText = joinState.warningMessage,
                        userNickName = joinState.userNickName,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.ime)
                    .padding(horizontal = MEDIUM_PADDING_SIZE, vertical = 12.dp)
            ) {
                Column {
                    CustomButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        buttonText = "다음",
                        contentColor = if (joinState.userNickName.isNotEmpty()) BLACK else GRAY400,
                        containerColor = if (joinState.userNickName.isNotEmpty()) PRIMARY else LIGHT400,
                        borderColor = if (joinState.userNickName.isNotEmpty()) PRIMARY else LIGHT200,
                        textSize = fs(B2),
                        textWeight = FontWeight.Bold,
                        onClick = {
                            onAction(JoinAction.OnCheckNickName)
                        }
                    )
                    HeightSpacer(heightDp = 12.dp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewJoinNickNameScreen(){
    JoinNickNameScreen(
        joinState = JoinState(
            userNickName = "닉네임"
        ),
        snackBarHostState = SnackbarHostState()
    )
}