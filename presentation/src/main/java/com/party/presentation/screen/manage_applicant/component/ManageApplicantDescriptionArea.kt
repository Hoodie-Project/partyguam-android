package com.party.presentation.screen.manage_applicant.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.T2

@Composable
fun ManageApplicantDescriptionArea(
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        TextComponent(
            modifier = Modifier
                .wrapContentWidth(),
            text = title,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
        )
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
private fun ManageApplicantDescriptionAreaPreview() {
    ManageApplicantDescriptionArea(
        title = "모집 공고 별 지원자",
        description = "지원자 관리를 원하는 모집 공고를 선택해주세요."
    )
}