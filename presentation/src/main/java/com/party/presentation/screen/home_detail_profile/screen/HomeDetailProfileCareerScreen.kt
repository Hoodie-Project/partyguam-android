package com.party.presentation.screen.home_detail_profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.snackbar.CustomSnackBar
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ProfileIndicatorSection
import com.party.common.utils.ScreenExplainArea
import com.party.common.utils.StepInfo
import com.party.common.utils.StepStatus
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LIGHT400
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.component.DetailProfileNextButton
import com.party.presentation.screen.detail.component.DetailCarrierScaffoldArea
import com.party.presentation.screen.home_detail_profile.action.HomeDetailProfileAction
import com.party.presentation.screen.home_detail_profile.component.PositionSection
import com.party.presentation.screen.home_detail_profile.state.HomeDetailProfileState
import com.party.presentation.screen.home_detail_profile.viewmodel.HomeDetailProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeDetailProfileCareerRoute(
    viewModel: HomeDetailProfileViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.successSaveCareer.collectLatest {
            navController.navigate(route = Screens.Trait1)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.existSavedData.collectLatest {
            snackBarHostState.showSnackbar("이미 저장된 데이터가 있습니다.")
        }
    }

    if(state.isShowFinishDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.8f))
        ) {
            TwoButtonDialog(
                dialogTitle = "나가기",
                description = "입력한 내용들이 모두 초기화됩니다.\n나가시겠습니까?",
                cancelButtonText = "취소",
                confirmButtonText = "나가기",
                onCancel = { viewModel.onAction(action = HomeDetailProfileAction.OnShowFinishDialog(isShow = false)) },
                onConfirm = {
                    navController.navigate(Screens.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true } // 모든 백 스택 제거
                        launchSingleTop = true // 중복 방지
                    }
                }
            )
        }
    }

    HomeDetailProfileCareerScreen(
        snackBarHostState = snackBarHostState,
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onClose = { viewModel.onAction(action = HomeDetailProfileAction.OnShowFinishDialog(isShow = true))},
        onGoToChoiceCarrierPosition = { navController.navigate(route = Screens.HomeDetailChoiceCarrierPosition(isMain = it))},
        onAction = { action ->  viewModel.onAction(action = action)},
        onGotoNext = { viewModel.saveCareer() },
        onSkip = { navController.navigate(route = Screens.Trait1) }
    )
}

@Composable
private fun HomeDetailProfileCareerScreen(
    snackBarHostState: SnackbarHostState,
    state: HomeDetailProfileState,
    onNavigationClick: () -> Unit,
    onClose: () -> Unit = {},
    onGoToChoiceCarrierPosition: (Boolean) -> Unit,
    onAction: (HomeDetailProfileAction) -> Unit = {},
    onGotoNext: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
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
            DetailCarrierScaffoldArea(
                onNavigationClick = onNavigationClick,
                onClose = onClose,
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
                val steps = listOf(
                    StepInfo("1", "관심지역", StepStatus.COMPLETED),
                    StepInfo("2", "경력/포지션", StepStatus.CURRENT),
                    StepInfo("3", "성향선택(1/4)", StepStatus.PENDING)
                )

                ProfileIndicatorSection(steps = steps)

                ScreenExplainArea(
                    mainExplain = "***님의\n경력과 포지션을 입력해 주세요",
                    subExplain = stringResource(id = R.string.detail_carrier2),
                )

                HeightSpacer(heightDp = 40.dp)

                // 주포지션, 부포지션 선택
                PositionSection(
                    state = state,
                    onGoToChoiceCarrierPosition = onGoToChoiceCarrierPosition,
                    onFirstReset = { onAction(HomeDetailProfileAction.OnResetFirst)},
                    onSecondReset = { onAction(HomeDetailProfileAction.OnResetSecond)},
                )
            }

            DetailProfileNextButton(
                text = stringResource(id = R.string.common1),
                textColor = if( (state.firstCareer.isNotEmpty() && state.firstMainPosition.isNotEmpty() && state.firstSubPosition.isNotEmpty()) ||  state.secondCareer.isNotEmpty() && state.secondMainPosition.isNotEmpty() && state.secondSubPosition.isNotEmpty()) BLACK else GRAY400,
                containerColor = if( (state.firstCareer.isNotEmpty() && state.firstMainPosition.isNotEmpty() && state.firstSubPosition.isNotEmpty()) ||  state.secondCareer.isNotEmpty() && state.secondMainPosition.isNotEmpty() && state.secondSubPosition.isNotEmpty()) PRIMARY else LIGHT400,
                onClick = onGotoNext
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                ,
                text = stringResource(id = R.string.common3),
                fontSize = B2,
                textColor = GRAY500,
                align = Alignment.Center,
                textDecoration = TextDecoration.Underline,
                onClick = onSkip
            )
        }
    }
}