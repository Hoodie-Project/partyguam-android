package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.TextComponent
import com.party.common.UIState
import com.party.common.component.ImageLoading
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyItem
import com.party.domain.model.party.PartyList
import com.party.presentation.screen.home.HomeListTitleArea
import com.party.presentation.screen.home.PartyCategory
import com.party.presentation.screen.home.viewmodel.HomeViewModel

@Composable
fun PartyListArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
) {
    val getPartyListState by homeViewModel.getPartyListState.collectAsState()
    val partyListResponse = getPartyListState.data

    LaunchedEffect(Unit) {
        homeViewModel.getPartyList(page = 1, size = 10, sort = "createdAt", order = "DESC")
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
            PartyItem(
                partyItemResponse = item,
                onClick = {},
            )
        }
    }
}

@Composable
fun PartyItem(
    partyItemResponse: PartyItem,
    onClick: () -> Unit,
) {
    Card(
        onClick = {},
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .height(295.dp)
                .padding(12.dp),
        ) {
            PartyItemTopArea(
                imageUrl = partyItemResponse.image,
            )
            HeightSpacer(heightDp = 12.dp)
            PartyItemBottomArea(
                category = partyItemResponse.partyType.type,
                title = partyItemResponse.title,
                recruitmentCount = partyItemResponse.recruitmentCount,
            )
        }
    }
}

@Composable
fun PartyItemTopArea(
    imageUrl: String? = null,
) {
    ImageLoading(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        imageUrl = imageUrl,
        roundedCornerShape = MEDIUM_CORNER_SIZE
    )
}

@Composable
fun PartyItemBottomArea(
    category: String,
    title: String,
    recruitmentCount: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(142.dp)
    ) {
        PartyCategory(category = category)
        HeightSpacer(heightDp = 4.dp)
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            text = title,
            fontSize = T3,
            fontWeight = FontWeight.Bold,
            textAlign = Alignment.TopStart,
        )
        HeightSpacer(heightDp = 12.dp)

        PartyItemBottomAreaDescription(
            recruitmentCount = recruitmentCount
        )
    }
}

@Composable
fun PartyItemBottomAreaDescription(
    recruitmentCount: Int,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        text = stringResource(id = R.string.home_list_party_recruitment_count, recruitmentCount),
        fontSize = B3,
        textColor = PRIMARY,
        fontWeight = FontWeight.SemiBold,
    )
}