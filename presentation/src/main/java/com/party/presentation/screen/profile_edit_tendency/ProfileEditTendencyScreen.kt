package com.party.presentation.screen.profile_edit_tendency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.utils.HeightSpacer
import com.party.common.component.button.CustomButton
import com.party.common.component.profileEditTendencyTabList
import com.party.common.utils.snackBarMessage
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.profile_edit_tendency.component.ProfileEditTendencyScaffoldArea
import com.party.presentation.screen.profile_edit_tendency.component.ProfileEditTendencyTabArea
import com.party.presentation.screen.profile_edit_tendency.component.TendencyArea
import com.party.presentation.screen.profile_edit_tendency.viewmodel.ProfileEditTendencyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileEditTendencyScreenRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    profileEditTendencyTabList: List<String>,
    profileEditTendencyViewModel: ProfileEditTendencyViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        profileEditTendencyViewModel.getPersonalityList()
    }

    LaunchedEffect(key1 = Unit) {
        profileEditTendencyViewModel.twoOverWarning.collectLatest {
            snackBarMessage(snackBarHostState, "최대 2개까지 선택 가능합니다.")
        }
    }

    LaunchedEffect(key1 = Unit) {
        profileEditTendencyViewModel.oneOverWarning.collectLatest {
            snackBarMessage(snackBarHostState, "최대 1개까지 선택 가능합니다.")
        }
    }

    LaunchedEffect(key1 = Unit) {
        profileEditTendencyViewModel.modifySuccess.collectLatest {
            snackBarMessage(snackBarHostState, "수정되었습니다.")
            navController.popBackStack()
        }
    }

    val state by profileEditTendencyViewModel.state.collectAsStateWithLifecycle()

    ProfileEditTendencyScreen(
        snackBarHostState = snackBarHostState,
        profileEditTendencyTabList = profileEditTendencyTabList,
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            when (action) {
                is ProfileEditTendencyAction.OnChangeSelectedTab -> profileEditTendencyViewModel.action(action)
                is ProfileEditTendencyAction.OnApply -> profileEditTendencyViewModel.action(action)
                is ProfileEditTendencyAction.OnSelectTendency -> profileEditTendencyViewModel.action(action)
                is ProfileEditTendencyAction.OnSelectConfidence -> profileEditTendencyViewModel.action(action)
                is ProfileEditTendencyAction.OnSelectChallenge -> profileEditTendencyViewModel.action(action)
                is ProfileEditTendencyAction.OnResetFirstArea -> profileEditTendencyViewModel.action(action)
                is ProfileEditTendencyAction.OnResetSecondArea -> profileEditTendencyViewModel.action(action)
                is ProfileEditTendencyAction.OnResetThirdArea -> profileEditTendencyViewModel.action(action)
            }
        }
    )
}

@Composable
private fun ProfileEditTendencyScreen(
    snackBarHostState: SnackbarHostState,
    profileEditTendencyTabList: List<String>,
    state: ProfileEditTendencyState,
    onNavigationClick: () -> Unit,
    onAction: (ProfileEditTendencyAction) -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            ProfileEditTendencyScaffoldArea(
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
                    .fillMaxSize()
                    .weight(1f)
            ) {

                // 1단계, 2단계, 3단계
                ProfileEditTendencyTabArea(
                    profileEditTendencyTabList = profileEditTendencyTabList,
                    selectedTabText = state.selectedTab,
                    onTabClick = { selectedTab -> onAction(ProfileEditTendencyAction.OnChangeSelectedTab(selectedTab)) }
                )

                // 성향 질문 영역
                TendencyArea(
                    state = state,
                    selectedTabText = state.selectedTab,
                    onSelectTendency = { personalityListOption -> onAction(ProfileEditTendencyAction.OnSelectTendency(personalityListOption)) },
                    onSelectConfidence = { personalityListOption -> onAction(ProfileEditTendencyAction.OnSelectConfidence(personalityListOption)) },
                    onSelectChallenge = { personalityListOption -> onAction(ProfileEditTendencyAction.OnSelectChallenge(personalityListOption)) },
                    onResetFirstArea = { onAction(ProfileEditTendencyAction.OnResetFirstArea) },
                    onResetSecondArea = { onAction(ProfileEditTendencyAction.OnResetSecondArea) },
                    onResetThirdArea = { onAction(ProfileEditTendencyAction.OnResetThirdArea) }
                )
            }

            // 적용하기 버튼
            CustomButton(
                modifier = Modifier
                    .height(48.dp),
                onClick = { onAction(ProfileEditTendencyAction.OnApply) },
                buttonText = "적용하기",
                textWeight = FontWeight.Bold,
                containerColor = PRIMARY,
                contentColor = BLACK,
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditTendencyScreenPreview() {
    ProfileEditTendencyScreenRoute(
        snackBarHostState = SnackbarHostState(),
        navController = rememberNavController(),
        profileEditTendencyTabList = profileEditTendencyTabList
    )
}