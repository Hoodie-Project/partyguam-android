package com.party.common.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.party.common.ui.theme.GRAY400

@Composable
fun DrawableIconButton(
    modifier: Modifier = Modifier,
    iconSize: Dp = 20.dp,
    iconColor: Color = GRAY400,
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            modifier = modifier.size(iconSize),
            painter = icon,
            contentDescription = contentDescription,
            tint = iconColor
        )
    }
}

@Composable
fun DrawableIcon(
    modifier: Modifier = Modifier,
    icon: Painter,
    iconSize: Dp = 20.dp,
    tintColor: Color = GRAY400,
    contentDescription: String,
) {
    Icon(
        modifier = modifier.size(iconSize),
        painter = icon,
        contentDescription = contentDescription,
        tint = tintColor
    )
}
