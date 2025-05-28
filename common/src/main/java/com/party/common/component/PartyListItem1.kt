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
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.guam.design.B3
import com.party.guam.design.GRAY100
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.MEDIUM_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.T3
import com.party.guam.design.TYPE_COLOR_BACKGROUND
import com.party.guam.design.TYPE_COLOR_TEXT
import com.party.guam.design.WHITE

@Composable
fun PartyListItem1(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    type: String,
    title: String,
    recruitmentCount: Int,
    typeChip: @Composable (() -> Unit) = {},
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 2.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY100),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
                .width(224.dp)
                .height(295.dp)
                .padding(12.dp),
        ) {
            PartyItemImageArea(
                imageUrl = imageUrl,
            )
            HeightSpacer(heightDp = 12.dp)
            PartyItemInfoArea(
                modifier = modifier,
                type = type,
                title = title,
                recruitmentCount = recruitmentCount,
                typeChip = typeChip,
                onClick = onClick,
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
    modifier: Modifier,
    type: String,
    title: String,
    recruitmentCount: Int,
    onClick: () -> Unit,
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
            WidthSpacer(widthDp = 4.dp)
            PartyItemCategory(
                category = type,
            )
        }

        HeightSpacer(heightDp = 4.dp)
        PartyItemTitle(
            modifier = modifier,
            title = title,
            onClick = onClick
        )
        HeightSpacer(heightDp = 12.dp)
        PartyItemRecruiting(
            modifier = modifier,
            recruitmentCount = recruitmentCount,
            onClick = onClick
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
    modifier: Modifier,
    title: String,
    onClick: () -> Unit,
) {
    TextComponent(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        text = title,
        fontSize = T3,
        fontWeight = FontWeight.Bold,
        align = Alignment.TopStart,
        onClick = onClick
    )
}

@Composable
private fun PartyItemRecruiting(
    modifier: Modifier,
    recruitmentCount: Int,
    onClick: () -> Unit,
) {
    if(recruitmentCount > 0){
        TextComponent(
            modifier = modifier
                .fillMaxWidth()
                .height(44.dp),
            text = stringResource(id = R.string.home_list_party_recruitment_count, recruitmentCount),
            fontSize = B3,
            textColor = PRIMARY,
            fontWeight = FontWeight.SemiBold,
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyListItemPreview() {
    PartyListItem1(
        imageUrl = "https://picsum.photos/200/300",
        type = "카테고리",
        title = "제목",
        recruitmentCount = 10,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyListItemPreview2() {
    PartyListItem1(
        imageUrl = "https://picsum.photos/200/300",
        type = "카테고리",
        title = "제목",
        recruitmentCount = 0,
        onClick = {}
    )
}