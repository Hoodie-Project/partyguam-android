package com.party.common.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT
import com.party.common.ui.theme.WHITE

@Composable
fun PartyListItem2(
    imageUrl: String? = null,
    status: String,
    partyType: String,
    title: String,
    main: String,
    sub: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(122.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PartyImageArea(
                imageUrl = imageUrl,
            )

            WidthSpacer(widthDp = 12.dp)
            PartyInfoArea(
                status = status,
                partyType = partyType,
                title = title,
                main = main,
                sub = sub,
            )
        }
    }
}

@Composable
private fun PartyImageArea(
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
private fun PartyInfoArea(
    status: String,
    partyType: String,
    title: String,
    main: String,
    sub: String,
) {
    Column(
        modifier = Modifier

    ) {
        PartyCategoryArea(
            status = status,
            partyType = partyType,
        )
        HeightSpacer(heightDp = 8.dp)

        PartyTitleArea(
            title = title,
        )
        HeightSpacer(heightDp = 8.dp)
        PartyPositionArea(
            main = main,
            sub = sub,
        )
    }
}

@Composable
private fun PartyCategoryArea(
    status: String,
    partyType: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Chip(
            containerColor = TYPE_COLOR_BACKGROUND,
            contentColor = TYPE_COLOR_TEXT,
            text = status,
        )
        WidthSpacer(widthDp = 4.dp)
        Chip(
            containerColor = TYPE_COLOR_BACKGROUND,
            contentColor = TYPE_COLOR_TEXT,
            text = partyType,
        )
    }
}

@Composable
private fun PartyTitleArea(
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
private fun PartyPositionArea(
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

@Preview(showBackground = true)
@Composable
fun PartyListItem2Preview() {
    PartyListItem2(
        imageUrl = "https://picsum.photos/200/300",
        status = "진행중",
        partyType = "포트폴리오",
        title = "파티제목입니다파티제목입니다파티제목입니다",
        main = "개발자",
        sub = "안드로이드",
        onClick = {}
    )
}