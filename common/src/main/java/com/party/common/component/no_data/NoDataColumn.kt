package com.party.common.component.no_data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.GRAY500

@Composable
fun NoDataColumn(
    modifier: Modifier = Modifier,
    title: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DrawableIcon(
                icon = painterResource(id = R.drawable.icon_info),
                contentDescription = "info",
                modifier = Modifier
                    .size(24.dp),
                tintColor = GRAY500
            )
            HeightSpacer(heightDp = 6.dp)
            TextComponent(
                text = title,
                fontSize = B1,
                fontWeight = FontWeight.SemiBold,
                textColor = GRAY500,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoDataColumnPreview() {
    NoDataColumn(
        title = "파티가 없어요."
    )
}