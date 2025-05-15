package com.party.presentation.screen.party_create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.bottomsheet.OneSelectBottomSheet
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.list.partyTypeList
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.common.utils.HeightSpacer
import com.party.common.utils.noRippleClickable
import com.party.presentation.component.HelpCard
import com.party.presentation.screen.party_create.component.PartyCreateCustomShape
import com.party.presentation.screen.party_create.component.PartyCreateDescriptionArea
import com.party.presentation.screen.party_create.component.PartyCreateInputField
import com.party.presentation.screen.party_create.component.PartyCreateScaffoldArea
import com.party.presentation.screen.party_create.component.PartyCreateSelectPositionArea
import com.party.presentation.screen.party_create.component.PartyCreateValidField
import com.party.presentation.screen.party_create.component.PartyImageUploadArea
import com.party.presentation.screen.party_create.viewmodel.PartyCreateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PartyCreateScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    partyCreateViewModel: PartyCreateViewModel = hiltViewModel(),
) {
    // 뒤로가기
    LaunchedEffect(key1 = Unit) {
        partyCreateViewModel.backState.collectLatest {
            navController.popBackStack()
        }
    }

    val partyCreateState by partyCreateViewModel.state.collectAsStateWithLifecycle()

    PartyCreateScreen(
        partyCreateState = partyCreateState,
        isShowCompleteDialog = partyCreateState.isCompleteShowDialog,
        snackBarHostState = snackBarHostState,
        onAction = { action ->
            partyCreateViewModel.onAction(action = action)
        },
        onClickMainPosition = {
            partyCreateViewModel.getSubPositionList(main = it)
        }
    )

    if (partyCreateState.isBackShowDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.2f))
                .noRippleClickable { partyCreateViewModel.dismissBackDialog() }
        ) {
            TwoButtonDialog(
                dialogTitle = "나가기",
                description = "입력한 내용들이 모두 초기화됩니다.\n나가시겠습니까?",
                cancelButtonText = "취소",
                confirmButtonText = "나가기",
                onCancel = { partyCreateViewModel.dismissBackDialog() },
                onConfirm = { partyCreateViewModel.navigationBack() }
            )
        }
    }

    if (partyCreateState.isCompleteShowDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.7f))
                .noRippleClickable { partyCreateViewModel.dismissCompleteDialog() }
        ) {
            TwoButtonDialog(
                dialogTitle = "생성 완료",
                description = "파티가 생성되었어요!\n파티원을 모집해 볼까요?",
                cancelButtonText = "닫기",
                confirmButtonText = "모집하기",
                onCancel = {
                    partyCreateViewModel.dismissCompleteDialog()
                    navController.popBackStack()
                },
                onConfirm = {
                    partyCreateViewModel.dismissCompleteDialog()
                    // 모집하기 이동
                    navController.popBackStack()
                    navController.navigate(Screens.RecruitmentCreate(partyId = partyCreateState.partyId))
                }
            )
        }
    }
}

