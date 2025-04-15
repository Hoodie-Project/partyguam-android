package com.party.presentation.screen.guide_permission.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.component.button.CustomButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.PRIMARY

@Composable
fun GuidePermissionConfirmButton(
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        buttonText = "확인",
        textWeight = FontWeight.Bold,
        textSize = B2,
        containerColor = PRIMARY,
        contentColor = BLACK,
        onClick = onClick
    )
}