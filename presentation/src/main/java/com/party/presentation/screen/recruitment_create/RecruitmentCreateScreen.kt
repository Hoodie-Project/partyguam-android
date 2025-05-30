package com.party.presentation.screen.recruitment_create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.bottomsheet.OneSelectPickerBottomSheet
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.list.peopleCountList
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
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
        onRecruitmentPreview = {
            navController.navigate(
                Screens.RecruitmentCreatePreview(
                    partyId = partyId,
                    description = recruitmentState.recruitmentDescription,
                    recruitingCount = recruitmentState.selectedCount,
                    main = recruitmentState.selectedMainPosition,
                    sub = recruitmentState.selectedSubPosition.sub,
                )
            )
        },
        onAction = { action ->
            recruitmentCreateViewModel.onAction(action = action)
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
    onClickMainPosition: (String) -> Unit,
    onRecruitmentPreview: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            RecruitmentCreateScaffoldArea(
                onNavigationClick = onBackNavigation,
                onRecruitmentPreview = onRecruitmentPreview,
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
                    placeHolder = "안녕하세요 앱 개발자 팀원을 모집하고 있습니다\n현재 앱 개발은 Flutter를 사용하여 회원가입, 추가 회원 정보 기입, 로그인 기능까지 구현된 상태입니다.",
                    inputText = recruitmentState.recruitmentDescription,
                    onValueChange = { description ->
                        if (description.length <= 250) {
                            onAction(RecruitmentCreateAction.OnChangeRecruitmentDescription(description))
                        }
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
                    isActive = isActiveApplyButton(recruitmentState),
                    onClick = {
                        onAction(RecruitmentCreateAction.OnRecruitmentCreate(partyId = partyId))
                    }
                )
                HeightSpacer(heightDp = 20.dp)
            }
        }
    )
}

private fun isActiveApplyButton(recruitmentState: RecruitmentState): Boolean{
    return recruitmentState.selectedMainPosition.isNotEmpty() &&
            recruitmentState.selectedSubPosition.sub.isNotEmpty() &&
            recruitmentState.selectedCount > 0 &&
            recruitmentState.recruitmentDescription.isNotEmpty()
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentCreateScreenPreview() {
    RecruitmentCreateScreen(
        partyId = 0,
        recruitmentState = RecruitmentState(),
        onBackNavigation = {},
        onAction = {},
        onClickMainPosition = {},
        onRecruitmentPreview = {},
    )
}