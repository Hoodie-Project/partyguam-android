package com.party.presentation.screen.manage_applicant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.component.dialog.OneButtonDialog
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.navigation.Screens
import com.party.presentation.enum.OrderDescType
import com.party.presentation.screen.manage_applicant.component.ManageApplicantChangeProgress
import com.party.presentation.screen.manage_applicant.component.ManageApplicantDescriptionArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantFilterArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantListArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantPositionTitle
import com.party.presentation.screen.manage_applicant.component.ManageApplicantRecruitmentListArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantScaffoldArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantSelectCategoryArea
import com.party.presentation.screen.manage_applicant.viewmodel.ManageApplicantViewModel
import com.party.presentation.screen.party_detail.component.RightModalDrawer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ManageApplicantScreenRoute(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    partyId: Int,
    manageApplicantViewModel: ManageApplicantViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        manageApplicantViewModel.acceptSuccess.collectLatest {
            snackBarHostState.showSnackbar("지원을 수락하였습니다.")
        }

        manageApplicantViewModel.rejectSuccess.collectLatest {
            snackBarHostState.showSnackbar("지원을 거절하였습니다.")
        }
    }

    LaunchedEffect(key1 = Unit) {
        manageApplicantViewModel.acceptAndRejectFail.collectLatest {
            snackBarHostState.showSnackbar(it)
        }
    }

    val manageApplicantState by manageApplicantViewModel.state.collectAsStateWithLifecycle()

    if(!manageApplicantState.isShowRecruitmentList){
        LaunchedEffect(key1 = Unit) {
            manageApplicantViewModel.getRecruitmentApplicant(partyId = partyId, partyRecruitmentId = manageApplicantState.selectedRecruitmentId, page = 1, limit = 50, sort = "createdAt", order = OrderDescType.DESC.type)
        }
    }else {
        LaunchedEffect(Unit) {
            manageApplicantViewModel.getPartyRecruitment(partyId = partyId, sort = "createdAt", order = OrderDescType.DESC.type, main = null, status = "active")
        }
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    RightModalDrawer(
        currentTitle = "지원자 관리",
        drawerState = drawerState,
        content = {
            ManageApplicantScreen(
                snackBarHostState = snackBarHostState,
                manageApplicantState = manageApplicantState,
                partyId = partyId,
                onNavigationClick = { navController.popBackStack() },
                onAction = { action ->
                    when(action){
                        is ManageApplicantAction.OnShowHelpCard -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnChangeProgress -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnChangeOrderBy -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnShowRecruitment -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnSelectRecruitmentTab -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnChangeApplicantOrderBy -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnSelectRecruitmentId -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnShowAcceptDialog -> manageApplicantViewModel.onAction(action)
                        is ManageApplicantAction.OnShowRejectDialog -> manageApplicantViewModel.onAction(action)
                    }
                },
                onManageClick = { scope.launch { drawerState.open() } }
            )
        },
        onGotoPartyEdit = {
            scope.launch { drawerState.close() }
            navController.popBackStack()
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
            navController.navigate(Screens.ManageApplicant(partyId = partyId))
        }
    )
}

