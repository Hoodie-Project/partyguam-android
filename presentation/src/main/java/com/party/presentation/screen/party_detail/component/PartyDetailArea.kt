package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PartyDetailImageArea(
            image = partyDetail.image
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
        partyType = PartyType(id = 9282, type = "inani"),
        tag = "persius",
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