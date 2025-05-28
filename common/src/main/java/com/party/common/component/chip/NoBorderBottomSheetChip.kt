package com.party.common.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK

@Composable
fun NoBorderBottomSheetChip(
    modifier: Modifier = Modifier,
    chipName: String,
    isSheetOpen: Boolean,
    onClick: (Boolean) -> Unit,
    spacer: Dp = 2.dp,
    painter: Painter,
    iconSize: Dp = 24.dp,
) {
    Row(
        modifier = modifier
            .noRippleClickable {
                onClick(!isSheetOpen)
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = chipName,
            color = BLACK,
            fontSize = B2
        )
        WidthSpacer(widthDp = spacer)
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painter,
            contentDescription = "Arrow Down",
            tint = BLACK,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoBorderBottomSheetChip() {
    NoBorderBottomSheetChip(
        chipName = "전체",
        isSheetOpen = false,
        onClick = {},
        painter = painterResource(id = R.drawable.icon_arrow_drop_down),
    )
}