@Composable
private fun ManageApplicantScreen(
    snackBarHostState: SnackbarHostState,
    manageApplicantState: ManageApplicantState,
    partyId: Int,
    onNavigationClick: () -> Unit,
    onManageClick: () -> Unit,
    onAction: (ManageApplicantAction) -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            ManageApplicantScaffoldArea(
                isShowRecruitmentList = manageApplicantState.isShowRecruitmentList,
                onShowHelpCard = { isShow -> onAction(ManageApplicantAction.OnShowHelpCard(isShowHelpCard = isShow)) },
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
            if(manageApplicantState.isShowRecruitmentList){
                ManageApplicantRecruitmentList(
                    manageApplicantState = manageApplicantState,
                    onChangeProgress = { isProgress -> onAction(ManageApplicantAction.OnChangeProgress(isProgress = isProgress, partyId = partyId))},
                    onChangeOrderBy = { isDesc -> onAction(ManageApplicantAction.OnChangeOrderBy(isDesc)) },
                    onClick = { selectedRecruitmentId, selectedMain, selectedSub ->
                        onAction(ManageApplicantAction.OnShowRecruitment(isShow = false)) // 지원자 관리로 이동
                        onAction(ManageApplicantAction.OnSelectRecruitmentId(selectedRecruitmentId, selectedMain, selectedSub))
                    }
                )
            } else {
                ManageApplicantArea(
                    manageApplicantState = manageApplicantState,
                    onSelectRecruitmentTab = {
                        selectedRecruitmentTab -> onAction(ManageApplicantAction.OnSelectRecruitmentTab(selectedRecruitmentTab))
                    },
                    onChangeOrderBy = { isDesc -> onAction(ManageApplicantAction.OnChangeApplicantOrderBy(isDesc)) },
                )
            }
        }
    }

    if(manageApplicantState.isShowAcceptDialog){
        OneButtonDialog(
            dialogTitle = "지원자를 수락했어요",
            description = "지원자가 합류를 결정하면\n파티 활동을 시작할 수 있어요.",
            buttonText = "확인",
            onCancel = { onAction(ManageApplicantAction.OnShowAcceptDialog(isShow = false)) },
            onConfirm = { onAction(ManageApplicantAction.OnShowAcceptDialog(isShow = false)) }
        )
    }

    if(manageApplicantState.isShowRejectDialog){
        OneButtonDialog(
            dialogTitle = "지원자를 거절했어요",
            description = "이 지원자는 파티에 참여할 수 없어요.",
            buttonText = "확인",
            onCancel = { onAction(ManageApplicantAction.OnShowRejectDialog(isShow = false)) },
            onConfirm = { onAction(ManageApplicantAction.OnShowRejectDialog(isShow = false)) }
        )
    }
}

@Composable
private fun ManageApplicantRecruitmentList(
    manageApplicantState: ManageApplicantState,
    onChangeProgress: (Boolean) -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
    onClick: (Int, String, String) -> Unit,
) {
    // Description Area
    HeightSpacer(16.dp)
    ManageApplicantDescriptionArea(
        title = "모집 공고 별 지원자",
        description = "지원자 관리를 원하는 모집 공고를 선택해주세요."
    )

    HeightSpacer(20.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 진랭중 필터
        ManageApplicantChangeProgress(
            isProgress = manageApplicantState.isProgress,
            onChangeProgress = onChangeProgress
        )

        // 모집일순 정렬

        ManageApplicantFilterArea(
            text = "모집일순",
            isDesc = manageApplicantState.isDesc,
            onChangeOrderBy = onChangeOrderBy,
        )
    }


    // 모집공고 리스트
    HeightSpacer(12.dp)
    ManageApplicantRecruitmentListArea(
        manageApplicantState = manageApplicantState,
        onClick = onClick,
    )
}

@Composable
private fun ManageApplicantArea(
    manageApplicantState: ManageApplicantState,
    onSelectRecruitmentTab: (String) -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    ManageApplicantPositionTitle(
        main = manageApplicantState.selectedRecruitmentMain,
        sub = manageApplicantState.selectedRecruitmentSub,
        recounting = manageApplicantState.recruitmentApplicantList.size,
    )

    ManageApplicantSelectCategoryArea(
        categoryList = listOf("전체", "검토중", "응답대기", "수락", "거절"),
        selectedCategory = manageApplicantState.selectedRecruitmentStatus,
        onClick = onSelectRecruitmentTab
    )

    // 지원일순 정렬
    ManageApplicantFilterArea(
        text = "지원일순",
        isDesc = manageApplicantState.isShowApplicantCreatedDt,
        onChangeOrderBy = onChangeOrderBy,
    )

    // 지원자 리스트
    HeightSpacer(12.dp)
    ManageApplicantListArea(
        manageApplicantState = manageApplicantState,
    )
}

@Preview(showBackground = true)
@Composable
private fun ManageApplicantScreenPreview() {
    ManageApplicantScreen(
        snackBarHostState = SnackbarHostState(),
        manageApplicantState = ManageApplicantState(
            isShowRecruitmentList = true,
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
        ),),
        partyId = 1,
        onNavigationClick = {},
        onAction = {},
        onManageClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ManageApplicantScreenPreview2() {
    ManageApplicantScreen(
        snackBarHostState = SnackbarHostState(),
        manageApplicantState = ManageApplicantState(
            isShowRecruitmentList = false,
            partyRecruitment = listOf()
        ),
        partyId = 0,
        onNavigationClick = {},
        onAction = {},
        onManageClick = {}
    )
}