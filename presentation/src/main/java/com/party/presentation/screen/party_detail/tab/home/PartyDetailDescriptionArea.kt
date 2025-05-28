package com.party.presentation.screen.party_detail.tab.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.guam.design.B1
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY600
import com.party.guam.design.T2

@Composable
fun PartyDetailDescriptionArea(
    modifier: Modifier,
    content: String,
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(bottom = 60.dp)
    ) {
        TextComponent(
            text = "파티 소개",
            fontSize = T2,
            textColor = BLACK,
            fontWeight = FontWeight.Bold
        )
        HeightSpacer(heightDp = 16.dp)
        TextComponent(
            text = content,
            fontSize = B1,
            textColor = GRAY600,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PartyDetailDescriptionAreaPreview() {
    PartyDetailDescriptionArea(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        content = "파티 소개 내용입니다.파티 소개 내용입니다.파티 소개 내용입니다.파티 소개 내용입니다.파티 소개 내용입니다."
    )
}