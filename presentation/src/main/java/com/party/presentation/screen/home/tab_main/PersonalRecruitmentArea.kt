package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.NetworkImageLoad
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.TextComponent
import com.party.common.UIState
import com.party.common.WidthSpacer
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.DARK400
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.presentation.screen.home.HomeListTitleArea
import com.party.presentation.screen.home.HomeViewModel

@Composable
fun PersonalRecruitmentArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
) {
    val getPersonalRecruitmentListState by homeViewModel.getPersonalRecruitmentListState.collectAsState()
    val personalRecruitmentListResponse = getPersonalRecruitmentListState.data

    LaunchedEffect(Unit) {
        homeViewModel.getPersonalRecruitmentList(page = 1, size = 1, sort = "createdAt", order = "DESC")
    }

    HeightSpacer(heightDp = 40.dp)

    HomeListTitleArea(
        title = stringResource(id = R.string.home_list_personal_title),
        description = stringResource(id = R.string.home_list_personal_description),
    )

    when(getPersonalRecruitmentListState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = personalRecruitmentListResponse as SuccessResponse<PersonalRecruitmentListResponse>
            PersonalRecruitmentListArea(successResult.data)
        }
        is UIState.Error -> {

        }
        is UIState.Exception -> {
            snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
        }
    }

}

@Composable
fun PersonalRecruitmentListArea(
    personalRecruitmentListResponse: PersonalRecruitmentListResponse?,
) {

    HeightSpacer(heightDp = 20.dp)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(
            items = personalRecruitmentListResponse?.partyRecruitments ?: emptyList(),
            key = { index, _ ->
                index
            }
        ) { _, item ->
            PersonalRecruitmentItem(
                personalRecruitmentLisItemResponse = item,
            )
        }
    }
}

@Composable
fun PersonalRecruitmentItem(
    personalRecruitmentLisItemResponse: PersonalRecruitmentItemResponse,
) {
    Card(
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
                .height(271.dp),
        ) {
            PersonalRecruitmentItemTopArea(
                imageUrl = personalRecruitmentLisItemResponse.party.image,
            )
            PersonalRecruitmentItemBottomArea(
                title = personalRecruitmentLisItemResponse.party.title,
                main = personalRecruitmentLisItemResponse.position.main,
                sub = personalRecruitmentLisItemResponse.position.sub,
                recruitingCount = personalRecruitmentLisItemResponse.recruitingCount,
                recruitedCount = personalRecruitmentLisItemResponse.recruitedCount,
            )
        }
    }
}

@Composable
fun PersonalRecruitmentItemTopArea(
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
fun PersonalRecruitmentItemBottomArea(
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
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
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
        )
    }
}

@Composable
fun PositionArea(
    modifier: Modifier,
    main: String,
    sub: String,
) {
    TextComponent(
        modifier = modifier
            .fillMaxWidth(),
        text = "$main | $sub",
        fontSize = B2,
    )
}

@Composable
fun RecruitmentCountArea(
    recruitingCount: Int,
    recruitedCount: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        TextComponent(
            text = "모집중",
            fontSize = B3,
        )

        WidthSpacer(widthDp = 4.dp)

        TextComponent(
            text = "$recruitedCount / $recruitingCount",
            fontSize = B3,
            textColor = RED,
        )
    }
}