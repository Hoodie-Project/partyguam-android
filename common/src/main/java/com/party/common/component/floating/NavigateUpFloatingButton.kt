package com.party.common.component.floating

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.WHITE

@Composable
fun NavigateUpFloatingButton(
    isShowNavigateUpFloatingButton: Boolean,
    isExpandedFloatingButton: Boolean,
    onClick: () -> Unit,
) {
    if(!isExpandedFloatingButton && isShowNavigateUpFloatingButton){
        FloatingActionButton(
            modifier = Modifier,
            onClick = onClick,
            shape = CircleShape,
            containerColor = WHITE
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Rounded.ArrowUpward,
                tint = GRAY500,
                contentDescription = "This is Expand Button",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigateUpFloatingButtonPreview() {
    NavigateUpFloatingButton(
        isShowNavigateUpFloatingButton = true,
        isExpandedFloatingButton = false,
        onClick = {}
    )
}