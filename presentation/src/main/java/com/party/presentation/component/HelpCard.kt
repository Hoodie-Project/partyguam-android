package com.party.presentation.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun HelpCard(
    title: String = "내용을 추천드려요.",
    description1: String,
    description2: String,
    onClose: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
        colors = CardDefaults.cardColors(
            containerColor = GRAY100
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            HelpCardTitle(
                title = title,
                onClose = onClose
            )
            TextComponent(
                text = description1,
                fontSize = B2,
                textColor = GRAY600
            )
            TextComponent(
                text = description2,
                fontSize = B2,
                textColor = GRAY600
            )
        }
    }
}

@Composable
private fun HelpCardTitle(
    title: String,
    onClose: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Chip(
                modifier = Modifier
                    .width(38.dp)
                    .height(20.dp),
                text = "TIP",
                containerColor = PRIMARY,
                contentColor = WHITE,
            )
            WidthSpacer(widthDp = 6.dp)
            TextComponent(
                text = title,
                fontSize = B2,
                textColor = BLACK,
                fontWeight = FontWeight.Bold,
            )
        }
        DrawableIconButton(
            icon = painterResource(id = R.drawable.icon_close2),
            contentDescription = "close",
            iconColor = GRAY500,
            onClick = { onClose() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HelpCardPreview() {
    HelpCard(
        title = "내용을 추천드려요",
        description1 = "어떤 활동을 하나요?",
        description2 = "규칙이 있나요? (출석, 강퇴 등)",
        onClose = {  }
    )
}