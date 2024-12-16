package com.party.presentation.screen.party_edit_recruitment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.Position1
import com.party.presentation.screen.party_edit_recruitment.component.PartyEditRecruitmentScaffoldArea
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentEditDescriptionArea
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentEditFilterArea
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentEditHelpCard
import com.party.presentation.screen.party_edit_recruitment.component.PartyRecruitmentListArea
import com.party.presentation.screen.party_edit_recruitment.viewmodel.PartyRecruitmentEditViewModel

@Composable
fun PartyEditRecruitmentScreenRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    partyId: Int,
    partyRecruitmentEditViewModel: PartyRecruitmentEditViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        partyRecruitmentEditViewModel.getPartyRecruitment(partyId = partyId, sort = "createdAt", order = "DESC", main = null)
    }
    val partyRecruitmentEditState by partyRecruitmentEditViewModel.state.collectAsStateWithLifecycle()

    PartyEditRecruitmentScreen(
        snackBarHostState = snackBarHostState,
        partyRecruitmentEditState = partyRecruitmentEditState,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            when(action){
                is PartyRecruitmentEditAction.OnShowHelpCard -> partyRecruitmentEditViewModel.onAction(action)
                is PartyRecruitmentEditAction.OnChangeOrderBy -> partyRecruitmentEditViewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun PartyEditRecruitmentScreen(
    snackBarHostState: SnackbarHostState,
    partyRecruitmentEditState: PartyRecruitmentEditState,
    onNavigationClick: () -> Unit,
    onAction: (PartyRecruitmentEditAction) -> Unit
) {

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyEditRecruitmentScaffoldArea(
                onShowHelpCard = { onAction(PartyRecruitmentEditAction.OnShowHelpCard(it)) },
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
            // 모집공고 선택 Description
            Box {
                if(partyRecruitmentEditState.isShowHelpCard){
                    PartyRecruitmentEditHelpCard(
                        onCloseHelpCard = { onAction(PartyRecruitmentEditAction.OnShowHelpCard(false)) }
                    )
                }else {
                    HeightSpacer(16.dp)
                    PartyRecruitmentEditDescriptionArea(
                        title = "모집공고",
                        recruitmentCount = partyRecruitmentEditState.partyRecruitment.size,
                        description = "편집을 원하는 모집 공고를 선택해 주세요.",
                    )
                }
            }

            // 참여일순 정렬
            HeightSpacer(20.dp)
            PartyRecruitmentEditFilterArea(
                partyRecruitmentEditState = partyRecruitmentEditState,
                onChangeOrderBy = { isDesc -> onAction(PartyRecruitmentEditAction.OnChangeOrderBy(isDesc)) }
            )

            // 리스트
            HeightSpacer(12.dp)
            PartyRecruitmentListArea(
                partyRecruitmentEditState = partyRecruitmentEditState
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PartyEditRecruitmentScreenPreview() {
    PartyEditRecruitmentScreen(
        snackBarHostState = SnackbarHostState(),
        partyRecruitmentEditState = PartyRecruitmentEditState(
            isShowHelpCard = false,
            partyRecruitment = listOf(
                PartyRecruitment(
                    id = 2081,
                    position = Position1(main = "개발자", sub = "안드로이드"),
                    content = "Domenic",
                    recruitingCount = 2,
                    recruitedCount = 1,
                    applicationCount = 1,
                    createdAt = "2024-10-25T21:38:28.850Z"
                )
            )

        ),
        onNavigationClick = {},
        onAction = {}
    )
}