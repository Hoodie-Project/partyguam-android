package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2

@Composable
fun PartyDetailTitleArea(
    modifier: Modifier = Modifier,
    title: String,
    number: String = ""
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = title,
            fontSize = T2,
            fontWeight = FontWeight.Bold,
        )
        WidthSpacer(widthDp = 6.dp)
        TextComponent(
            text = number,
            fontSize = T2,
            fontWeight = FontWeight.Bold,
            textColor = PRIMARY
        )
    }
}

@Preview
@Composable
fun PartyDetailTitleAreaPreview() {
    PartyDetailTitleArea(
        modifier = Modifier,
        title = "파티의 제목입니다."
    )
}