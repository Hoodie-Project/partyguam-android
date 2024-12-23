package com.party.common.component.dialog.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.HeightSpacer
import com.party.common.TextComponent

@Composable
fun DialogDescription(
    modifier: Modifier = Modifier,
    description: String,
) {
    HeightSpacer(heightDp = 24.dp)

    TextComponent(
        modifier = modifier
            .fillMaxWidth(),
        text = description,
        fontSize = 16.sp,
        textAlign = Alignment.Center
    )
}