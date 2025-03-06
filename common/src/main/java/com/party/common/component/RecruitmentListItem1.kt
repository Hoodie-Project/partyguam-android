package com.party.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT
import com.party.common.ui.theme.WHITE

@Composable
fun RecruitmentListItem1(
    imageUrl: String? = null,
    category: String,
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .height(315.dp)
                .padding(12.dp),
        ) {
            RecruitmentImageArea(
                imageUrl = imageUrl,
            )
            HeightSpacer(heightDp = 12.dp)
            RecruitmentInfoArea(
                category = category,
                title = title,
                main = main,
                sub = sub,
                recruitingCount = recruitingCount,
                recruitedCount = recruitedCount,
            )
        }
    }
}

@Composable
private fun RecruitmentImageArea(
    imageUrl: String? = null,
) {
    ImageLoading(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        imageUrl = imageUrl,
        roundedCornerShape = MEDIUM_CORNER_SIZE
    )
}

@Composable
private fun RecruitmentInfoArea(
    category: String,
    title: String,
    main: String,
    sub: String,
    recruitingCount: Int,
    recruitedCount: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(142.dp)
    ) {
        RecruitmentCategory(category = category)
        HeightSpacer(heightDp = 4.dp)

        RecruitmentTitle(title = title)
        HeightSpacer(heightDp = 4.dp)

        MainAndSubPosition(
            modifier = Modifier
                .height(20.dp),
            main = main,
            sub = sub,
        )

        HeightSpacer(heightDp = 12.dp)
        RecruitmentCountArea(
            modifier = Modifier
                .height(20.dp),
            recruitingCount = recruitingCount,
            recruitedCount = recruitedCount,
        )
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
private fun RecruitmentTitle(
    title: String,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        text = title,
        fontSize = T3,
        fontWeight = FontWeight.Bold,
        align = Alignment.TopStart,
    )
}

@Composable
private fun MainAndSubPosition(
    modifier: Modifier,
    main: String,
    sub: String,
    textColor: Color = BLACK,
) {
    TextComponent(
        modifier = modifier
            .fillMaxWidth(),
        text = "$main | $sub",
        fontSize = B2,
        textColor = textColor,
    )
}

@Composable
private fun RecruitmentCountArea(
    modifier: Modifier,
    recruitingCount: Int,
    recruitedCount: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.End,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        TextComponent(
            text = stringResource(id = R.string.home_common),
            fontSize = B3,
        )

        WidthSpacer(widthDp = 4.dp)

        TextComponent(
            text = "$recruitedCount / $recruitingCount",
            fontSize = B3,
            textColor = RED,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem1Preview(
    modifier: Modifier = Modifier
) {
    RecruitmentListItem1(
        imageUrl = "https://picsum.photos/200/300",
        category = "카테고리",
        title = "안드로이드 개발자를 모집합니다.",
        main = "개발자",
        sub = "안드로이드",
        recruitingCount = 1,
        recruitedCount = 0,
        onClick = {}
    )
}