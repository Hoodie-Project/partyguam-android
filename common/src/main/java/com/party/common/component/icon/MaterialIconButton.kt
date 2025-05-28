package com.party.common.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.party.guam.design.BLACK

@Composable
fun MaterialIconButton(
    modifier: Modifier = Modifier,
    iconSize: Dp = 20.dp,
    iconColor: Color = BLACK,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = { onClick() },
    ) {
        Icon(
            modifier = modifier.size(iconSize),
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor
        )
    }
}