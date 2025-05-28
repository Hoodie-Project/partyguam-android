package com.party.presentation.screen.profile_edit_time

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ScreenExplainArea
import com.party.common.component.button.ResetAndApplyButtonArea
import com.party.common.utils.snackBarMessage
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.profile_edit_time.component.ProfileEditTimeScaffoldArea
import com.party.presentation.screen.profile_edit_time.component.TimeSelectArea
import com.party.presentation.screen.profile_edit_time.viewmodel.ProfileEditTimeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileEditTimeScreenRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    profileEditTimeViewModel: ProfileEditTimeViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        profileEditTimeViewModel.getPersonalityList()
    }

    LaunchedEffect(key1 = Unit) {
        profileEditTimeViewModel.twoOverWarning.collectLatest {
            snackBarMessage(snackBarHostState, "최대 2개까지 선택 가능합니다.")
        }
    }

    LaunchedEffect(key1 = Unit) {
        profileEditTimeViewModel.modifySuccess.collectLatest {
            snackBarMessage(snackBarHostState, "수정되었습니다.")
            navController.popBackStack()
        }
    }

    val state by profileEditTimeViewModel.state.collectAsStateWithLifecycle()

    ProfileEditTimeScreen(
        snackBarHostState = snackBarHostState,
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onAction = { profileEditTimeViewModel.onAction(it) }
    )
}

@Composable
private fun ProfileEditTimeScreen(
    snackBarHostState: SnackbarHostState,
    state: ProfileEditTimeState,
    onNavigationClick: () -> Unit,
    onAction: (ProfileEditTimeAction) -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            ProfileEditTimeScaffoldArea(
                onNavigationClick = onNavigationClick
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
                    mainExplain = "주로 작업하는 시간대는\n어떻게 되시나요?",
                    subExplain = "비슷한 성향의 파티원을 추천해 드려요. (최대 2개)"
                )

                // Time Select Area
                HeightSpacer(heightDp = 20.dp)
                TimeSelectArea(
                    personalityList = state.personalityList,
                    selectedList = state.selectedList,
                    onSelect = { selectedItem -> onAction(ProfileEditTimeAction.OnSelectPersonality(selectedItem)) }
                )
            }

            ResetAndApplyButtonArea(
                onReset = { onAction(ProfileEditTimeAction.OnReset) },
                onApply = { onAction(ProfileEditTimeAction.OnApply) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditTimeScreenPreview() {
    ProfileEditTimeScreenRoute(
        snackBarHostState = SnackbarHostState(),
        navController = rememberNavController(),
    )
}