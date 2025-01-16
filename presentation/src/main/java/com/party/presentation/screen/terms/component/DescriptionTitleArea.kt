package com.party.presentation.screen.terms.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.party.common.TextComponent
import com.party.common.ui.theme.B1

@Composable
fun DescriptionTitleArea(
    title: String,
) {
    TextComponent(
        text = title,
        fontSize = B1,
        fontWeight = FontWeight.SemiBold,
    )
}