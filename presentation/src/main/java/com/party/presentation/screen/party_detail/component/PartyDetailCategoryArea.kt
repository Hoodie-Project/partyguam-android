package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.ui.theme.TAG_COLOR_BACKGROUND
import com.party.common.ui.theme.TAG_COLOR_TEXT
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT

@Composable
fun PartyDetailCategoryArea(
    tag: String,
    partyType: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Chip(
            containerColor = TAG_COLOR_BACKGROUND,
            contentColor = TAG_COLOR_TEXT,
            text = tag,
        )
        WidthSpacer(widthDp = 8.dp)
        Chip(
            containerColor = TYPE_COLOR_BACKGROUND,
            contentColor = TYPE_COLOR_TEXT,
            text = partyType,
        )
    }
}