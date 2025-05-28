package com.party.presentation.screen.reports.component

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
fun ReportsButton(
    inputReason: String,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = Modifier
            .height(LARGE_BUTTON_HEIGHT),
        buttonText = "신고하기",
        textWeight = FontWeight.Bold,
        containerColor = if (inputReason.isNotEmpty()) PRIMARY else LIGHT400,
        contentColor = if (inputReason.isNotEmpty()) BLACK else GRAY400,
        borderColor = if (inputReason.isNotEmpty()) PRIMARY else LIGHT200,
        onClick = onClick
    )
}