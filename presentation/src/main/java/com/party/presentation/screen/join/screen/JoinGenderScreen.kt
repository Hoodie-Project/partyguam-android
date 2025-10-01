package com.party.presentation.screen.join.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.Screens
import com.party.common.component.snackbar.CustomSnackBar
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ScreenExplainArea
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY500
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.join.component.JoinScreenButton
import com.party.presentation.screen.join.viewmodel.JoinViewModel
import com.party.presentation.screen.join.action.JoinAction
import com.party.presentation.screen.join.component.JoinTitleSection
import com.party.presentation.screen.join.component.SelectGenderSection
import com.party.presentation.screen.join.state.JoinState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun JoinGenderScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    joinViewModel: JoinViewModel,
){
    val joinState by joinViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit){
        joinViewModel.goToJoinCompleteScreen.collectLatest {
            navController.navigate(route = Screens.JoinComplete)
        }
    }

    JoinGenderScreen(
        joinState = joinState,
        snackBarHostState = snackBarHostState,
        onNavigateBack = { navController.popBackStack() },
        onAction = { joinViewModel.onAction(action = it) },
    )
}

@Composable
private fun JoinGenderScreen(
    joinState: JoinState,
    snackBarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit =  {},
    onAction: (JoinAction) -> Unit = {},
){
    Scaffold(
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
                        text = "4/4",
                        fontSize = fs(B2),
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
                    mainExplain = "성별은\n어떻게 되시나요?",
                    subExplain = "프로필에서 노출 여부를 설정할 수 있어요."
                )

                HeightSpacer(heightDp = 40.dp)

                SelectGenderSection(
                    selectedGender = joinState.gender,
                    onSelect = { gender ->
                        onAction(JoinAction.OnSelectGender(gender = gender))
                    }
                )
            }

            JoinScreenButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                ,
                buttonText = "완료",
                buttonTextColor = BLACK,
                buttonContainerColor = PRIMARY,
                buttonBorderColor = PRIMARY,
                fontSize = fs(B2),
                fontWeight = FontWeight.Bold,
                onClick = { onAction(JoinAction.OnJoinComplete) }
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewJoinGenderScreen(){
    JoinGenderScreen(
        joinState = JoinState(),
        snackBarHostState = SnackbarHostState()
    )
}