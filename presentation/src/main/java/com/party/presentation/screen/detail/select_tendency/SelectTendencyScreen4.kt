package com.party.presentation.screen.detail.select_tendency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK400
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT300
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PersonalitySaveRequest2
import com.party.common.Screens
import com.party.common.component.dialog.TwoButtonDialog
import com.party.presentation.screen.detail.ProfileIndicatorArea
import com.party.presentation.screen.detail.select_tendency.SavePersonalityData.personalitySaveRequest1
import com.party.presentation.screen.detail.select_tendency.SavePersonalityData.personalitySaveRequest2
import com.party.presentation.screen.detail.select_tendency.SavePersonalityData.personalitySaveRequest3
import com.party.presentation.screen.detail.select_tendency.SavePersonalityData.personalitySaveRequest4
import com.party.presentation.screen.detail.select_tendency.component.SelectTendencyScaffoldArea
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SelectTendencyScreen4(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    selectTendencyViewModel: SelectTendencyViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        selectTendencyViewModel.saveSuccess.collectLatest {
            navController.navigate(Screens.SelectTendencyComplete)
        }
    }

    LaunchedEffect(key1 = Unit) {
        selectTendencyViewModel.saveConflict.collectLatest {
            snackBarMessage(snackBarHostState, it)
        }
    }

    LaunchedEffect(Unit) {
        selectTendencyViewModel.getPersonalityList()
    }

    var isShowDialog by remember {
        mutableStateOf(false)
    }

    // Get Personality
    val personalityListState by selectTendencyViewModel.personalityState.collectAsStateWithLifecycle()
    val personalityListResult = personalityListState.data

    // Save Personality
    val personalitySaveState by selectTendencyViewModel.personalitySaveState.collectAsStateWithLifecycle()
    val personalitySaveResult = personalitySaveState.data

    /*val selectedTendencyList by remember {
        mutableStateOf(mutableStateListOf<PersonalityListOption>())
    }*/

    val selectedTendencyList by remember {
        mutableStateOf(mutableStateListOf<PersonalityListOption>())
    }

    val isValid by remember {
        mutableStateOf(false)
    }.apply { value = selectedTendencyList.size in 1..2 }


    when(personalitySaveState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {}
        is UIState.Error -> {}
        is UIState.Exception -> {}
    }

    Scaffold(
        topBar = {
            SelectTendencyScaffoldArea(
                onNavigationClick = { navController.popBackStack() },
                onClose = {
                    isShowDialog = true
                }
            )
        }
    ) {
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
                ProfileIndicatorArea(
                    container1 = PRIMARY,
                    container2 = PRIMARY,
                    container3 = PRIMARY,
                    textColor1 = BLACK,
                    textColor2 = BLACK,
                    textColor3 = BLACK,
                    indicatorText = stringResource(id = R.string.detail_profile6),
                )

                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.select_tendency7),
                    subExplain = stringResource(id = R.string.select_tendency6),
                )

                HeightSpacer(heightDp = 40.dp)

                when(personalityListState){
                    is UIState.Idle -> {}
                    is UIState.Loading -> { LoadingProgressBar() }
                    is UIState.Success -> {
                        val successResult = personalityListResult as SuccessResponse
                        val successResult2 = successResult.data?.filter { it.id == 4 }
                        SelectTendencyArea4(
                            selectedTendencyList = selectedTendencyList,
                            getPersonalityList = successResult2?.get(0)?.personalityOptions ?: emptyList(),
                            onSelect = {
                                if(selectedTendencyList.contains(it)) {
                                    selectedTendencyList.remove(it)
                                } else if(selectedTendencyList.size == 2){
                                    snackBarMessage(snackBarHostState, "최대 2개까지 선택할 수 있어요.")
                                }else {
                                    selectedTendencyList.add(it)
                                }
                            }

                        )
                    }
                    is UIState.Error -> {}
                    is UIState.Exception -> {}
                }
            }

            TendencyBottomArea(
                //navController = navController,
                //routeScreens = Screens.SelectTendencyComplete,
                buttonText = stringResource(id = R.string.common2),
                textColor = if(isValid) BLACK else GRAY400,
                borderColor = if(isValid) PRIMARY else LIGHT200,
                containerColor = if(isValid) PRIMARY else LIGHT400,
                onClick = {
                    if(isValid){
                        personalitySaveRequest4 = PersonalitySaveRequest2(
                            personalityQuestionId = selectedTendencyList[0].personalityQuestionId,
                            personalityOptionId = selectedTendencyList.map { it.id }
                        )

                        val personalitySaveRequest = PersonalitySaveRequest(
                            personality = listOf(
                                personalitySaveRequest1,
                                personalitySaveRequest2,
                                personalitySaveRequest3,
                                personalitySaveRequest4
                            )
                        )
                        selectTendencyViewModel.savePersonality(
                            personalitySaveRequest = personalitySaveRequest
                        )
                    }
                },
                onSkip = { navController.navigate(Screens.SelectTendencyComplete) }
            )
        }
    }

    if(isShowDialog){
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
                onCancel = { isShowDialog = false },
                onConfirm = {
                    navController.navigate(Screens.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true } // 모든 백 스택 제거
                        launchSingleTop = true // 중복 방지
                    }
                }

            )
        }
    }
}

@Composable
fun SelectTendencyArea4(
    selectedTendencyList: MutableList<PersonalityListOption>,
    getPersonalityList: List<PersonalityListOption>,
    onSelect: (PersonalityListOption) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = getPersonalityList,
            key =  { index, _ ->
                index
            }
        ){_, item ->
            SelectTendencyAreaComponent(
                containerColor = if(selectedTendencyList.contains(item)) LIGHT300 else WHITE,
                item = item,
                fontWeight = if(selectedTendencyList.contains(item)) FontWeight.SemiBold else FontWeight.Normal,
                iconColor = if(selectedTendencyList.contains(item)) PRIMARY else GRAY200,
                onSelect = { onSelect(it) },
                textColor = if(selectedTendencyList.contains(item)) DARK400 else BLACK,
            )
        }
    }
}