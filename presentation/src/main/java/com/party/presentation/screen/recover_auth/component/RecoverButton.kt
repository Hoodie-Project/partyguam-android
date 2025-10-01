package com.party.presentation.screen.recover_auth.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.party.common.component.button.CustomButton
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.PRIMARY

@Composable
fun RecoverButton(
    onClick: () -> Unit,
) {
    CustomButton(
        onClick = onClick,
        buttonText = "계정 복구",
        textWeight = FontWeight.Bold,
        textSize = fs(B2),
        containerColor = PRIMARY,
        borderColor = PRIMARY,
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT)
    )
}