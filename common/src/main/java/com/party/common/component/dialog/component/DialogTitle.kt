package com.party.common.component.dialog.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.party.common.TextComponent

@Composable
fun DialogTitle(
    dialogTitle: String,
) {
    TextComponent(
        modifier = Modifier.fillMaxWidth(),
        text = dialogTitle,
        fontSize = 18.sp,
        align = Alignment.Center,
        fontWeight = FontWeight.Bold,
    )
}