package com.party.common.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.guam.design.B2
import com.party.guam.design.GRAY100
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.MEDIUM_CORNER_SIZE
import com.party.guam.design.T3
import com.party.guam.design.TYPE_COLOR_BACKGROUND
import com.party.guam.design.WHITE

@Composable
fun PartyListItem3(
    imageUrl: String? = null,
    status: String,
    type: String,
    title: String,
    main: String,
    sub: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(282.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(2.dp),
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            // 썸네일
            PartyItemImageArea(
                imageUrl = imageUrl,
            )

            // 진행중 / 포트폴리오
            HeightSpacer(heightDp = 12.dp)
            StatusAndTypeArea(
                status = status,
                type = type
            )

            // 제목
            HeightSpacer(heightDp = 4.dp)
            PartyItemTitle(
                title = title
            )

            // 포지션
            HeightSpacer(heightDp = 4.dp)
            PartyItemPositionArea(
                main = main,
                sub = sub
            )
        }
    }
}

@Composable
private fun PartyItemImageArea(
    imageUrl: String? = null,
) {
    ImageLoading(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        imageUrl = imageUrl,
        roundedCornerShape = MEDIUM_CORNER_SIZE,
    )
}

@Composable
private fun StatusAndTypeArea(
    status: String,
    type: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Chip(
            text = status,
            containerColor = TYPE_COLOR_BACKGROUND,
        )
        WidthSpacer(widthDp = 4.dp)
        Chip(
            text = type,
            containerColor = TYPE_COLOR_BACKGROUND,
        )
    }
}

@Composable
private fun PartyItemTitle(
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
private fun PartyItemPositionArea(
    main: String,
    sub: String,
) {
    TextComponent(
        text = "$main | $sub",
        fontSize = B2,
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyListItem3Preview() {
    PartyListItem3(
        onClick = {},
        status = "진행중",
        type = "포트폴리오",
        title = "파티 제목파티 제목파티 제목파티 제목",
        main = "개발자",
        sub = "안드로이드",
    )
}