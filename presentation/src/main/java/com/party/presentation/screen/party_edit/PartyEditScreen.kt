package com.party.presentation.screen.party_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.bottomsheet.OneSelectBottomSheet
import com.party.common.component.bottomsheet.list.partyTypeList
import com.party.common.component.button.CustomButton
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.component.HelpCard
import com.party.presentation.screen.party_create.PartyCreateAction
import com.party.presentation.screen.party_detail.component.RightModalDrawer
import com.party.presentation.screen.party_edit.component.PartyEditCustomShape
import com.party.presentation.screen.party_edit.component.PartyEditDescriptionArea
import com.party.presentation.screen.party_edit.component.PartyEditInputField
import com.party.presentation.screen.party_edit.component.PartyEditScaffoldArea
import com.party.presentation.screen.party_edit.component.PartyEditSelectPositionArea
import com.party.presentation.screen.party_edit.component.PartyEditValidField
import com.party.presentation.screen.party_edit.component.PartyImageArea
import com.party.presentation.screen.party_edit.component.SelectPartyStateButtonArea
import com.party.presentation.screen.party_edit.viewmodel.PartyEditViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun PartyEditScreenRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    partyId: Int,
    partyEditViewModel: PartyEditViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        partyEditViewModel.getPartyDetail(partyId = partyId)
    }
    val partyEditState by partyEditViewModel.state.collectAsStateWithLifecycle()

    // 파티 삭제 완료되면 뒤로가기
    LaunchedEffect(key1 = Unit) {
        partyEditViewModel.partyDeleteSuccess.collectLatest {
            navController.popBackStack()
            navController.popBackStack()
        }
    }

    // 파티 수정 완료되면 Snackbar Message
    LaunchedEffect(key1 = Unit) {
        partyEditViewModel.partyModifySuccess.collectLatest {
            snackBarHostState.showSnackbar("파티 정보가 수정되었습니다.")
        }
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    RightModalDrawer(
        currentTitle = "파티 수정",
        drawerState = drawerState,
        content = {
            PartyEditScreen(
                snackBarHostState = snackBarHostState,
                partyEditState = partyEditState,
                partyId = partyId,
                onAction = { action ->
                    when(action){
                        is PartyEditAction.OnChangeImage -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnIsVisibleToolTip -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnRemovePartyTitle -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangeInputTitle -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangeSelectPartyType -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangePartyTypeSheet -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangeIsShowHelpCard -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangePartyDescription -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangeMainPosition -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangeSubPosition -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangeMainPositionBottomSheet -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnPartyModify -> partyEditViewModel.onAction(action)
                        is PartyEditAction.OnChangeShowPartyDeleteDialog -> partyEditViewModel.onAction(action)
                    }
                },
                onClickMainPosition = {},
                onNavigationClick = { navController.popBackStack() },
                onManageClick = { scope.launch { drawerState.open() } }
            )
        },
        onGotoPartyEdit = {
            scope.launch { drawerState.close() }
            navController.navigate(Screens.PartyEdit(partyId = partyId))
        },
        onGotoPartyUser = {
            scope.launch { drawerState.close() }
            navController.popBackStack()
            navController.navigate(Screens.PartyUserManage(partyId = partyId))
        },
        onGotoPartyRecruitmentEdit = {
            scope.launch { drawerState.close() }
            navController.popBackStack()
            navController.navigate(Screens.PartyEditRecruitment(partyId = partyId))
        },
        onGotoManageApplicant = {
            scope.launch { drawerState.close() }
            navController.popBackStack()
            navController.navigate(Screens.ManageApplicant(partyId = partyId))
        }
    )



    if(partyEditState.isShowPartyDeleteDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.3f))
                .noRippleClickable { partyEditViewModel.dismissDeleteDialog() }
        ) {
            TwoButtonDialog(
                dialogTitle = "파티 삭제",
                description = "한 번 삭제한 파티는 복구할 수 없어요.\n정말로 이 파티를 삭제하시나요?",
                cancelButtonText = "닫기",
                confirmButtonText = "삭제하기",
                onCancel = {
                    partyEditViewModel.dismissDeleteDialog()
                },
                onConfirm = {
                    partyEditViewModel.dismissDeleteDialog()
                    // 삭제하기
                    partyEditViewModel.deleteParty(partyId = partyId)
                }
            )
        }
    }
}

