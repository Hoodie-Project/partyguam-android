package com.party.common.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.chip.Chip
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT
import com.party.common.ui.theme.WHITE

@Composable
fun PartyListItem1(
    imageUrl: String? = null,
    category: String,
    title: String,
    recruitmentCount: Int,
    typeChip: @Composable (() -> Unit) = {},
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
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .height(295.dp)
                .padding(12.dp),
        ) {
            PartyItemImageArea(
                imageUrl = imageUrl,
            )
            HeightSpacer(heightDp = 12.dp)
            PartyItemInfoArea(
                category = category,
                title = title,
                recruitmentCount = recruitmentCount,
                typeChip = typeChip
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
private fun PartyItemInfoArea(
    category: String,
    title: String,
    recruitmentCount: Int,
    typeChip: @Composable (() -> Unit) = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(142.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            typeChip()
            PartyItemCategory(
                category = category,
            )
        }

        HeightSpacer(heightDp = 4.dp)
        PartyItemTitle(
            title = title,
        )
        HeightSpacer(heightDp = 12.dp)
        PartyItemRecruiting(
            recruitmentCount = recruitmentCount,
        )
    }
}

@Composable
private fun PartyItemCategory(
    category: String,
) {
    Chip(
        containerColor = TYPE_COLOR_BACKGROUND,
        contentColor = TYPE_COLOR_TEXT,
        text = category,
    )
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
        textAlign = Alignment.TopStart,
    )
}

@Composable
private fun PartyItemRecruiting(
    recruitmentCount: Int,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        text = stringResource(id = R.string.home_list_party_recruitment_count, recruitmentCount),
        fontSize = B3,
        textColor = PRIMARY,
        fontWeight = FontWeight.SemiBold,
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyListItemPreview() {
    PartyListItem1(
        imageUrl = "https://picsum.photos/200/300",
        category = "카테고리",
        title = "제목",
        recruitmentCount = 10,
        onClick = {}
    )
}