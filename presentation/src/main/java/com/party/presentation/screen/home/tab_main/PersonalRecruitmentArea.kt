package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PersonalRecruitmentItem
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.presentation.screen.home.HomeListTitleArea
import com.party.presentation.screen.home.PartyCategory
import com.party.presentation.screen.home.PositionArea
import com.party.presentation.screen.home.RecruitmentCountArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel

@Composable
fun PersonalRecruitmentArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
) {
    LaunchedEffect(Unit) {
        homeViewModel.getPersonalRecruitmentList(page = 1, size = 10, sort = "createdAt", order = "DESC")
    }

    val getPersonalRecruitmentListState by homeViewModel.getPersonalRecruitmentListState.collectAsState()
    val personalRecruitmentListResponse = getPersonalRecruitmentListState.data

    HeightSpacer(heightDp = 40.dp)

    HomeListTitleArea(
        title = stringResource(id = R.string.home_list_personal_title),
        titleIcon = painterResource(id = R.drawable.reload),
        description = stringResource(id = R.string.home_list_personal_description),
        onClick = {
            homeViewModel.getPersonalRecruitmentList(page = 1, size = 10, sort = "createdAt", order = "DESC")
        },
    )

    when(getPersonalRecruitmentListState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = personalRecruitmentListResponse as SuccessResponse<PersonalRecruitmentList>
            PersonalRecruitmentListArea(successResult.data)
        }
        is UIState.Error -> {ErrorArea()}
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }
}

@Composable
fun ErrorArea() {
    HeightSpacer(heightDp = 20.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GRAY100),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.icon_error),
                    contentDescription = "error",
                    tint = DARK100
                )
                HeightSpacer(heightDp = 6.dp)
                Text(
                    text = "모집공고를 불러오는 데 실패했어요\n            다시 시도해주세요",
                    fontSize = B1,
                    fontWeight = FontWeight.SemiBold,
                    color = DARK100,
                )
            }
        }
    }

}

@Composable
fun PersonalRecruitmentListArea(
    personalRecruitmentListResponse: PersonalRecruitmentList?,
) {
    HeightSpacer(heightDp = 20.dp)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = personalRecruitmentListResponse?.partyRecruitments ?: emptyList(),
            key = { index, _ ->
                index
            }
        ) { _, item ->
            PersonalRecruitmentItem(
                personalRecruitmentLisItemResponse = item,
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Composable
fun PersonalRecruitmentItem(
    personalRecruitmentLisItemResponse: PersonalRecruitmentItem,
    onClick: () -> Unit,
) {
    Card(
        onClick = { onClick() },
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
            PersonalRecruitmentItemTopArea(
                imageUrl = personalRecruitmentLisItemResponse.party.image,
            )
            HeightSpacer(heightDp = 12.dp)
            PersonalRecruitmentItemBottomArea(
                category = personalRecruitmentLisItemResponse.party.partyType.type,
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
    ImageLoading(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        imageUrl = imageUrl,
        roundedCornerShape = MEDIUM_CORNER_SIZE
    )
}

@Composable
fun PersonalRecruitmentItemBottomArea(
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