package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.ui.theme.GRAY100
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyType

@Composable
fun PartyDetailArea(
    partyDetailTabList: List<String>,
    partyDetail: PartyDetail,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        PartyDetailImageArea(
            image = partyDetail.image
        )

        HeightSpacer(heightDp = 20.dp)
        PartyDetailCategoryArea(
            tag = partyDetail.tag,
            partyType = partyDetail.partyType.type
        )

        HeightSpacer(heightDp = 12.dp)
        PartyDetailTitleArea(
            title = partyDetail.title
        )
        HeightSpacer(heightDp = 32.dp)
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = GRAY100,
            thickness = 12.dp
        )
        PartyDetailTabArea(
            partyDetailTabList = partyDetailTabList,
            selectedTabText = selectedTabText,
            onTabClick = {onTabClick(it)}
        )
        HeightSpacer(heightDp = 24.dp)
        when (selectedTabText) {
            partyDetailTabList[0] -> {
                PartyDetailDescriptionArea(
                    content = partyDetail.content
                )
            }
        }
        HeightSpacer(heightDp = 60.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun PartyDetailAreaPreview() {
    val partyDetail = PartyDetail(
        id = 5686,
        partyType = PartyType(id = 9282, type = "포트폴리오"),
        tag = "모집중",
        title = "파티 제목 입니다~~~~~~~",
        content = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다!. 새로운 프로젝트를 위해 모여 함께 아이디어를 나눕시다!",
        image = "viris",
        status = "error",
        createdAt = "recteque",
        updatedAt = "venenatis"
    )

    PartyDetailArea(
        partyDetailTabList = listOf("홈", "파티원", "모집공고"),
        partyDetail = partyDetail,
        selectedTabText = "홈",
        onTabClick = {}
    )
}