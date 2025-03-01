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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.HeightSpacer
import com.party.common.ScreenExplainArea
import com.party.common.component.button.CustomButton
import com.party.common.convertToIntFromYear
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
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
    var selectedCareerFirst by remember { mutableStateOf("") }
    var selectedMainPositionFirst by remember { mutableStateOf("") }
    var selectedSubPositionFirst by remember { mutableStateOf("") }
    var selectedSubPositionIdFirst by remember { mutableIntStateOf(0) }

    var selectedCareerSecond by remember { mutableStateOf("") }
    var selectedMainPositionSecond by remember { mutableStateOf("") }
    var selectedSubPositionSecond by remember { mutableStateOf("") }
    var selectedSubPositionIdSecond by remember { mutableIntStateOf(0) }

    val userProfileState by profileEditCareerViewModel.state.collectAsStateWithLifecycle()
    /*if (userProfileState.getCarrierList.isNotEmpty()){
        userProfileState.getCarrierList.forEach {
             if (it.careerType == "primary"){
                selectedCareerFirst = "${it.years}년"
                selectedMainPositionFirst = it.
                selectedSubPositionFirst = it.subPosition
                selectedSubPositionIdFirst = it.positionId
            }else {
                selectedCareerSecond = "${it.years}년"
                selectedMainPositionSecond = it.position
                selectedSubPositionSecond = it.subPosition
                selectedSubPositionIdSecond = it.positionId        }
    }*/

    LaunchedEffect(Unit) {
        profileEditCareerViewModel.saveSuccessState.collectLatest {
            navController.popBackStack()
        }
    }

    when(userProfileState.isShowPrevScreen) {
        true -> {
            ProfileEditCareerScreen(
                selectedCareerFirst = selectedCareerFirst,
                selectedMainPositionFirst = selectedMainPositionFirst,
                selectedSubPositionFirst = selectedSubPositionFirst,
                selectedCareerSecond = selectedCareerSecond,
                selectedMainPositionSecond = selectedMainPositionSecond,
                selectedSubPositionSecond = selectedSubPositionSecond,
                userProfileState = userProfileState,
                onNavigationClick = { navController.popBackStack() },
                onAction = { action ->
                    when(action){
                        is ProfileEditCareerAction.OnChangePrevScreen -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnChangeMainOrSub -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnGetSubPositionList -> profileEditCareerViewModel.onAction(action)
                    }
                },
                onResetSelectedFirst = {
                    selectedCareerFirst = ""
                    selectedMainPositionFirst = ""
                    selectedSubPositionFirst = ""
                    selectedSubPositionIdFirst = 0
                },
                onResetSelectedSecond = {
                    selectedCareerSecond = ""
                    selectedMainPositionSecond = ""
                    selectedSubPositionSecond = ""
                    selectedSubPositionIdSecond = 0
                },
                onApply = {
                    val carrierRequests = listOfNotNull(
                        SaveCarrierRequest(
                            positionId = selectedSubPositionIdFirst,
                            years = convertToIntFromYear(selectedCareerFirst),
                            careerType = "primary"
                        ),
                        selectedCareerSecond.takeIf { it.isNotEmpty() }?.let {
                            SaveCarrierRequest(
                                positionId = selectedSubPositionIdSecond,
                                years = convertToIntFromYear(selectedCareerSecond),
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
                selectedCareer = if(userProfileState.isMainPosition) selectedCareerFirst else selectedCareerSecond,
                selectedMainPosition = if(userProfileState.isMainPosition) selectedMainPositionFirst else selectedMainPositionSecond,
                selectedSubPosition = if(userProfileState.isMainPosition) selectedSubPositionFirst else selectedSubPositionSecond,
                userProfileState = userProfileState,
                onNavigationClick = {
                    profileEditCareerViewModel.navigateScreen()
                },
                onAction = { action ->
                    when(action){
                        is ProfileEditCareerAction.OnChangePrevScreen -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnChangeMainOrSub -> profileEditCareerViewModel.onAction(action)
                        is ProfileEditCareerAction.OnGetSubPositionList -> profileEditCareerViewModel.onAction(action)
                    }
                },
                onSelectCareer = {
                    if(userProfileState.isMainPosition) selectedCareerFirst = it else selectedCareerSecond = it
                },
                onSelectMainPosition = {
                    if(userProfileState.isMainPosition) selectedMainPositionFirst = it else selectedMainPositionSecond = it
                },
                onSelectSubPosition = {
                    if(userProfileState.isMainPosition) {
                        selectedSubPositionFirst = it.sub
                        selectedSubPositionIdFirst = it.id
                    } else {
                        selectedSubPositionSecond = it.sub
                        selectedSubPositionIdSecond = it.id
                    }
                },
                onReset = {
                    if(userProfileState.isMainPosition){
                        selectedCareerFirst = ""
                        selectedMainPositionFirst = ""
                        selectedSubPositionFirst = ""
                        selectedSubPositionIdFirst = 0
                    }else {
                        selectedCareerSecond = ""
                        selectedMainPositionSecond = ""
                        selectedSubPositionSecond = ""
                        selectedSubPositionIdSecond = 0
                    }
                },
                onAdd = { profileEditCareerViewModel.navigateScreen() }
            )
        }
    }
}

@Composable
private fun ProfileEditCareerScreen(
    selectedCareerFirst: String,
    selectedMainPositionFirst: String,
    selectedSubPositionFirst: String,
    selectedCareerSecond: String,
    selectedMainPositionSecond: String,
    selectedSubPositionSecond: String,
    userProfileState: ProfileEditCareerState,
    onNavigationClick: () -> Unit,
    onAction: (ProfileEditCareerAction) -> Unit,
    onResetSelectedFirst: () -> Unit,
    onResetSelectedSecond: () -> Unit,
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
                    selectedCareerFirst = selectedCareerFirst,
                    selectedMainPositionFirst = selectedMainPositionFirst,
                    selectedSubPositionFirst = selectedSubPositionFirst,
                    selectedCareerSecond = selectedCareerSecond,
                    selectedMainPositionSecond = selectedMainPositionSecond,
                    selectedSubPositionSecond = selectedSubPositionSecond,
                    onGoToSelectCareerAndPosition = {
                        isMain -> onAction(ProfileEditCareerAction.OnChangeMainOrSub(isMain = isMain))
                        onAction(ProfileEditCareerAction.OnChangePrevScreen(isShowPrevScreen = false))
                    },
                    onResetSelectedFirst = onResetSelectedFirst,
                    onResetSelectedSecond = onResetSelectedSecond
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