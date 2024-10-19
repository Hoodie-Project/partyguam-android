package com.party.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

val FLOATING_BUTTON_SIZE = 48.dp
val FLOATING_BUTTON_ICON_SIZE = 32.dp

@Composable
fun FloatingButton() {
    FloatingActionButton(
        modifier = Modifier.size(FLOATING_BUTTON_SIZE),
        onClick = { /*TODO*/ },
        contentColor = WHITE,
        containerColor = PRIMARY,
        shape = CircleShape,
    ) {
        Icon(
            modifier = Modifier.size(FLOATING_BUTTON_ICON_SIZE),
            imageVector = Icons.Rounded.Add,
            contentDescription = "add"
        )
    }
}