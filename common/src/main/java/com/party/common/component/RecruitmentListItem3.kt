package com.party.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.convertIsoToCustomDateFormat
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT
import com.party.common.ui.theme.WHITE
import com.party.common.ui.theme.YELLOW

@Composable
fun RecruitmentListItem3(
    date: String,
    partyType: String,
    title: String,
    main: String,
    sub: String,
    content: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            RecruitmentDataAndState(
                applyDate = date,
            )
            HeightSpacer(heightDp = 12.dp)
            RecruitmentInfoArea(
                partyType = partyType,
                title = title,
                main = main,
                sub = sub,
            )

            HeightSpacer(heightDp = 20.dp)
            RecruitmentContent(
                content = content
            )
        }
    }
}

@Composable
private fun RecruitmentDataAndState(
    applyDate: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(17.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextComponent(
            text = "지원일 ${convertIsoToCustomDateFormat(applyDate)}",
            fontSize = B3,
            textColor = GRAY500,
        )

        TextComponent(
            text = "응답대기",
            fontSize = B3,
            textColor = YELLOW,
        )
    }
}

@Composable
private fun RecruitmentInfoArea(
    imageUrl: String? = null,
    partyType: String,
    title: String,
    main: String,
    sub: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RecruitmentImageArea(
            imageUrl = imageUrl,
        )
        WidthSpacer(widthDp = 12.dp)
        RecruitmentContent(
            partyType = partyType,
            title = title,
            main = main,
            sub = sub,
        )
    }
}

@Composable
private fun RecruitmentImageArea(
    imageUrl: String? = null,
) {
    ImageLoading(
        modifier = Modifier
            .width(120.dp)
            .height(90.dp),
        imageUrl = imageUrl,
        roundedCornerShape = LARGE_CORNER_SIZE
    )
}

@Composable
private fun RecruitmentContent(
    partyType: String,
    title: String,
    main: String,
    sub: String,
) {
    Column{
        RecruitmentCategory(
            partyType = partyType,
        )
        HeightSpacer(heightDp = 8.dp)
        RecruitmentTitle(
            title = title,
        )
        HeightSpacer(heightDp = 8.dp)

        RecruitmentPositionArea(
            main = main,
            sub = sub,
        )
    }
}

@Composable
private fun RecruitmentCategory(
    partyType: String,
) {
    Chip(
        containerColor = TYPE_COLOR_BACKGROUND,
        contentColor = TYPE_COLOR_TEXT,
        text = partyType,
    )
}

@Composable
private fun RecruitmentTitle(
    title: String,
) {
    TextComponent(
        text = title,
        fontSize = T3,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
private fun RecruitmentPositionArea(
    main: String,
    sub: String,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth(),
        text = "$main | $sub",
        fontSize = B2,
    )
}

@Composable
private fun RecruitmentContent(
    content: String,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth(),
        text = content,
        fontSize = B2,
        textColor = GRAY600
    )
}


@Preview(showBackground = true)
@Composable
fun RecruitmentListItem3Preview() {
    RecruitmentListItem3(
        date = "2024-12-05T08:09:19.765Z",
        partyType = "스터디",
        title = "스터디 모집합니다",
        main = "대학생",
        sub = "서울",
        content = "스터디 모집합니다",
        onClick = {}
    )
}