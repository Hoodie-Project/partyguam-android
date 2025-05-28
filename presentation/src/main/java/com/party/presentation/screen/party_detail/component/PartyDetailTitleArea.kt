package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.guam.design.PRIMARY
import com.party.guam.design.T2

@Composable
fun PartyDetailTitleArea(
    modifier: Modifier = Modifier,
    title: String,
    number: String = "",
    progressContent: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
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

        progressContent()
    }
}



@Preview
@Composable
private fun PartyDetailTitleAreaPreview() {
    PartyDetailTitleArea(
        modifier = Modifier,
        title = "파티의 제목입니다.",
        number = "1",
    )
}