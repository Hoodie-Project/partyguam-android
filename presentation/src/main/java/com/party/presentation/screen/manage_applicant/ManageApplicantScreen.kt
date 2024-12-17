package com.party.presentation.screen.manage_applicant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.presentation.screen.manage_applicant.component.ManageApplicantDescriptionArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantFilterArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantListArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantPositionTitle
import com.party.presentation.screen.manage_applicant.component.ManageApplicantRecruitmentListArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantScaffoldArea
import com.party.presentation.screen.manage_applicant.component.ManageApplicantSelectCategoryArea
import com.party.presentation.screen.manage_applicant.viewmodel.ManageApplicantViewModel

@Composable
fun ManageApplicantScreenRoute(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    partyId: Int,
    manageApplicantViewModel: ManageApplicantViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        manageApplicantViewModel.getPartyRecruitment(partyId = partyId, sort = "createdAt", order = "DESC", main = null)
    }

    val manageApplicantState by manageApplicantViewModel.state.collectAsStateWithLifecycle()

    ManageApplicantScreen(
        snackBarHostState = snackBarHostState,
        manageApplicantState = manageApplicantState,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            when(action){
                is ManageApplicantAction.OnShowHelpCard -> manageApplicantViewModel.onAction(action)
                is ManageApplicantAction.OnChangeOrderBy -> manageApplicantViewModel.onAction(action)
                is ManageApplicantAction.OnShowRecruitment -> manageApplicantViewModel.onAction(action)
                is ManageApplicantAction.OnSelectRecruitmentTab -> manageApplicantViewModel.onAction(action)
                is ManageApplicantAction.OnChangeApplicantOrderBy -> manageApplicantViewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun ManageApplicantScreen(
    snackBarHostState: SnackbarHostState,
    manageApplicantState: ManageApplicantState,
    onNavigationClick: () -> Unit,
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
                    onChangeOrderBy = { isDesc -> onAction(ManageApplicantAction.OnChangeOrderBy(isDesc)) },
                    onClick = {
                        onAction(ManageApplicantAction.OnShowRecruitment(isShow = false)) // 지원자 관리로 이동
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
}

@Composable
private fun ManageApplicantRecruitmentList(
    manageApplicantState: ManageApplicantState,
    onChangeOrderBy: (Boolean) -> Unit,
    onClick: (Int) -> Unit,
) {
    // Description Area
    HeightSpacer(16.dp)
    ManageApplicantDescriptionArea(
        title = "모집 공고 별 지원자",
        description = "지원자 관리를 원하는 모집 공고를 선택해주세요."
    )

    // 모집일순 정렬
    HeightSpacer(20.dp)
    ManageApplicantFilterArea(
        text = "모집일순",
        isDesc = manageApplicantState.isDesc,
        onChangeOrderBy = onChangeOrderBy,
    )

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
        main = "개발자",
        sub = "안드로이드",
        recounting = 3,
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
                    position = Position1(main = "개발자", sub = "안드로이드"),
                    content = "Domenic",
                    recruitingCount = 2,
                    recruitedCount = 1,
                    applicationCount = 1,
                    createdAt = "2024-10-25T21:38:28.850Z",
                    isOptionsRevealed = false
                )
        ),),
        onNavigationClick = {},
        onAction = {}
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
        onNavigationClick = {},
        onAction = {}
    )
}