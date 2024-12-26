package com.party.common.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE

@Composable
fun RoundChip(
    text: String,
    spacer: Dp = 4.dp,
    painter: Painter = painterResource(id = R.drawable.icon_close2),
    iconSize: Dp = 16.dp,
    onIconClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        modifier = Modifier
            .wrapContentWidth()
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
fun PreviewRoundChip() {
    RoundChip(
        text = "Chip",
        onIconClick = {}
    )
}