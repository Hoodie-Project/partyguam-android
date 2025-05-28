package com.party.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.B3
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY100
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.RED
import com.party.guam.design.T3
import com.party.guam.design.TYPE_COLOR_BACKGROUND
import com.party.guam.design.TYPE_COLOR_TEXT
import com.party.guam.design.WHITE

@Composable
fun RecruitmentListItem2(
    id: Int,
    partyId: Int,
    imageUrl: String? = null,
    category: String,
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
    onClick: (Int, Int) -> Unit,
) {
    Card(
        onClick = { onClick(id, partyId) },
        modifier = Modifier
            .fillMaxWidth()
            .height(162.dp)
            .padding(bottom = 2.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            RecruitmentCategory(category = category)
            HeightSpacer(heightDp = 8.dp)
            RecruitmentInfoArea(
                imageUrl = imageUrl,
                title = title,
                main = main,
                sub = sub,
                recruitingCount = recruitingCount,
                recruitedCount = recruitedCount,
                onClick = { onClick(id, partyId)},
            )
        }
    }
}

@Composable
private fun RecruitmentCategory(
    category: String,
) {
    Chip(
        containerColor = TYPE_COLOR_BACKGROUND,
        contentColor = TYPE_COLOR_TEXT,
        text = category,
    )
}

@Composable
private fun RecruitmentInfoArea(
    imageUrl: String?,
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .height(90.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RecruitmentImage(
            imageUrl = imageUrl,
        )
        WidthSpacer(widthDp = 12.dp)
        RecruitmentContent(
            title = title,
            main = main,
            sub = sub,
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
            onClick = onClick,
        )
    }
}

@Composable
private fun RecruitmentImage(
    imageUrl: String?,
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
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(195.dp)
            .height(90.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            RecruitmentTitle(
                title = title,
                onClick = onClick,
            )
            HeightSpacer(heightDp = 5.dp)
            RecruitmentPositionArea(
                modifier = Modifier
                    .height(20.dp),
                main = main,
                sub = sub,
                onClick = onClick,
            )
        }

        HeightSpacer(heightDp = 5.dp)
        RecruitmentCountArea(
            modifier = Modifier,
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
            onClick = onClick,
        )
    }
}

@Composable
private fun RecruitmentTitle(
    title: String,
    onClick: () -> Unit,
) {
    TextComponent(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp),
        text = title,
        fontSize = T3,
        fontWeight = FontWeight.SemiBold,
        textColor = BLACK,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun RecruitmentPositionArea(
    modifier: Modifier,
    main: String,
    sub: String,
    textColor: Color = BLACK,
    onClick: () -> Unit,
) {
    TextComponent(
        modifier = modifier
            .fillMaxWidth(),
        text = "$main | $sub",
        fontSize = B2,
        textColor = textColor,
        onClick = onClick,
    )
}

@Composable
private fun RecruitmentCountArea(
    modifier: Modifier,
    recruitingCount: Int,
    recruitedCount: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable {
                onClick
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        TextComponent(
            text = stringResource(id = R.string.home_common),
            fontSize = B3,
            onClick = onClick,
        )

        WidthSpacer(widthDp = 4.dp)

        TextComponent(
            text = "$recruitedCount / $recruitingCount",
            fontSize = B3,
            textColor = RED,
            onClick = onClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem2Preview(modifier: Modifier = Modifier) {
    RecruitmentListItem2(
        id = 1,
        partyId = 1,
        category = "포트폴리오",
        title = "같이할 사람 구해요같이할 사람 구해요",
        main = "개발자",
        sub = "안드로이드",
        recruitingCount = 1,
        recruitedCount = 0,
        onClick = { _, _ -> }
    )
}