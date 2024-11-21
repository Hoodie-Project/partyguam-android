package com.party.presentation.screen.party_detail.tab.recruitment.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2

@Composable
fun PartyDetailRecruitmentTitle(
    listSize: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = "모집공고",
            fontSize = T2,
            fontWeight = FontWeight.Bold,
        )
        WidthSpacer(widthDp = 6.dp)
        TextComponent(
            text = "$listSize",
            fontSize = T2,
            fontWeight = FontWeight.Bold,
            textColor = PRIMARY
        )
    }
}