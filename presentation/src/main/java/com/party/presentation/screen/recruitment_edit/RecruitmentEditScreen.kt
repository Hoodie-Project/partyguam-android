package com.party.presentation.screen.recruitment_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.Screens
import com.party.common.utils.WidthSpacer
import com.party.common.component.bottomsheet.OneSelectPickerBottomSheet
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.list.peopleCountList
import com.party.common.component.button.CustomButton
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.utils.noRippleClickable
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.LIGHT200
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.component.HelpCard
import com.party.presentation.component.SelectMainAndSubPositionArea
import com.party.presentation.screen.recruitment_create.component.RecruitmentCreateInputField
import com.party.presentation.screen.recruitment_edit.component.RecruitmentEditDescriptionArea
import com.party.presentation.screen.recruitment_edit.component.RecruitmentEditScaffoldArea
import com.party.presentation.screen.recruitment_edit.viewmodel.RecruitmentEditViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecruitmentEditRoute(
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
        recruitmentEditViewModel.completedSuccess.collectLatest {
            navController.popBackStack()
        }
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
        onPreview = {
            navController.navigate(
                Screens.RecruitmentPreview(
                    recruitmentId = partyRecruitmentId,
                    description = recruitmentEditState.recruitmentDescription,
                    recruitingCount = recruitmentEditState.selectedCount,
                    main = recruitmentEditState.selectedMainPosition,
                    sub = recruitmentEditState.selectedSubPosition.sub
                )
            )
        },
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
                is RecruitmentEditAction.OnPartyRecruitmentCompleted -> recruitmentEditViewModel.onAction(action)
                is RecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog -> recruitmentEditViewModel.onAction(action)
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
    onPreview: () -> Unit,
    onAction: (RecruitmentEditAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .blur(
                radiusX = if (recruitmentEditState.isShowPartyRecruitmentCompletedDialog ) 10.dp else 0.dp,
                radiusY = if (recruitmentEditState.isShowPartyRecruitmentCompletedDialog ) 10.dp else 0.dp,
            ),
        topBar = {
            RecruitmentEditScaffoldArea(
                onNavigationClick = onBackNavigation,
                onPreview = onPreview
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
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus()  // 필드 외부 터치 시 포커스 해제 → 키보드 내려감
                    }
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
                            icon = painterResource(id = R.drawable.icon_arrow_down),
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
                            icon = painterResource(id = R.drawable.icon_help),
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

                HeightSpacer(heightDp = 42.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val isValidButton = isActiveApplyButton(recruitmentEditState)

                    val borderColor = if(isValidButton) PRIMARY else LIGHT200
                    val containerColor = WHITE
                    val textColor = if(isValidButton) BLACK else GRAY400
                    CustomButton(
                        onClick = {
                            onAction(RecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(true))
                        },
                        buttonText = "마감하기",
                        contentColor = textColor,
                        textWeight = FontWeight.Bold,
                        containerColor = containerColor,
                        borderColor = borderColor,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                    )
                    WidthSpacer(widthDp = 8.dp)
                    ApplyButton(
                        buttonText = "수정 완료",
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        isActive = isValidButton,
                        onClick = {
                            onAction(RecruitmentEditAction.OnModifyRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId))
                        }
                    )
                }

                HeightSpacer(heightDp = 20.dp)
            }
        }
    )

    if(recruitmentEditState.isShowPartyRecruitmentCompletedDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.3f))
                .noRippleClickable {
                    onAction(RecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(false))
                }
        ) {
            TwoButtonDialog(
                dialogTitle = "모집공고 마감",
                description = "지원자에게 알림이 전송돼요.\n마감 후에는 수정할 수 없어요.\n정말로 모집공고를 마감하시나요?",
                cancelButtonText = "닫기",
                confirmButtonText = "마감하기",
                onCancel = { onAction(RecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(false)) },
                onConfirm = {
                    onAction(RecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(false))
                    onAction(RecruitmentEditAction.OnPartyRecruitmentCompleted(partyId = partyId, partyRecruitmentId = partyRecruitmentId))
                }
            )
        }
    }
}

private fun isActiveApplyButton(recruitmentEditState: RecruitmentEditState): Boolean{
    return recruitmentEditState.selectedMainPosition.isNotEmpty() &&
            recruitmentEditState.selectedSubPosition.sub.isNotEmpty() &&
            recruitmentEditState.selectedCount > 0 &&
            recruitmentEditState.recruitmentDescription.isNotEmpty()
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
        onClickMainPosition = {},
        onPreview = {}
    )
}