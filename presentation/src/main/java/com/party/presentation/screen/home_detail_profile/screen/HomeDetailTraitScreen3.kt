package com.party.presentation.screen.home_detail_profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ProfileIndicatorArea
import com.party.common.utils.ScreenExplainArea
import com.party.common.utils.StepInfo
import com.party.common.utils.StepStatus
import com.party.common.utils.TextComponent
import com.party.common.utils.snackBarMessage
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.DARK400
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LIGHT300
import com.party.guam.design.LIGHT400
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.DetailProfileNextButton
import com.party.presentation.screen.detail.ProfileIndicatorArea
import com.party.presentation.screen.detail.select_tendency.component.SelectTendencyScaffoldArea
import com.party.presentation.screen.home_detail_profile.action.HomeDetailProfileAction
import com.party.presentation.screen.home_detail_profile.component.TraitCard
import com.party.presentation.screen.home_detail_profile.state.HomeDetailProfileState
import com.party.presentation.screen.home_detail_profile.viewmodel.HomeDetailProfileViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeDetailTraitRoute3(
    viewModel: HomeDetailProfileViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.traitLimitExceeded.collectLatest {
            snackBarMessage(snackBarHostState, it )
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

    HomeDetailTraitScreen3(
        snackBarHostState = snackBarHostState,
        onNavigationClick = { navController.popBackStack() },
        state = state,
        onAction = { action -> viewModel.onAction(action = action)},
        onGotoNext = { navController.navigate(route = Screens.Trait4)},
        onClose = { viewModel.onAction(action = HomeDetailProfileAction.OnShowFinishDialog(isShow = true))},
        onSkip = { navController.navigate(route = Screens.Trait4)}
    )
}

@Composable
private fun HomeDetailTraitScreen3(
    snackBarHostState: SnackbarHostState,
    onNavigationClick: () -> Unit = {},
    state: HomeDetailProfileState,
    onAction: (HomeDetailProfileAction) -> Unit,
    onGotoNext: () -> Unit = {},
    onClose: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            SelectTendencyScaffoldArea(
                onNavigationClick = onNavigationClick,
                onClose = onClose
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
                val steps = listOf(
                    StepInfo("1", "관심지역", StepStatus.COMPLETED),
                    StepInfo("2", "경력/포지션", StepStatus.COMPLETED),
                    StepInfo("3", "성향선택(3/4)", StepStatus.CURRENT)
                )

                ProfileIndicatorArea(steps = steps)

                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.select_tendency5),
                    subExplain = stringResource(id = R.string.select_tendency6),
                )

                HeightSpacer(heightDp = 40.dp)

                Trait3Section(
                    modifier = Modifier
                        .weight(1f)
                    ,
                    selectedTraitList3 = state.selectedTraitList3,
                    traitList = state.personalityList.filter { it.id == 3 },
                    onSelect = { onAction(HomeDetailProfileAction.OnSelectTrait3(it))}
                )
            }

            DetailProfileNextButton(
                text = stringResource(id = R.string.common1),
                textColor = if(state.selectedTraitList3.isNotEmpty()) BLACK else GRAY400,
                containerColor = if(state.selectedTraitList3.isNotEmpty()) PRIMARY else LIGHT400,
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

@Composable
private fun Trait3Section(
    modifier: Modifier,
    selectedTraitList3: List<PersonalityListOption>,
    traitList: List<PersonalityList>,
    onSelect: (PersonalityListOption) -> Unit,
) {
    val trait1Options = traitList.find { it.id == 3 }?.personalityOptions ?: emptyList()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = trait1Options,
            key = { _, item -> item.id }
        ) { index, option ->
            val isSelected = selectedTraitList3.any { it.id == option.id }

            TraitCard(
                containerColor = if(isSelected) LIGHT300 else WHITE,
                item = option.content,
                fontWeight = if(isSelected) FontWeight.SemiBold else FontWeight.Normal,
                iconColor = if(isSelected) PRIMARY else GRAY200,
                onSelect = { onSelect(option) },
                textColor = if(isSelected) DARK400 else BLACK,
            )
        }
    }
}