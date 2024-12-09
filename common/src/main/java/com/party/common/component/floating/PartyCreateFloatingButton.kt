package com.party.common.component.floating

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun PartyCreateFloatingButton(
    isExpandedFloatingButton: Boolean,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier,
        onClick = onClick,
        shape = CircleShape,
        containerColor = if(isExpandedFloatingButton) WHITE else PRIMARY
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = if(isExpandedFloatingButton) Icons.Rounded.Close else Icons.Rounded.Add,
            tint = if (isExpandedFloatingButton) BLACK else WHITE,
            contentDescription = "This is Expand Button",
        )
    }
}