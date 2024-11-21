package com.party.presentation.screen.party_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.partyDetailTabList
import com.party.common.snackBarMessage
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyRecruitment
import com.party.presentation.screen.home.tab_main.ErrorArea
import com.party.presentation.screen.party_detail.component.PartyDetailArea
import com.party.presentation.screen.party_detail.viewmodel.PartyViewModel

@Composable
fun PartyDetailScreen(
    snackBarHostState: SnackbarHostState,
    partyViewModel: PartyViewModel,
    partyId: Int,
) {
    // 선택된 필터 (전체, 기획자, 디자이너, 개발자, 마케터)
    var selectedPosition by remember {
        mutableStateOf("전체")
    }

    LaunchedEffect(Unit) {
        partyViewModel.getPartyDetail(partyId = partyId)
        partyViewModel.getPartyRecruitment(partyId = partyId, sort = "createdAt", order = "DESC", main = null)
    }

    val partyDetailState by partyViewModel.getPartyDetailState.collectAsStateWithLifecycle()
    val partyRecruitmentState by partyViewModel.getPartyRecruitmentState.collectAsStateWithLifecycle()

    PartyDetailContent(
        snackBarHostState = snackBarHostState,
        partyDetailState = partyDetailState,
        partyRecruitmentState = partyRecruitmentState,
        selectedPosition = selectedPosition,
        onReset = { selectedPosition = ""},
        onApply = {
            selectedPosition = it
            partyViewModel.getPartyRecruitment(partyId = partyId, sort = "createdAt", order = "DESC", main = if (selectedPosition == "전체") null else selectedPosition)
        },
    )
}

@Composable
fun PartyDetailContent(
    snackBarHostState: SnackbarHostState,
    partyDetailState: UIState<ServerApiResponse<PartyDetail>>,
    partyRecruitmentState: UIState<ServerApiResponse<List<PartyRecruitment>>>,
    selectedPosition: String,
    onReset: () -> Unit,
    onApply: (String) -> Unit,
){
    var selectedTabText by remember {
        mutableStateOf(partyDetailTabList[0])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when(partyDetailState){
            is UIState.Idle -> {}
            is UIState.Loading -> { LoadingProgressBar() }
            is UIState.Success -> {
                val successResult = partyDetailState.data as SuccessResponse<PartyDetail>
                PartyDetailArea(
                    snackBarHostState = snackBarHostState,
                    partyDetailTabList = partyDetailTabList,
                    partyDetail = successResult.data ?: return,
                    selectedTabText = selectedTabText,
                    onTabClick = { selectedTabText = it },
                    partyRecruitmentState = partyRecruitmentState,
                    selectedPosition = selectedPosition,
                    onReset = onReset,
                    onApply = onApply,
                )
            }
            is UIState.Error -> { ErrorArea() }
            is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
        }
    }
}