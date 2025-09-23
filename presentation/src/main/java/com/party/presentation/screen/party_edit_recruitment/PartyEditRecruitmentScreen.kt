package com.party.presentation.screen.party_edit_recruitment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.Screens
import com.party.common.component.bottomsheet.RecruitmentCompletedAndDeleteBottomSheet
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.partyRecruitmentEditTabList
import com.party.common.utils.noRippleClickable
import com.party.common.utils.snackBarMessage
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.RED
import com.party.guam.design.WHITE
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.SortType
import com.party.presentation.screen.party_detail.component.RightModalDrawer
import com.party.presentation.screen.party_edit_recruitment.component.PartyEditRecruitmentScaffoldArea
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentEditFilterArea
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentEditFloatingArea
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentEditHelpCard
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentEditTabBarArea
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentListArea
import com.party.presentation.screen.party_edit_recruitment.viewmodel.PartyRecruitmentEditViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun PartyEditRecruitmentScreenRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    partyId: Int,
    partyRecruitmentEditViewModel: PartyRecruitmentEditViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        partyRecruitmentEditViewModel.getPartyRecruitment(partyId = partyId, sort = SortType.CREATED_AT.type, order = OrderDescType.DESC.type, main = null)
    }

    LaunchedEffect(key1 = Unit) {
        partyRecruitmentEditViewModel.completedSuccess.collectLatest {
            snackBarMessage(snackBarHostState, "모집공고가 마감되었어요.")
            partyRecruitmentEditViewModel.getPartyRecruitment(partyId = partyId, sort = SortType.CREATED_AT.type, order = OrderDescType.DESC.type, main = null)
        }
    }

    LaunchedEffect(key1 = Unit) {
        partyRecruitmentEditViewModel.deleteRecruitment.collectLatest {
            snackBarMessage(snackBarHostState, "모집공고가 삭제되었어요.")
            partyRecruitmentEditViewModel.getPartyRecruitment(partyId = partyId, sort = SortType.CREATED_AT.type, order = OrderDescType.DESC.type, main = null)
        }
    }
    val partyRecruitmentEditState by partyRecruitmentEditViewModel.state.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    RightModalDrawer(
        currentTitle = "모집 편집",
        drawerState = drawerState,
        content = {
            PartyEditRecruitmentScreen(
                snackBarHostState = snackBarHostState,
                partyId = partyId,
                partyRecruitmentEditState = partyRecruitmentEditState,
                onNavigationClick = { navController.popBackStack() },
                onGotoRecruitmentCreate = { navController.navigate(Screens.RecruitmentCreate(partyId = partyId)) },
                onGotoRecruitmentPreview = {
                    navController.navigate(
                        Screens.RecruitmentPreview(
                            recruitmentId = partyRecruitmentEditState.selectedRecruitmentId,
                            description = partyRecruitmentEditState.description,
                            recruitingCount = partyRecruitmentEditState.recruitingCount,
                            main = partyRecruitmentEditState.main,
                            sub = partyRecruitmentEditState.sub,
                        )
                    )
                },
                onAction = { action ->
                    when(action){
                        is PartyRecruitmentEditAction.OnSelectedTabText -> partyRecruitmentEditViewModel.onAction(action)
                        is PartyRecruitmentEditAction.OnShowHelpCard -> partyRecruitmentEditViewModel.onAction(action)
                        is PartyRecruitmentEditAction.OnChangeOrderBy -> partyRecruitmentEditViewModel.onAction(action)
                        is PartyRecruitmentEditAction.OnDeleteRecruitment -> partyRecruitmentEditViewModel.deleteRecruitment(action.partyId, action.partyRecruitmentId)
                        is PartyRecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog -> partyRecruitmentEditViewModel.onAction(action)
                        is PartyRecruitmentEditAction.OnPartyRecruitmentCompleted -> partyRecruitmentEditViewModel.onAction(action)
                        is PartyRecruitmentEditAction.OnShowPartyRecruitmentDeleteDialog -> partyRecruitmentEditViewModel.onAction(action)
                        is PartyRecruitmentEditAction.OnShowBottomSheet -> partyRecruitmentEditViewModel.onAction(action)
                    }
                },
                onManageClick = { scope.launch { drawerState.open() } },
                onClick = { partyRecruitmentId ->
                    navController.navigate(Screens.RecruitmentEdit(partyId = partyId, partyRecruitmentId = partyRecruitmentId))
                }
            )
        },
        onGotoPartyEdit = {
            scope.launch {
                drawerState.close()
                navController.popBackStack()
                navController.navigate(Screens.PartyEdit(partyId = partyId))
            }
        },
        onGotoPartyUser = {
            scope.launch {
                drawerState.close()
                navController.popBackStack()
                navController.navigate(Screens.PartyUserManage(partyId = partyId))
            }
        },
        onGotoPartyRecruitmentEdit = {
            scope.launch { drawerState.close() }
        },
        onGotoManageApplicant = {
            scope.launch {
                drawerState.close()
                navController.popBackStack()
                navController.navigate(Screens.ManageApplicant(partyId = partyId))
            }
        }
    )
}

