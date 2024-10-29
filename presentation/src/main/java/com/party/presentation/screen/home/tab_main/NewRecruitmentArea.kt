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
import androidx.compose.ui.Alignment
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
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.RecruitmentItemResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.presentation.screen.home.HomeListTitleArea
import com.party.presentation.screen.home.HomeViewModel
import com.party.presentation.screen.home.PartyCategory
import com.party.presentation.screen.home.PositionArea
import com.party.presentation.screen.home.RecruitmentCountArea

@Composable
fun NewRecruitmentArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    onGoRecruitment: () -> Unit,
) {
    val getRecruitmentListState by homeViewModel.getRecruitmentListState.collectAsState()
    val recruitmentListResponse = getRecruitmentListState.data

    LaunchedEffect(Unit) {
        homeViewModel.getRecruitmentList(page = 1, size = 10, sort = "createdAt", order = "DESC")
    }

    HomeListTitleArea(
        title = stringResource(id = R.string.home_list_new_title),
        titleIcon = painterResource(id = R.drawable.arrow_right_icon),
        description = stringResource(id = R.string.home_list_new_description),
        onClick = { onGoRecruitment() },
    )

    when (getRecruitmentListState) {
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = recruitmentListResponse as SuccessResponse<RecruitmentListResponse>
            RecruitmentListArea(successResult.data)
        }
        is UIState.Error -> {}
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }
}

@Composable
fun RecruitmentListArea(
    recruitmentListResponse: RecruitmentListResponse?,
) {
    HeightSpacer(heightDp = 20.dp)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = recruitmentListResponse?.partyRecruitments ?: emptyList(),
            key = { index, _ ->
                index
            }
        ) { _, item ->
            RecruitmentItem(
                recruitmentLisItemResponse = item,
                onClick = {},
            )
        }
    }
}


@Composable
fun RecruitmentItem(
    recruitmentLisItemResponse: RecruitmentItemResponse,
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
                .height(315.dp)
                .padding(12.dp),
        ) {
            RecruitmentItemTopArea(
                imageUrl = recruitmentLisItemResponse.party.image,
            )
            HeightSpacer(heightDp = 12.dp)
            RecruitmentItemBottomArea(
                category = recruitmentLisItemResponse.party.partyType.type,
                title = recruitmentLisItemResponse.party.title,
                main = recruitmentLisItemResponse.position.main,
                sub = recruitmentLisItemResponse.position.sub,
                recruitingCount = recruitmentLisItemResponse.recruitingCount,
                recruitedCount = recruitmentLisItemResponse.recruitedCount,
            )
        }
    }
}

@Composable
fun RecruitmentItemTopArea(
    imageUrl: String? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        border = BorderStroke(1.dp, GRAY100),
        shape = RoundedCornerShape(MEDIUM_CORNER_SIZE),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ){
            NetworkImageLoad(
                url = imageUrl,
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
fun RecruitmentItemBottomArea(
    category: String,
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
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

        HeightSpacer(heightDp = 4.dp)

        PositionArea(
            modifier = Modifier
                .height(20.dp),
            main = main,
            sub = sub,
        )
        HeightSpacer(heightDp = 12.dp)
        RecruitmentCountArea(
            modifier = Modifier
                .height(20.dp),
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
        )
    }
}