@Composable
private fun PartyEditScreen(
    snackBarHostState: SnackbarHostState,
    partyEditState: PartyEditState,
    partyId: Int,
    onAction: (PartyEditAction) -> Unit,
    onClickMainPosition: (String) -> Unit,
    onNavigationClick: () -> Unit,
    onManageClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .blur(
                radiusX = if (partyEditState.isShowPartyDeleteDialog) 10.dp else 0.dp,
                radiusY = if (partyEditState.isShowPartyDeleteDialog) 10.dp else 0.dp,
            ),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyEditScaffoldArea(
                onNavigationClick = onNavigationClick,
                onManageClick = onManageClick
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
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                HeightSpacer(heightDp = 12.dp)

                PartyImageArea(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onSetImage = { image -> onAction(PartyEditAction.OnChangeImage(image = image)) }
                )

                // ToolTip
                if(partyEditState.isVisibleToolTip){
                    PartyEditCustomShape(
                        onClick = { onAction(PartyEditAction.OnIsVisibleToolTip(false)) }
                    )
                }

                // 파티명
                HeightSpacer(heightDp = 32.dp)
                PartyEditDescriptionArea(
                    title = "파티명",
                    description = "직관적인 파티명을 사용하시면 조회수가 올라가요."
                )
                HeightSpacer(heightDp = 20.dp)
                PartyEditInputField(
                    inputText = partyEditState.inputPartyTitle,
                    placeHolder = "15자 이내로 입력해 주세요.",
                    readOnly = false,
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.close),
                            contentDescription = "",
                            onClick = { onAction(PartyEditAction.OnRemovePartyTitle) }
                        )
                    },
                    onValueChange = { inputText ->
                        if (inputText.length <= 15) {
                            onAction(PartyEditAction.OnChangeInputTitle(inputText))
                        }
                    }
                )
                HeightSpacer(heightDp = 12.dp)
                PartyEditValidField(
                    count = partyEditState.inputPartyTitle.length
                )

                // 파티 유형
                HeightSpacer(heightDp = 30.dp)
                PartyEditDescriptionArea(
                    title = "파티 유형",
                    description = "파티가 목표로 하는 유형을 선택해 주세요."
                )
                HeightSpacer(heightDp = 20.dp)
                PartyEditInputField(
                    inputText = partyEditState.selectedPartyType,
                    placeHolder = "유형을 선택해 주세요.",
                    readOnly = true,
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.arrow_down_icon),
                            contentDescription = "",
                            onClick = { onAction(PartyEditAction.OnChangePartyTypeSheet(true)) }
                        )
                    },
                    onValueChange = {}
                )

                if(partyEditState.isPartyTypeSheetOpen){
                    OneSelectBottomSheet(
                        bottomSheetTitle = "파티 유형",
                        contentList = partyTypeList,
                        selectedText = partyEditState.selectedPartyType,
                        onBottomSheetClose = { onAction(PartyEditAction.OnChangePartyTypeSheet(false)) },
                        onApply = { selectPartyType ->
                            onAction(PartyEditAction.OnChangeSelectPartyType(selectPartyType))
                            onAction(PartyEditAction.OnChangePartyTypeSheet(false))
                        }
                    )
                }

                // 파티 소개글
                HeightSpacer(heightDp = 80.dp)
                PartyEditDescriptionArea(
                    title = "파티 소개글",
                    description = "파티의 방향성, 참고사항 등을 자유롭게 적어주세요.",
                    icon = {
                        DrawableIconButton(
                            icon = painterResource(id = R.drawable.help),
                            contentDescription = "",
                            onClick = { onAction(PartyEditAction.OnChangeIsShowHelpCard(true)) },
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
                if(partyEditState.isHelpCardOpen){
                    HeightSpacer(heightDp = 12.dp)
                    HelpCard(
                        description1 = "어떤 활동을 하나요?",
                        description2 = "규칙이 있나요? (출석, 강퇴 등)",
                        onClose = { onAction(PartyEditAction.OnChangeIsShowHelpCard(false)) }
                    )
                }
                HeightSpacer(heightDp = 20.dp)
                MultiLineInputField(
                    placeHolder = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다.",
                    inputText = partyEditState.partyDescription,
                    onValueChange = { inputDescription -> onAction(PartyEditAction.OnChangePartyDescription(inputDescription)) },
                    onAllDeleteInputText = { onAction(PartyEditAction.OnChangePartyDescription("")) }
                )

                // 내 포지션
                HeightSpacer(heightDp = 30.dp)
                PartyEditDescriptionArea(
                    title = "내 포지션",
                    description = "파티 내에서 본인의 포지션을 선택해주세요."
                )

                HeightSpacer(heightDp = 20.dp)
                PartyEditSelectPositionArea(
                    subPositionList = partyEditState.subPositionList,
                    isMainPositionBottomSheetShow = partyEditState.isMainPositionBottomSheetShow,
                    selectedMainPosition = partyEditState.selectedMainPosition,
                    selectedSubPosition = partyEditState.selectedSubPosition,
                    onApply = { mainPosition, selectedSubPosition->
                        onAction(PartyEditAction.OnChangeMainPosition(mainPosition))
                        onAction(PartyEditAction.OnChangeSubPosition(selectedSubPosition))
                    },
                    onShowPositionBottomSheet = { isShow ->
                        onAction(PartyEditAction.OnChangeMainPositionBottomSheet(isShow))
                    },
                    onClickMainPosition = onClickMainPosition
                )

                // 파티 상태
                HeightSpacer(heightDp = 60.dp)
                PartyEditDescriptionArea(
                    title = "파티 상태",
                    description = "파티 종료 시 일부 기능이 제한되며, 이후 다시 진행 중으로 변경할 수 있어요."
                )
                HeightSpacer(heightDp = 20.dp)
                SelectPartyStateButtonArea(
                    onProgress = {},
                    onFinish = {}
                )

                // 파티 삭제하기
                HeightSpacer(heightDp = 48.dp)
                TextComponent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "파티 삭제하기",
                    fontSize = B2,
                    textColor = RED,
                    textDecoration = TextDecoration.Underline,
                    textAlign = Alignment.Center,
                    onClick = { onAction(PartyEditAction.OnChangeShowPartyDeleteDialog(true)) }
                )

                HeightSpacer(heightDp = 48.dp)
            }

            // 수정 버튼
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { onAction(PartyEditAction.OnPartyModify(partyId = partyId)) },
                textWeight = FontWeight.Bold,
                textSize = B2,
                buttonText = "수정 완료",
                containerColor = PRIMARY
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyEditScreenPreview() {
    PartyEditScreen(
        snackBarHostState = SnackbarHostState(),
        partyEditState = PartyEditState(),
        partyId = 0,
        onAction = {},
        onClickMainPosition = {},
        onNavigationClick = {},
        onManageClick = {}
    )
}