package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.PartyListItem1
import com.party.common.snackBarMessage
import com.party.domain.model.party.PartyList
import com.party.presentation.screen.home.HomeListTitleArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel

@Composable
fun PartyListArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
) {
    val getPartyListState by homeViewModel.getPartyListState.collectAsStateWithLifecycle()
    val partyListResponse = getPartyListState.data

    LaunchedEffect(Unit) {
        homeViewModel.getPartyList(page = 1, size = 10, sort = "createdAt", order = "DESC", titleSearch = null)
    }

    HomeListTitleArea(
        title = stringResource(id = R.string.home_list_party_title),
        titleIcon = painterResource(id = R.drawable.arrow_right_icon),
        description = stringResource(id = R.string.home_list_party_description),
        onClick = {},
    )

    when(getPartyListState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = partyListResponse as SuccessResponse<PartyList>
            PartyListArea(
                partyListResponse = successResult.data,
            )
        }
        is UIState.Error -> {}
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }
}

@Composable
fun PartyListArea(
    partyListResponse: PartyList?,
) {
    HeightSpacer(heightDp = 20.dp)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = partyListResponse?.parties ?: emptyList(),
            key = { index, _ ->
                index
            }
        ) { _, item ->
            PartyListItem1(
                imageUrl = item.image,
                category = item.tag,
                title = item.title,
                recruitmentCount = item.recruitmentCount,
                onClick = {}
            )
        }
    }
}