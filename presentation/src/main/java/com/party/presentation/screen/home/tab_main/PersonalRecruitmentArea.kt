package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.LoadingProgressBar
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.component.ImageLoading
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PersonalRecruitmentItem
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.presentation.screen.home.HomeState
import com.party.presentation.screen.home.component.HomeListTitleArea
import com.party.presentation.screen.home.component.PartyCategory
import com.party.presentation.screen.home.component.PositionArea
import com.party.presentation.screen.home.component.RecruitmentCountArea

@Composable
fun PersonalRecruitmentArea(
    homeState: HomeState,
    onReload: () -> Unit,onClick: (Int, Int) -> Unit,
    onGotoDetailProfile: () -> Unit,
) {
    when {
        homeState.isLoadingPersonalRecruitmentList -> LoadingProgressBar()
        homeState.isNotProfileError -> NotInputProfileInformation(
            onGotoDetailProfile = onGotoDetailProfile
        )
        homeState.personalRecruitmentList.partyRecruitments.isNotEmpty() -> {
            HeightSpacer(heightDp = 40.dp)
            HomeListTitleArea(
                title = stringResource(id = R.string.home_list_personal_title),
                titleIcon = painterResource(id = R.drawable.icon_reload),
                description = stringResource(id = R.string.home_list_personal_description),
                onReload = onReload
            )

            PersonalRecruitmentListArea(
                personalRecruitmentListResponse = homeState.personalRecruitmentList,
                onClick = onClick
            )
        }
    }
}

@Composable
fun PersonalRecruitmentListArea(
    personalRecruitmentListResponse: PersonalRecruitmentList?,
    onClick: (Int, Int) -> Unit,
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
                onClick = onClick
            )
        }
    }
}

@Composable
fun PersonalRecruitmentItem(
    personalRecruitmentLisItemResponse: PersonalRecruitmentItem,
    onClick: (Int, Int) -> Unit,
) {
    Card(
        onClick = { onClick(personalRecruitmentLisItemResponse.party.id, personalRecruitmentLisItemResponse.id) },
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
                onClick = { onClick(personalRecruitmentLisItemResponse.party.id, personalRecruitmentLisItemResponse.id) },            )
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
                    .padding(start = 6.dp)
                ,
                text = title,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
                align = Alignment.TopStart,
                onClick = onClick
            )

            HeightSpacer(heightDp = 4.dp)

            PositionArea(
                modifier = Modifier
                    .height(20.dp),
                main = main,
                sub = sub,
                onClick = onClick
            )
        }

        HeightSpacer(heightDp = 12.dp)
        RecruitmentCountArea(
            modifier = Modifier
                .height(20.dp),
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
            onClick = onClick
        )
    }
    /*Column(
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
            align = Alignment.TopStart,
        )

        HeightSpacer(heightDp = 4.dp)
        PositionArea(
            modifier = Modifier
                .height(20.dp),
            main = main,
            sub = sub,
            onClick = {}
        )
        HeightSpacer(heightDp = 12.dp)
        RecruitmentCountArea(
            modifier = Modifier
                .height(20.dp),
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
            onClick = {}
        )
    }*/
}

@Composable
fun NotInputProfileInformation(
    modifier: Modifier = Modifier,
    onGotoDetailProfile: () -> Unit,
) {
    HeightSpacer(heightDp = 40.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.not_profille),
            contentDescription = "not_profile",
            modifier = modifier
                .width(48.dp)
                .height(60.dp)
        )
        HeightSpacer(heightDp = 12.dp)
        TextComponent(
            text = "세부프로필을 완료하고\n맞춤 모집공고를 추천받으세요!",
            textAlign = TextAlign.Center,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
        )
        HeightSpacer(heightDp = 12.dp)

        Card(
            onClick = onGotoDetailProfile,
            modifier = Modifier
                .width(160.dp)
                .height(32.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, PRIMARY),
            colors = CardDefaults.cardColors(
                containerColor = WHITE,
                contentColor = Color.Black
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "세부프로필 설정하기",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = B2,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_arrow_right),
                        contentDescription = "arrow_right",
                        modifier = Modifier
                            .size(16.dp),
                    )
                }
            }


        }
    }
}