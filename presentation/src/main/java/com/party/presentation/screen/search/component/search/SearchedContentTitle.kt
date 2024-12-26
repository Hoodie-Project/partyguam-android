package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.TextComponent
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.T2

@Composable
fun SearchedContentTitle(
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextComponent(
            text = title,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold
        )

        DrawableIconButton(
            icon = painterResource(id = com.party.common.R.drawable.icon_arrow_right),
            contentDescription = "더보기",
            onClick = onClick,
            iconColor = GRAY500,
            iconSize = 24.dp
        )
    }
}