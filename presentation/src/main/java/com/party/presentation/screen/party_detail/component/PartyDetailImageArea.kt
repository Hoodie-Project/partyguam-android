package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.ImageLoading

@Composable
fun PartyDetailImageArea(
    image: String,
) {
    ImageLoading(
        modifier = Modifier
            .fillMaxWidth()
            .height(281.dp),
        imageUrl = image,
        roundedCornerShape = 0.dp
    )
}

@Preview
@Composable
fun PartyDetailImageAreaPreview() {
    PartyDetailImageArea(
        image = "https://images.unasdadssplash.com/photo-1632210004114-3b3b3b3b3b3b"
    )
}