package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.RecruitmentItemResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.domain.model.party.RecruitmentPartyResponse
import com.party.domain.model.party.RecruitmentPartyTypeResponse
import com.party.domain.model.party.RecruitmentPositionResponse
import com.party.presentation.screen.home.HomeViewModel
import com.party.presentation.screen.home.PartyCategory
import com.party.presentation.screen.home.PositionArea
import com.party.presentation.screen.home.RecruitmentCountArea

@Composable
fun RecruitmentColumnListArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    selectedCreateDataOrderByDesc: Boolean,
    selectedPartyType: MutableList<String>,
    onRecruitmentItemClick: (Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        homeViewModel.getRecruitmentList(page = 1, size = 20, sort = "createdAt", order = "DESC")
    }

    val getRecruitmentListState by homeViewModel.getRecruitmentListState.collectAsState()
    val recruitmentListResponse = getRecruitmentListState.data

    when (getRecruitmentListState) {
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = recruitmentListResponse as SuccessResponse<RecruitmentListResponse>
            val resultList = successResult.data?.partyRecruitments
            val sortedList = if(selectedCreateDataOrderByDesc) resultList?.sortedByDescending { it.createdAt } else resultList?.sortedBy { it.createdAt }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                itemsIndexed(
                    items = test(sortedList, selectedPartyType) ?: emptyList() ,
                    key = { index, _ ->
                        index
                    }
                ){_, item ->
                    RecruitmentColumnListItem(
                        recruitmentItemResponse = item,
                        onRecruitmentItemClick = onRecruitmentItemClick
                    )
                }
            }
        }

        is UIState.Error -> {}
        is UIState.Exception -> snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
    }
}

fun test(
    sortedList: List<RecruitmentItemResponse>?,
    selectedPartyType: MutableList<String>,
): List<RecruitmentItemResponse>? {
    return if(selectedPartyType.isNotEmpty()){
        sortedList?.filter { selectedPartyType.contains(it.party.partyType.type) }
    } else {
        sortedList
    }
}

@Composable
fun RecruitmentColumnListItem(
    recruitmentItemResponse: RecruitmentItemResponse,
    onRecruitmentItemClick: (Int) -> Unit,
) {
    Card(
        onClick = {onRecruitmentItemClick(recruitmentItemResponse.id)},
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            PartyCategory(category = recruitmentItemResponse.party.partyType.type)
            HeightSpacer(heightDp = 8.dp)
            RecruitmentContentArea(
                imageUrl = recruitmentItemResponse.party.image,
                title = recruitmentItemResponse.party.title,
                main = recruitmentItemResponse.position.main,
                sub = recruitmentItemResponse.position.sub,
                recruitingCount = recruitmentItemResponse.recruitingCount,
                recruitedCount = recruitmentItemResponse.recruitedCount,
            )
        }
    }
}

@Composable
fun RecruitmentContentArea(
    imageUrl: String?,
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RecruitmentContentImage(
            imageUrl = imageUrl,
        )
        WidthSpacer(widthDp = 12.dp)
        RecruitmentContent(
            title = title,
            main = main,
            sub = sub,
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
        )
    }
}

@Composable
fun RecruitmentContentImage(
    imageUrl: String?,
) {
    Card(
        modifier = Modifier
            .width(96.dp)
            .height(72.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
    ) {
       Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            NetworkImageLoad(
                url = imageUrl,
            )
        }
    }
}

@Composable
fun RecruitmentContent(
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
) {
    Column(
        modifier = Modifier
            .width(195.dp)
            .height(71.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp),
            text = title,
            fontSize = T3,
            fontWeight = FontWeight.SemiBold,
            textColor = BLACK,
        )
        HeightSpacer(heightDp = 5.dp)
        PositionArea(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            main = main,
            sub = sub
        )
        HeightSpacer(heightDp = 5.dp)
        RecruitmentCountArea(
            modifier = Modifier.height(20.dp),
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
            horizontalArrangement = Arrangement.Start,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecruitmentColumnListItemPreview() {
    RecruitmentColumnListItem(
        recruitmentItemResponse = RecruitmentItemResponse(
            id = 1,
            partyId = 1,
            positionId = 1,
            recruitedCount = 1,
            recruitingCount = 10,
            content = "content",
            createdAt = "2022-01-01",
            party = RecruitmentPartyResponse(
                title = "파티를 모집중입니다",
                image = "image",
                partyType = RecruitmentPartyTypeResponse(
                    id = 1,
                    type = "해커톤"
                )
            ),
            position = RecruitmentPositionResponse(
                id = 1,
                main = "개발자",
                sub = "Android"
            )
        ),
        onRecruitmentItemClick = {}
    )
}
