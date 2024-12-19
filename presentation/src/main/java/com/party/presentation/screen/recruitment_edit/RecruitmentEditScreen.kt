package com.party.presentation.screen.recruitment_edit

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
import androidx.compose.material3.SnackbarHostState
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
import com.party.common.snackBarMessage
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.component.HelpCard
import com.party.presentation.component.SelectMainAndSubPositionArea
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateInputField
import com.party.presentation.screen.recruitment_edit.component.RecruitmentEditDescriptionArea
import com.party.presentation.screen.recruitment_edit.component.RecruitmentEditScaffoldArea
import com.party.presentation.screen.recruitment_edit.viewmodel.RecruitmentEditViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecruitmentEditRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    partyRecruitmentId: Int,
    partyId: Int,
    recruitmentEditViewModel: RecruitmentEditViewModel = hiltViewModel()
) {
    val recruitmentEditState by recruitmentEditViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        recruitmentEditViewModel.getRecruitmentDetail(partyRecruitmentId)
    }

    LaunchedEffect(key1 = Unit) {
        recruitmentEditViewModel.successModify.collectLatest {
            navController.popBackStack()
        }
    }

    RecruitmentEditScreen(
        partyId = partyId,
        partyRecruitmentId = partyRecruitmentId,
        recruitmentEditState = recruitmentEditState,
        onBackNavigation = { navController.popBackStack() },
        onClickMainPosition = { recruitmentEditViewModel.getSubPositionList(it) },
        onAction = { action ->
            when (action) {
                is RecruitmentEditAction.OnChangeMainPositionBottomSheet -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnChangeMainPosition -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnChangeSubPosition -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnSetSelectedCount -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnChangePeopleCountSheet -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnChangeHelpCardOpen -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnChangeRecruitmentDescription -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnModifyRecruitment -> recruitmentEditViewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun RecruitmentEditScreen(
    partyId: Int,
    partyRecruitmentId: Int,
    recruitmentEditState: RecruitmentEditState,
    onBackNavigation: () -> Unit,
    onClickMainPosition: (String) -> Unit,
    onAction: (RecruitmentEditAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            RecruitmentEditScaffoldArea(
                onNavigationClick = onBackNavigation,
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
                // 모집 포지션
                HeightSpacer(heightDp = 40.dp)
                RecruitmentEditDescriptionArea(
                    title = "모집 포지션",
                    description = "모집하시는 포지션을 입력해 주세요."
                )
                HeightSpacer(heightDp = 20.dp)
                SelectMainAndSubPositionArea(
                    subPositionList = recruitmentEditState.subPositionList,
                    isMainPositionBottomSheetShow = recruitmentEditState.isMainPositionBottomSheetShow,
                    selectedMainPosition = recruitmentEditState.selectedMainPosition,
                    selectedSubPosition = recruitmentEditState.selectedSubPosition,
                    onApply = { mainPosition, selectedSubPosition->
                        onAction(RecruitmentEditAction.OnChangeMainPosition(mainPosition))
                        onAction(RecruitmentEditAction.OnChangeSubPosition(selectedSubPosition))
                    },
                    onShowPositionBottomSheet = { isMainPositionBottomSheetShow ->
                        onAction(RecruitmentEditAction.OnChangeMainPositionBottomSheet(isMainPositionBottomSheetShow))
                    },
                    onClickMainPosition = onClickMainPosition
                )

                // 모집 인원
                HeightSpacer(heightDp = 60.dp)
                RecruitmentEditDescriptionArea(
                    title = "모집 인원",
                    description = "모집하시는 인원을 선택해 주세요."
                )
                HeightSpacer(heightDp = 20.dp)
                RecruitmentCreateInputField(
                    inputText = "${recruitmentEditState.selectedCount}명" ,
                    placeHolder = "인원을 선택해 주세요.",
                    readOnly = true,
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.arrow_down_icon),
                            contentDescription = "",
                            onClick = {
                                onAction(RecruitmentEditAction.OnChangePeopleCountSheet(true))
                            }
                        )
                    },
                    onValueChange = {}
                )
                if(recruitmentEditState.isPeopleCountSheetOpen){
                    OneSelectPickerBottomSheet(
                        bottomSheetTitle = "모집 인원",
                        selectedText = "${recruitmentEditState.selectedCount}명",
                        peopleCountList = peopleCountList,
                        onBottomSheetClose = { onAction(RecruitmentEditAction.OnChangePeopleCountSheet(false)) },
                        onApply = { selectedData ->
                            onAction(RecruitmentEditAction.OnSetSelectedCount(selectedData.split("명")[0]))
                            onAction(RecruitmentEditAction.OnChangePeopleCountSheet(false))
                        }
                    )
                }

                // 모집 소개글
                HeightSpacer(heightDp = 60.dp)
                RecruitmentEditDescriptionArea(
                    title = "모집 소개글",
                    description = "파티의 방향성, 참고사항 등을 자유롭게 적어 주세요.",
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.help),
                            contentDescription = "",
                            onClick = { onAction(RecruitmentEditAction.OnChangeHelpCardOpen(true)) },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
                if (recruitmentEditState.isHelpCardOpen) {
                    HeightSpacer(heightDp = 12.dp)
                    HelpCard(
                        description1 = "현재 파티의 진행상태는 어떤가요?",
                        description2 = "어떤 사람을 구인하시나요? (툴, 포르폴리오 등)",
                        onClose = { onAction(RecruitmentEditAction.OnChangeHelpCardOpen(false)) }
                    )
                }
                HeightSpacer(heightDp = 20.dp)
                MultiLineInputField(
                    placeHolder = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다.",
                    inputText = recruitmentEditState.recruitmentDescription,
                    onValueChange = { description ->
                        onAction(RecruitmentEditAction.OnChangeRecruitmentDescription(description))
                    },
                    onAllDeleteInputText = {
                        onAction(RecruitmentEditAction.OnChangeRecruitmentDescription(""))
                    }
                )

                HeightSpacer(heightDp = 60.dp)
                ApplyButton(
                    buttonText = "수정 완료",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {
                        onAction(RecruitmentEditAction.OnModifyRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId))
                    }
                )
                HeightSpacer(heightDp = 20.dp)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentEditScreenPreview() {
    RecruitmentEditScreen(
        partyId = 0,
        partyRecruitmentId = 0,
        recruitmentEditState = RecruitmentEditState(),
        onBackNavigation = {},
        onAction = {},
        onClickMainPosition = {}
    )
}