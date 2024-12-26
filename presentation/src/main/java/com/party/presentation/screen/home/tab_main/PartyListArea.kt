package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.component.PartyListItem1
import com.party.domain.model.party.PartyList
import com.party.presentation.screen.home.HomeState
import com.party.presentation.screen.home.component.HomeListTitleArea

@Composable
fun PartyListArea(
    homeState: HomeState,
    onGoParty: () -> Unit,
    onClick: (Int) -> Unit,
) {
    HomeListTitleArea(
        title = stringResource(id = R.string.home_list_party_title),
        titleIcon = painterResource(id = R.drawable.icon_arrow_right),
        description = stringResource(id = R.string.home_list_party_description),
        onClick = onGoParty,
    )

    when {
        homeState.isLoadingPartyList -> LoadingProgressBar()
        homeState.partyList.parties.isNotEmpty() -> {
            PartyListArea(
                partyListResponse = homeState.partyList,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun PartyListArea(
    partyListResponse: PartyList?,
    onClick: (Int) -> Unit,
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
                type = item.partyType.type,
                title = item.title,
                recruitmentCount = item.recruitmentCount,
                onClick = {onClick(item.id)}
            )
        }
    }
}