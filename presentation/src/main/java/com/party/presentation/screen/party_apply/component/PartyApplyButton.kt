package com.party.presentation.screen.party_apply.component

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.party.common.component.button.CustomButton
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.LIGHT200
import com.party.guam.design.LIGHT400
import com.party.guam.design.PRIMARY

@Composable
fun PartyApplyButton(
    inputApplyReason: String,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = Modifier
            .height(LARGE_BUTTON_HEIGHT),
        textWeight = FontWeight.Bold,
        containerColor = if (inputApplyReason.isNotEmpty()) PRIMARY else LIGHT400,
        contentColor = if (inputApplyReason.isNotEmpty()) BLACK else GRAY400,
        borderColor = if (inputApplyReason.isNotEmpty()) PRIMARY else LIGHT200,
        onClick = onClick
    )
}