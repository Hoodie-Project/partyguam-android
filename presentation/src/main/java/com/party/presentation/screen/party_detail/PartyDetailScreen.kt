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
import com.party.presentation.screen.home.tab_main.ErrorArea
import com.party.presentation.screen.party_detail.component.PartyDetailArea
import com.party.presentation.screen.party_detail.viewmodel.PartyViewModel

@Composable
fun PartyDetailScreen(
    snackBarHostState: SnackbarHostState,
    partyViewModel: PartyViewModel,
    partyId: Int,
) {
    LaunchedEffect(Unit) {
        partyViewModel.getPartyDetail(partyId = partyId)
    }

    val partyDetailState by partyViewModel.getPartyDetailState.collectAsStateWithLifecycle()

    PartyDetailContent(
        snackBarHostState = snackBarHostState,
        partyDetailState = partyDetailState,
    )
}

@Composable
fun PartyDetailContent(
    snackBarHostState: SnackbarHostState,
    partyDetailState: UIState<ServerApiResponse<PartyDetail>>,
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
                    partyDetailTabList = partyDetailTabList,
                    partyDetail = successResult.data ?: return,
                    selectedTabText = selectedTabText,
                    onTabClick = { selectedTabText = it },
                )
            }
            is UIState.Error -> { ErrorArea() }
            is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
        }
    }
}