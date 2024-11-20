package com.party.presentation.screen.party_detail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.party.common.TextComponent

@Composable
fun PartyDetailTitleArea(
    title: String,
) {
    TextComponent(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Preview
@Composable
fun PartyDetailTitleAreaPreview() {
    PartyDetailTitleArea(
        title = "파티의 제목입니다."
    )
}