package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.NetworkImageLoad
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.TextComponent
import com.party.common.UIState
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyItemResponse
import com.party.domain.model.party.PartyListResponse
import com.party.presentation.screen.home.HomeListTitleArea
import com.party.presentation.screen.home.HomeViewModel

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
    )

    when(getPartyListState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = partyListResponse as SuccessResponse<PartyListResponse>
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
    partyListResponse: PartyListResponse?,
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
    partyItemResponse: PartyItemResponse,
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
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .height(271.dp),
        ) {
            PartyItemTopArea(
                imageUrl = partyItemResponse.image,
            )
            PartyItemBottomArea(
                title = partyItemResponse.title,
            )
        }
    }
}

@Composable
fun PartyItemTopArea(
    imageUrl: String? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ){
        NetworkImageLoad(
            url = imageUrl,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun PartyItemBottomArea(
    title: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(121.dp)
            .padding(8.dp)
    ) {
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            text = title,
            fontSize = T3,
            fontWeight = FontWeight.Bold,
        )
        HeightSpacer(heightDp = 16.dp)

        PartyItemBottomAreaDescription()
    }
}

@Composable
fun PartyItemBottomAreaDescription(
    modifier: Modifier = Modifier
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        text = "지금 4개 포지션 모집 중",
        fontSize = B3,
        textColor = PRIMARY,
        fontWeight = FontWeight.SemiBold,
    )
}