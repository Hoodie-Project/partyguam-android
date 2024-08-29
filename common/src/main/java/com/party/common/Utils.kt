package com.party.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.party.common.ui.theme.T2

@Composable
fun ScaffoldTitle(
    title: String,
) {
    Text(
        text = title,
        fontSize = T2,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
    )
}