package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2

@Composable
fun PartyRecruitmentEditDescriptionArea(
    title: String,
    recruitmentCount: Int,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextComponent(
                modifier = Modifier
                    .wrapContentWidth(),
                text = title,
                fontSize = T2,
                fontWeight = FontWeight.SemiBold,
            )
            WidthSpacer(widthDp = 6.dp)
            TextComponent(
                modifier = Modifier
                    .wrapContentWidth(),
                text = recruitmentCount.toString(),
                fontSize = T2,
                textColor = PRIMARY,
                fontWeight = FontWeight.SemiBold,
            )
        }
        HeightSpacer(heightDp = 8.dp)
        TextComponent(
            modifier = Modifier
                .fillMaxWidth(),
            text = description,
            fontSize = B1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyRecruitmentEditDescriptionAreaPreview() {
    PartyRecruitmentEditDescriptionArea(
        title = "모집공고",
        recruitmentCount = 1,
        description = "편집을 원하는 모집 공고를 선택해 주세요.",
    )
}