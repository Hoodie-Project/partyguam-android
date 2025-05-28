package com.party.common.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY500

@Composable
fun RoundChip(
    text: String,
    spacer: Dp = 4.dp,
    painter: Painter = painterResource(id = R.drawable.icon_close2),
    iconSize: Dp = 16.dp,
    onClickCard: (String) -> Unit,
    onIconClick: () -> Unit,
) {
    Card(
        onClick = { onClickCard(text) },
        shape = RoundedCornerShape(999.dp),
        modifier = Modifier
            .widthIn(min = 60.dp)
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = GRAY100
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            TextComponent(
                text = text,
                fontSize = B2,
                textColor = BLACK,
                onClick = { onClickCard(text) },
            )
            WidthSpacer(widthDp = spacer)
            Icon(
                painter = painter,
                contentDescription = "",
                tint = GRAY500,
                modifier = Modifier
                    .size(iconSize)
                    .noRippleClickable { onIconClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRoundChip() {
    RoundChip(
        text = "파티모집",
        onIconClick = {},
        onClickCard = {}
    )
}