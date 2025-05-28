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
import androidx.compose.runtime.getValue
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
import com.party.common.utils.HeightSpacer
import com.party.common.utils.LoadingProgressBar
import com.party.common.R
import com.party.common.utils.ScreenExplainArea
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.guam.design.BLACK
import com.party.guam.design.DARK400
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.LIGHT200
import com.party.guam.design.LIGHT300
import com.party.guam.design.LIGHT400
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PersonalitySaveRequest2
import com.party.common.Screens
import com.party.common.component.dialog.TwoButtonDialog
import com.party.presentation.screen.detail.ProfileIndicatorArea
import com.party.presentation.screen.detail.select_tendency.SavePersonalityData.personalitySaveRequest2
import com.party.presentation.screen.detail.select_tendency.component.SelectTendencyScaffoldArea

@Composable
fun SelectTendencyScreen2(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    selectTendencyViewModel: SelectTendencyViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        selectTendencyViewModel.getAccessToken().join()
    }

    val accessToken by selectTendencyViewModel.accessToken.collectAsStateWithLifecycle()

    var isShowDialog by remember {
        mutableStateOf(false)
    }

    if(accessToken.isNotEmpty()){
        LaunchedEffect(Unit) {
            selectTendencyViewModel.getPersonalityList()
        }
    }

    val personalityListState by selectTendencyViewModel.personalityState.collectAsStateWithLifecycle()
    val personalityListResult = personalityListState.data

    /*val selectedTendencyList by remember {
        mutableStateOf(mutableStateListOf<PersonalityListOption>())
    }*/

    var selectedTendencyList by remember {
        mutableStateOf(PersonalityListOption(
            id = 0,
            personalityQuestionId = 0,
            content = ""
        ))
    }

    val isValid by remember {
        mutableStateOf(false)
    }.apply { value = selectedTendencyList.id != 0 }

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
                    indicatorText = stringResource(id = R.string.detail_profile4),
                )

                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.select_tendency3),
                    subExplain = stringResource(id = R.string.select_tendency4),
                )
                
                HeightSpacer(heightDp = 40.dp)

                when(personalityListState){
                    is UIState.Idle -> {}
                    is UIState.Loading -> { LoadingProgressBar() }
                    is UIState.Success -> {
                        val successResult = personalityListResult as SuccessResponse
                        val successResult2 = successResult.data?.filter { it.id == 2 }
                        SelectTendencyArea2(
                            selectedTendencyList = selectedTendencyList,
                            getPersonalityList = successResult2?.get(0)?.personalityOptions ?: emptyList(),
                            onSelect = {
                                selectedTendencyList = it
                            }
                        )
                    }
                    is UIState.Error -> {}
                    is UIState.Exception -> {}
                }
            }

            TendencyBottomArea(
                //navController = navController,
                //routeScreens = Screens.SelectTendency3,
                buttonText = stringResource(id = R.string.common1),
                textColor = if(isValid) BLACK else GRAY400,
                borderColor = if(isValid) PRIMARY else LIGHT200,
                containerColor = if(isValid) PRIMARY else LIGHT400,
                onClick = {
                    if(isValid){
                        personalitySaveRequest2 = PersonalitySaveRequest2(
                            personalityQuestionId = selectedTendencyList.personalityQuestionId,
                            personalityOptionId = listOf(selectedTendencyList.id)
                        )
                        navController.navigate(Screens.SelectTendency3)
                    }
                },
                onSkip = { navController.navigate(Screens.SelectTendency3) }
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
fun SelectTendencyArea2(
    selectedTendencyList: PersonalityListOption,
    getPersonalityList: List<PersonalityListOption>,
    onSelect: (PersonalityListOption) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = getPersonalityList,
            key =  { index, _ ->
                index
            }
        ) { _, item ->
            SelectTendencyAreaComponent(
                containerColor = if(selectedTendencyList == item) LIGHT300 else WHITE,
                item = item,
                fontWeight = if(selectedTendencyList == item) FontWeight.SemiBold else FontWeight.Normal,
                iconColor = if(selectedTendencyList == item) PRIMARY else GRAY200,
                onSelect = { onSelect(it) },
                textColor = if(selectedTendencyList == item) DARK400 else BLACK,
            )
        }
    }
}