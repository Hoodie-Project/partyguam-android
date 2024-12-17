package com.party.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.convertIsoToCustomDateFormat
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun RecruitmentListItem5(
    id: Int,
    createdAt: String,
    main: String,
    sub: String,
    applicationCount: Int,
    onClick: (Int) -> Unit,
) {
    Card(
        onClick = { onClick(id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            TextComponent(
                text = "모집일 ${convertIsoToCustomDateFormat(createdAt)}",
                fontSize = B3,
                textColor = GRAY500
            )
            HeightSpacer(heightDp = 8.dp)
            TextComponent(
                text = "$main | $sub",
                fontSize = T3,
                fontWeight = FontWeight.SemiBold,
            )
            HeightSpacer(heightDp = 20.dp)

            ApplicantArea(
                applicationCount = applicationCount
            )
        }
    }
}

@Composable
private fun ApplicantArea(
    applicationCount: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextComponent(
            text = "지원자",
            fontSize = T3,
            textColor = BLACK,
            modifier = Modifier.alignByBaseline() // Baseline에 맞춤
        )

        WidthSpacer(4.dp)
        TextComponent(
            text = applicationCount.toString(),
            fontSize = T3,
            textColor = PRIMARY,
            modifier = Modifier.alignByBaseline() // Baseline에 맞춤
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem5Preview(modifier: Modifier = Modifier) {
    RecruitmentListItem5(
        id = 1,
        createdAt = "2024-06-05T15:30:45.123Z",
        main = "개발자",
        sub = "안드로이드",
        applicationCount = 3,
        onClick = {}
    )
}