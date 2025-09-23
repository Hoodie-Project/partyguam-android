package com.party.common.component.dialog.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.calculateLetterSpacing
import com.party.guam.design.B2

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
        textAlign = TextAlign.Center,
        align = Alignment.Center,
        letterSpacing = calculateLetterSpacing(16.sp, (-2.5f))
    )
}