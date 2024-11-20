package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyType

@Composable
fun PartyDetailArea(
    partyDetail: PartyDetail,
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
        content = "tellus",
        image = "viris",
        status = "error",
        createdAt = "recteque",
        updatedAt = "venenatis"
    )

    PartyDetailArea(
        partyDetail = partyDetail
    )
}