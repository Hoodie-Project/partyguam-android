package com.party.presentation.screen.party_edit.component

import androidx.compose.runtime.Composable
import com.party.common.component.custom_tooltip.CustomTooltip

@Composable
fun PartyEditCustomShape(
    onClick: () -> Unit,
) {
    CustomTooltip(
        onClick = onClick
    )
}