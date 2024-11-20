package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.T2

@Composable
fun PartyDetailDescriptionArea(
    content: String,
) {
    Column(
        modifier = Modifier
    ) {
        TextComponent(
            text = "파티 소개",
            fontSize = T2,
            textColor = GRAY600,
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