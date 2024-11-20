package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.component.ImageLoading
import com.party.domain.model.party.PartyDetail

@Composable
fun PartyDetailArea(
    partyDetail: PartyDetail,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ImageLoading(
            modifier = Modifier
                .fillMaxWidth()
                .height(281.dp),
            imageUrl = partyDetail.image,
            roundedCornerShape = 0.dp
        )
    }
}