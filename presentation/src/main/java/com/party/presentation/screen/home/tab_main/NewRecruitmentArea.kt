package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.LoadingProgressBar
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.component.ImageLoading
import com.party.guam.design.GRAY100
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.MEDIUM_CORNER_SIZE
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.domain.model.party.RecruitmentItem
import com.party.domain.model.party.RecruitmentList
import com.party.presentation.screen.home.HomeState
import com.party.presentation.screen.home.component.HomeListTitleArea
import com.party.presentation.screen.home.component.PartyCategory
import com.party.presentation.screen.home.component.PositionArea
import com.party.presentation.screen.home.component.RecruitmentCountArea

@Composable
fun NewRecruitmentArea(
    homeState: HomeState,
    onGoRecruitment: () -> Unit,
    onClick: (Int, Int) -> Unit,
) {

    HomeListTitleArea(
        title = stringResource(id = R.string.home_list_new_title),
        text = "더보기",
        titleIcon = painterResource(id = R.drawable.icon_arrow_right),
        description = stringResource(id = R.string.home_list_new_description),
        onReload = onGoRecruitment,
    )

    when {
        homeState.isLoadingRecruitmentList -> { LoadingProgressBar() }
        homeState.recruitmentList.partyRecruitments.isNotEmpty() -> {
            RecruitmentListArea(
                recruitmentListResponse = homeState.recruitmentList,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun RecruitmentListArea(
    recruitmentListResponse: RecruitmentList?,
    onClick: (Int, Int) -> Unit,
) {
    HeightSpacer(heightDp = 20.dp)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(2.dp),
    ) {
        itemsIndexed(
            items = recruitmentListResponse?.partyRecruitments ?: emptyList(),
            key = { index, _ ->
                index
            }
        ) { _, item ->
            RecruitmentItem(
                recruitmentLisItemResponse = item,
                onClick = onClick,
            )
        }
    }
}


@Composable
private fun RecruitmentItem(
    recruitmentLisItemResponse: RecruitmentItem,
    onClick: (Int, Int) -> Unit,
) {
    Card(
        onClick = { onClick(recruitmentLisItemResponse.party.id, recruitmentLisItemResponse.id) },
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
                .width(224.dp)
                .height(311.dp)
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
                onClick = { onClick(recruitmentLisItemResponse.party.id, recruitmentLisItemResponse.id) },
            )
        }
    }
}

@Composable
fun RecruitmentItemTopArea(
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
fun RecruitmentItemBottomArea(
    category: String,
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(142.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        ) {
            PartyCategory(category = category)
            HeightSpacer(heightDp = 4.dp)
            TextComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 6.dp),
                text = title,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
                align = Alignment.TopStart,
                onClick = onClick
            )

            HeightSpacer(heightDp = 4.dp)

            PositionArea(
                modifier = Modifier
                    .height(20.dp)
                    .padding(start = 6.dp),
                main = main,
                sub = sub,
                onClick = onClick
            )
        }

        HeightSpacer(heightDp = 12.dp)
        RecruitmentCountArea(
            modifier = Modifier
                .height(17.dp),
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
            onClick = onClick
        )
    }
}