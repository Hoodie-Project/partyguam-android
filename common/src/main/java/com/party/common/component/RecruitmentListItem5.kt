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
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.guam.design.B2
import com.party.guam.design.B3
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY500
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.common.utils.convertIsoToCustomDateFormat

@Composable
fun RecruitmentListItem5(
    status: String,
    id: Int,
    createdAt: String,
    main: String,
    sub: String,
    applicationCount: Int,
    onClick: (Int, String, String) -> Unit,
) {
    Card(
        onClick = { onClick(id, main, sub) },
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(2.dp),
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
                status = status,
                applicationCount = applicationCount
            )
        }
    }
}

@Composable
private fun ApplicantArea(
    status: String,
    applicationCount: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(status == "active"){
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
        }else {
            TextComponent(
                text = "모집마감",
                fontSize = B2,
                textColor = BLACK,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem5Preview() {
    RecruitmentListItem5(
        status = "completed",
        id = 1,
        createdAt = "2024-06-05T15:30:45.123Z",
        main = "개발자",
        sub = "안드로이드",
        applicationCount = 3,
        onClick = { _, _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem5Preview2() {
    RecruitmentListItem5(
        status = "active",
        id = 1,
        createdAt = "2024-06-05T15:30:45.123Z",
        main = "개발자",
        sub = "안드로이드",
        applicationCount = 3,
        onClick = { _, _, _ -> }
    )
}