package com.party.presentation.screen.recruitment_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.bottomsheet.OneSelectPickerBottomSheet
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.list.peopleCountList
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.component.HelpCard
import com.party.presentation.component.SelectMainAndSubPositionArea
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateDescriptionArea
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateInputField
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateScaffoldArea
import com.party.presentation.screen.recruitment_create.viewmodel.RecruitmentCreateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecruitmentCreateScreenRoute(
    navController: NavHostController,
    recruitmentCreateViewModel: RecruitmentCreateViewModel = hiltViewModel(),
    partyId: Int,
) {
    val recruitmentState by recruitmentCreateViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
       recruitmentCreateViewModel.successSaveState.collectLatest { navController.popBackStack() }
    }

    RecruitmentCreateScreen(
        partyId = partyId,
        recruitmentState = recruitmentState,
        onBackNavigation = { navController.popBackStack() },
        onAction = { action ->
            when (action) {
                is RecruitmentCreateAction.OnChangeMainPositionBottomSheet -> recruitmentCreateViewModel.onAction(action)
                is RecruitmentCreateAction.OnChangeMainPosition -> recruitmentCreateViewModel.onAction(action)
                is RecruitmentCreateAction.OnChangeSubPosition -> recruitmentCreateViewModel.onAction(action)
                is RecruitmentCreateAction.OnSetSelectedCount -> recruitmentCreateViewModel.onAction(action)
                is RecruitmentCreateAction.OnChangePeopleCountSheet -> recruitmentCreateViewModel.onAction(action)
                is RecruitmentCreateAction.OnChangeHelpCardOpen -> recruitmentCreateViewModel.onAction(action)
                is RecruitmentCreateAction.OnChangeRecruitmentDescription -> recruitmentCreateViewModel.onAction(action)
                is RecruitmentCreateAction.OnRecruitmentCreate -> recruitmentCreateViewModel.onAction(action)
            }
        },
        onClickMainPosition = {
            recruitmentCreateViewModel.getSubPositionList(it)
        }
    )
}

@Composable
fun RecruitmentCreateScreen(
    partyId: Int,
    recruitmentState: RecruitmentState,
    onBackNavigation: () -> Unit,
    onAction: (RecruitmentCreateAction) -> Unit,
    onClickMainPosition: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            RecruitmentCreateScaffoldArea(
                onNavigationClick = onBackNavigation
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WHITE)
                    .padding(it)
                    .padding(horizontal = MEDIUM_PADDING_SIZE)
                    .verticalScroll(scrollState)
            ) {
                HeightSpacer(heightDp = 40.dp)
                RecruitmentCreateDescriptionArea(
                    title = "모집 포지션",
                    description = "모집하시는 포지션을 입력해 주세요."
                )
                HeightSpacer(heightDp = 20.dp)
                SelectMainAndSubPositionArea(
                    subPositionList = recruitmentState.subPositionList,
                    isMainPositionBottomSheetShow = recruitmentState.isMainPositionBottomSheetShow,
                    selectedMainPosition = recruitmentState.selectedMainPosition,
                    selectedSubPosition = recruitmentState.selectedSubPosition,
                    onApply = { mainPosition, selectedSubPosition->
                        onAction(RecruitmentCreateAction.OnChangeMainPosition(mainPosition))
                        onAction(RecruitmentCreateAction.OnChangeSubPosition(selectedSubPosition))
                    },
                    onShowPositionBottomSheet = { isMainPositionBottomSheetShow ->
                        onAction(RecruitmentCreateAction.OnChangeMainPositionBottomSheet(isMainPositionBottomSheetShow))
                    },
                    onClickMainPosition = onClickMainPosition
                )

                HeightSpacer(heightDp = 60.dp)
                RecruitmentCreateDescriptionArea(
                    title = "모집 인원",
                    description = "모집하시는 인원을 선택해 주세요."
                )
                HeightSpacer(heightDp = 20.dp)
                RecruitmentCreateInputField(
                    inputText = "${recruitmentState.selectedCount}명" ,
                    placeHolder = "인원을 선택해 주세요.",
                    readOnly = true,
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.icon_arrow_down),
                            contentDescription = "",
                            onClick = {
                                onAction(RecruitmentCreateAction.OnChangePeopleCountSheet(true))
                            }
                        )
                    },
                    onValueChange = {}
                )
                if(recruitmentState.isPeopleCountSheetOpen){
                    OneSelectPickerBottomSheet(
                        bottomSheetTitle = "모집 인원",
                        selectedText = "${recruitmentState.selectedCount}명",
                        peopleCountList = peopleCountList,
                        onBottomSheetClose = { onAction(RecruitmentCreateAction.OnChangePeopleCountSheet(false)) },
                        onApply = { selectedData ->
                            onAction(RecruitmentCreateAction.OnSetSelectedCount(selectedData.split("명")[0]))
                            onAction(RecruitmentCreateAction.OnChangePeopleCountSheet(false))
                        }
                    )
                }

                HeightSpacer(heightDp = 60.dp)
                RecruitmentCreateDescriptionArea(
                    title = "모집 소개글",
                    description = "파티의 방향성, 참고사항 등을 자유롭게 적어 주세요.",
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.icon_help),
                            contentDescription = "",
                            onClick = { onAction(RecruitmentCreateAction.OnChangeHelpCardOpen(true)) },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
                if (recruitmentState.isHelpCardOpen) {
                    HeightSpacer(heightDp = 12.dp)
                    HelpCard(
                        description1 = "현재 파티의 진행상태는 어떤가요?",
                        description2 = "어떤 사람을 구인하시나요? (툴, 포르폴리오 등)",
                        onClose = { onAction(RecruitmentCreateAction.OnChangeHelpCardOpen(false)) }
                    )
                }
                HeightSpacer(heightDp = 20.dp)
                MultiLineInputField(
                    placeHolder = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다.",
                    inputText = recruitmentState.recruitmentDescription,
                    onValueChange = { description ->
                        onAction(RecruitmentCreateAction.OnChangeRecruitmentDescription(description))
                    },
                    onAllDeleteInputText = {
                        onAction(RecruitmentCreateAction.OnChangeRecruitmentDescription(""))
                    }
                )

                HeightSpacer(heightDp = 60.dp)
                ApplyButton(
                    buttonText = "모집하기",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {
                        onAction(RecruitmentCreateAction.OnRecruitmentCreate(partyId = partyId))
                    }
                )
                HeightSpacer(heightDp = 20.dp)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentCreateScreenPreview() {
    RecruitmentCreateScreen(
        partyId = 0,
        recruitmentState = RecruitmentState(),
        onBackNavigation = {},
        onAction = {},
        onClickMainPosition = {}
    )
}