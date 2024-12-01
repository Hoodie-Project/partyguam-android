package com.party.common.component.no_data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600

@Composable
fun NoDataRow(
    modifier: Modifier = Modifier,
    text: String,
    spacer: Dp = 4.dp,
    iconColor: Color = GRAY500,
    textColor: Color = GRAY600
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            DrawableIcon(
                icon = painterResource(id = R.drawable.info),
                contentDescription = "info",
                modifier = Modifier
                    .size(16.dp),
                tintColor = iconColor
            )
            WidthSpacer(widthDp = spacer)
            TextComponent(
                text = text,
                fontSize = B1,
                textColor = textColor,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun NoDataRowPreview(modifier: Modifier = Modifier) {
    NoDataRow(
        text = "파티가 없어요."
    )
}