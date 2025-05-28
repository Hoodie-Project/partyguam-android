package com.party.presentation.screen.profile_edit_career

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.party.common.utils.ScreenExplainArea
import com.party.common.component.button.CustomButton
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveCarrierRequest
import com.party.presentation.screen.profile_edit_career.component.ProfileEditCareerScaffoldArea
import com.party.presentation.screen.profile_edit_career.component.ProfileEditCareerSelectedPositionArea
import com.party.presentation.screen.profile_edit_career.viewmodel.ProfileEditCareerViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileEditCareerScreenRoute(
    navController: NavHostController,
    profileEditCareerViewModel: ProfileEditCareerViewModel = hiltViewModel()
) {

    val state by profileEditCareerViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        profileEditCareerViewModel.saveSuccessState.collectLatest {
            navController.popBackStack()
        }
    }

    when(state.isShowPrevScreen) {
        true -> {
            ProfileEditCareerScreen(
                state = state,
                onNavigationClick = { navController.popBackStack() },
                onAction = { action ->
                    profileEditCareerViewModel.onAction(action = action)
                },
                onApply = {
                    val carrierRequests = listOfNotNull(
                        SaveCarrierRequest(
                            positionId = state.getMainPosition?.position?.id ?: 0,
                            years = state.getMainPosition?.years ?: 0,
                            careerType = "primary"
                        ),
                        state.getSubPosition?.years?.let {
                            SaveCarrierRequest(
                                positionId = state.getSubPosition?.position?.id ?: 0,
                                years = it,
                                careerType = "secondary"
                            )
                        }
                    )
                    val saveCarrierList = SaveCarrierList(career = carrierRequests)
                    profileEditCareerViewModel.deleteCareer(career = saveCarrierList)
                }
            )
        }
        false -> {
            SelectCareerAndPositionScreen(
                selectedCareer = if(state.isMainPosition) "${state.getMainPosition?.years.toString()}년" else "${state.getSubPosition?.years.toString()}년",
                selectedMainPosition = if(state.isMainPosition) state.getMainPosition?.position?.main ?: "" else state.getSubPosition?.position?.main ?: "",
                selectedSubPosition = if(state.isMainPosition) state.getMainPosition?.position?.sub ?: "" else state.getSubPosition?.position?.sub ?: "",
                userProfileState = state,
                onNavigationClick = {
                    profileEditCareerViewModel.navigateScreen()
                },
                onAction = { action ->
                    when(action){
                        is ProfileEditCareerAction.OnChangePrevScreen -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnChangeMainOrSub -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnGetSubPositionList -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnResetPrimaryPosition -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnResetSecondaryPosition -> profileEditCareerViewModel.onAction(action)
                    }
                },
                onAdd = { years, main, sub, subId ->
                    if(state.isMainPosition){
                        profileEditCareerViewModel.setCarrier(
                            isMain = true,
                            year = if(years == "신입") "0" else years,
                            main = main,
                            sub = sub,
                            id = subId
                        )
                    } else {
                        profileEditCareerViewModel.setCarrier(
                            isMain = false,
                            year = if(years == "신입") "0" else years,
                            main = main,
                            sub = sub,
                            id = subId
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileEditCareerScreen(
    state: ProfileEditCareerState,
    onNavigationClick: () -> Unit,
    onAction: (ProfileEditCareerAction) -> Unit,
    onApply: () -> Unit
) {
    Scaffold(
        topBar = {
            ProfileEditCareerScaffoldArea(
                title = "경력/포지션",
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
                    mainExplain = "***님의\n경력과 포지션을 입력해 주세요.",
                    subExplain = "경력과 포지션을 입력하고 파티를 추천받으세요."
                )

                // 주포지션, 부포지션
                HeightSpacer(heightDp = 40.dp)
                ProfileEditCareerSelectedPositionArea(
                    state = state,
                    onGoToSelectCareerAndPosition = {
                        isMain -> onAction(ProfileEditCareerAction.OnChangeMainOrSub(isMain = isMain))
                        onAction(ProfileEditCareerAction.OnChangePrevScreen(isShowPrevScreen = false))
                    },
                    onResetSelectedFirst = {
                        onAction(ProfileEditCareerAction.OnResetPrimaryPosition)
                    },
                    onResetSelectedSecond = {
                        onAction(ProfileEditCareerAction.OnResetSecondaryPosition)
                    }
                )
            }

            CustomButton(
                onClick = onApply,
                buttonText = "적용하기",
                textWeight = FontWeight.Bold,
                modifier = Modifier
                    .height(48.dp)
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditCareerScreenPreview() {
    ProfileEditCareerScreenRoute(
        navController = rememberNavController(),
    )
}