package com.party.presentation.screen.party_create.component

import androidx.compose.runtime.Composable
import com.party.common.component.custom_tooltip.CustomTooltip

@Composable
fun PartyCreateCustomShape(
    onClick: () -> Unit,
) {
    CustomTooltip(
        onClick = onClick
    )
}