@Composable
private fun PartyCreateScreen(
    partyCreateState: PartyCreateState,
    isShowCompleteDialog: Boolean,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onAction: (PartyCreateAction) -> Unit,
    onClickMainPosition: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .blur(
                radiusX = if (partyCreateState.isBackShowDialog || isShowCompleteDialog) 10.dp else 0.dp,
                radiusY = if (partyCreateState.isBackShowDialog || isShowCompleteDialog) 10.dp else 0.dp,
            ),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyCreateScaffoldArea(
                onNavigationClick = {
                    onAction(PartyCreateAction.OnIsShowDialogBack(true))
                }
            )
        }
    ) {
        val focusManager = LocalFocusManager.current

        Column(
            modifier = modifier
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
            HeightSpacer(heightDp = 12.dp)

            PartyImageUploadArea(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onSetImage = { multipartBody ->
                    onAction(PartyCreateAction.OnChangeImage(multipartBody))
                }
            )

            if (partyCreateState.isVisibleToolTip) {
                PartyCreateCustomShape(
                    onClick = { onAction(PartyCreateAction.OnIsVisibleToolTip(false)) }
                )
            }

            HeightSpacer(heightDp = 32.dp)
            PartyCreateDescriptionArea(
                title = "파티명",
                description = "직관적인 파티명을 사용하시면 조회수가 올라가요."
            )

            HeightSpacer(heightDp = 20.dp)
            PartyCreateInputField(
                inputText = partyCreateState.inputPartyTitle,
                placeHolder = "15자 이내로 입력해 주세요.",
                readOnly = false,
                icon = {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_close),
                        contentDescription = "",
                        onClick = { onAction(PartyCreateAction.OnRemovePartyTitle) }
                    )
                },
                onValueChange = { inputText ->
                    if (inputText.length <= 15) {
                        onAction(PartyCreateAction.OnChangeInputTitle(inputText))
                    }
                }
            )

            HeightSpacer(heightDp = 12.dp)
            PartyCreateValidField(
                count = partyCreateState.inputPartyTitle.length
            )

            HeightSpacer(heightDp = 30.dp)
            PartyCreateDescriptionArea(
                title = "파티 유형",
                description = "파티가 목표로 하는 유형을 선택해 주세요."
            )

            HeightSpacer(heightDp = 20.dp)
            PartyCreateInputField(
                inputText = partyCreateState.selectedPartyType,
                placeHolder = "유형을 선택해 주세요.",
                readOnly = true,
                icon = {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_arrow_down),
                        contentDescription = "",
                        onClick = { onAction(PartyCreateAction.OnChangePartyTypeSheet(true)) }
                    )
                },
                onValueChange = {}
            )

            HeightSpacer(heightDp = 80.dp)
            PartyCreateDescriptionArea(
                title = "파티 소개글",
                description = "파티의 방향성, 참고사항 등을 자유롭게 적어주세요.",
                icon = {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_help),
                        contentDescription = "",
                        onClick = { onAction(PartyCreateAction.OnChangeIsShowHelpCard(true)) },
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            if (partyCreateState.isHelpCardOpen) {
                HeightSpacer(heightDp = 12.dp)
                HelpCard(
                    description1 = "어떤 활동을 하나요?",
                    description2 = "규칙이 있나요? (출석, 강퇴 등)",
                    onClose = { onAction(PartyCreateAction.OnChangeIsShowHelpCard(false)) }
                )
            }

            HeightSpacer(heightDp = 20.dp)
            MultiLineInputField(
                placeHolder = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다!\n\n창의적인 아이디어와 열정이 가득한 분들과 함께하는 시간을 가지려고 합니다.\n함께 모여 스터디를 시작하거나 프로젝트를 만들어서 성장하는 즐거움을 함께 누려봐요!\n\n함께하고 싶으신분들은 언제든지 환영입니다!",
                inputText = partyCreateState.partyDescription,
                onValueChange = { inputDescription -> onAction(PartyCreateAction.OnChangePartyDescription(inputDescription)) },
                onAllDeleteInputText = { onAction(PartyCreateAction.OnChangePartyDescription("")) }
            )

            HeightSpacer(heightDp = 30.dp)
            PartyCreateDescriptionArea(
                title = "내 포지션",
                description = "파티 내에서 본인의 포지션을 선택해주세요."
            )

            HeightSpacer(heightDp = 20.dp)
            PartyCreateSelectPositionArea(
                subPositionList = partyCreateState.subPositionList,
                isMainPositionBottomSheetShow = partyCreateState.isMainPositionBottomSheetShow,
                selectedMainPosition = partyCreateState.selectedMainPosition,
                selectedSubPosition = partyCreateState.selectedSubPosition,
                onApply = { mainPosition, selectedSubPosition->
                    onAction(PartyCreateAction.OnChangeMainPosition(mainPosition))
                    onAction(PartyCreateAction.OnChangeSubPosition(selectedSubPosition))
                },
                onShowPositionBottomSheet = { isShow ->
                    onAction(PartyCreateAction.OnChangeMainPositionBottomSheet(isShow))
                },
                onClickMainPosition = onClickMainPosition
            )

            HeightSpacer(heightDp = 90.dp)

            ApplyButton(
                buttonText = "생성하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    onAction(PartyCreateAction.OnPartyCreate)
                }
            )
        }

        if (partyCreateState.isPartyTypeSheetOpen) {
            OneSelectBottomSheet(
                bottomSheetTitle = "파티 유형",
                contentList = partyTypeList,
                selectedText = partyCreateState.selectedPartyType,
                onBottomSheetClose = { onAction(PartyCreateAction.OnChangePartyTypeSheet(false)) },
                onApply = { selectPartyType ->
                    onAction(PartyCreateAction.OnChangeSelectPartyType(selectPartyType))
                    onAction(PartyCreateAction.OnChangePartyTypeSheet(false))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyCreateScreenPreview() {
    PartyCreateScreen(
        partyCreateState = PartyCreateState(),
        isShowCompleteDialog = false,
        snackBarHostState = SnackbarHostState(),
        onAction = {},
        onClickMainPosition = {}
    )
}