@Composable
private fun PartyEditRecruitmentScreen(
    snackBarHostState: SnackbarHostState,
    partyId: Int,
    partyRecruitmentEditState: PartyRecruitmentEditState,
    onNavigationClick: () -> Unit,
    onGotoRecruitmentCreate: () -> Unit,
    onGotoRecruitmentPreview: () -> Unit,
    onManageClick: () -> Unit,
    onClick: (Int) -> Unit,
    onAction: (PartyRecruitmentEditAction) -> Unit
) {
    var selectedRecruitmentId by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .blur(
                    radiusX = if (partyRecruitmentEditState.isShowPartyRecruitmentDeleteDialog || partyRecruitmentEditState.isShowPartyRecruitmentCompletedDialog) 10.dp else 0.dp,
                    radiusY = if (partyRecruitmentEditState.isShowPartyRecruitmentDeleteDialog || partyRecruitmentEditState.isShowPartyRecruitmentCompletedDialog) 10.dp else 0.dp,
                ),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarHostState,
                )
            },
            topBar = {
                PartyEditRecruitmentScaffoldArea(
                    onShowHelpCard = { onAction(PartyRecruitmentEditAction.OnShowHelpCard(it)) },
                    onNavigationClick = onNavigationClick,
                    onManageClick = onManageClick,
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
                PartyRecruitmentEditTabBarArea(
                    topTabList = partyRecruitmentEditTabList,
                    selectedTabText = partyRecruitmentEditState.selectedTabText,
                    onTabClick = { selectedTabText -> onAction(PartyRecruitmentEditAction.OnSelectedTabText(selectedTabText = selectedTabText, partyId = partyId)) }
                )

                // 참여일순 정렬
                PartyRecruitmentEditFilterArea(
                    partyRecruitmentEditState = partyRecruitmentEditState,
                    onChangeOrderBy = { isDesc -> onAction(PartyRecruitmentEditAction.OnChangeOrderBy(isDesc)) }
                )

                // 리스트
                HeightSpacer(12.dp)
                PartyRecruitmentListArea(
                    partyRecruitmentEditState = partyRecruitmentEditState,
                    onClick = onClick,
                    onMoreClick = { selectedRecruitmentId, status, description, recruitingCount, main, sub ->
                        onAction(PartyRecruitmentEditAction.OnShowBottomSheet(
                            isShow = !partyRecruitmentEditState.isShowBottomSheet,
                            recruitmentId = selectedRecruitmentId,
                            status = status,
                            description = description,
                            recruitingCount = recruitingCount,
                            main = main,
                            sub = sub
                        )
                        )
                    }
                )
            }
        }

        // 모집공고 선택 Description
        Box {
            if(partyRecruitmentEditState.isShowHelpCard){
                Column {
                    HeightSpacer(heightDp = 60.dp)
                    PartyRecruitmentEditHelpCard(
                        modifier = Modifier,
                        onCloseHelpCard = { onAction(PartyRecruitmentEditAction.OnShowHelpCard(false)) }
                    )
                }

            }
        }

        PartyRecruitmentEditFloatingArea(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 46.dp, end = 20.dp),
            onClick = onGotoRecruitmentCreate,
        )
    }

    if(partyRecruitmentEditState.isShowBottomSheet){
        RecruitmentCompletedAndDeleteBottomSheet(
            text = if(partyRecruitmentEditState.selectedStatus == "completed") "삭제하기" else "마감하기",
            textColor = if(partyRecruitmentEditState.selectedStatus == "completed") RED else BLACK,
            status = partyRecruitmentEditState.selectedStatus,
            onModelClose = {
                onAction(PartyRecruitmentEditAction.OnShowBottomSheet(isShow = false, recruitmentId = -1, status = "", description = "", recruitingCount = 0, main = "", sub = ""))
            },
            onPreview = {
                onAction(PartyRecruitmentEditAction.OnShowBottomSheet(isShow = false, recruitmentId = -1, status = "", description = "", recruitingCount = 0, main = "", sub = ""))
                onGotoRecruitmentPreview()
            },
            onPartyRecruitmentCompleted = {
                selectedRecruitmentId = partyRecruitmentEditState.selectedRecruitmentId
                onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(true))
                onAction(PartyRecruitmentEditAction.OnShowBottomSheet(isShow = false, recruitmentId = -1, status = "", description = "", recruitingCount = 0, main = "", sub = ""))
            },
            onPartyRecruitmentDeleted = {
                selectedRecruitmentId = partyRecruitmentEditState.selectedRecruitmentId
                onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentDeleteDialog(true))
                onAction(PartyRecruitmentEditAction.OnShowBottomSheet(isShow = false, recruitmentId = -1, status = "", description = "", recruitingCount = 0, main = "", sub = ""))
            },
        )
    }

    if(partyRecruitmentEditState.isShowPartyRecruitmentCompletedDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.3f))
                .noRippleClickable {
                    onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(false))
                }
        ) {
            TwoButtonDialog(
                dialogTitle = "모집공고 마감",
                description = "지원자에게 알림이 전송돼요.\n마감 후에는 수정할 수 없어요.\n정말로 모집공고를 마감하시나요?",
                cancelButtonText = "닫기",
                confirmButtonText = "마감하기",
                onCancel = { onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(false)) },
                onConfirm = {
                    onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog(false))
                    onAction(PartyRecruitmentEditAction.OnPartyRecruitmentCompleted(partyId = partyId, partyRecruitmentId = selectedRecruitmentId))
                }
            )
        }
    }

    if(partyRecruitmentEditState.isShowPartyRecruitmentDeleteDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.7f))
                .noRippleClickable {
                    onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentDeleteDialog(false))
                }
        ) {
            TwoButtonDialog(
                dialogTitle = "모집공고 삭제",
                description = "지원자에게 모집 완료 알림이 전송돼요.\n정말로 모집공고를 삭제하시나요?",
                cancelButtonText = "닫기",
                confirmButtonText = "삭제하기",
                onCancel = { onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentDeleteDialog(false)) },
                onConfirm = {
                    onAction(PartyRecruitmentEditAction.OnShowPartyRecruitmentDeleteDialog(false))
                    onAction(PartyRecruitmentEditAction.OnDeleteRecruitment(partyId = partyId, partyRecruitmentId = selectedRecruitmentId))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyEditRecruitmentScreenPreview() {
    PartyEditRecruitmentScreen(
        snackBarHostState = SnackbarHostState(),
        partyId = 1,
        partyRecruitmentEditState = PartyRecruitmentEditState(
            isShowHelpCard = false,
            partyRecruitment = listOf(
                PartyRecruitment(
                    id = 2081,
                    status = "active",
                    position = Position1(main = "개발자", sub = "안드로이드"),
                    content = "Domenic",
                    recruitingCount = 2,
                    recruitedCount = 1,
                    applicationCount = 1,
                    createdAt = "2024-10-25T21:38:28.850Z",
                    isOptionsRevealed = false
                ),
                PartyRecruitment(
                    id = 2081,
                    status = "active",
                    position = Position1(main = "개발자", sub = "안드로이드"),
                    content = "Domenic",
                    recruitingCount = 2,
                    recruitedCount = 1,
                    applicationCount = 1,
                    createdAt = "2024-10-25T21:38:28.850Z",
                    isOptionsRevealed = false
                )
            ),
        ),
        onNavigationClick = {},
        onGotoRecruitmentCreate = {},
        onGotoRecruitmentPreview = {},
        onAction = {},
        onManageClick = {},
        onClick = {}
    )
}