package com.party.presentation.screen.user_delete.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.party.common.TextComponent
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T3

@Composable
fun WarningTitleArea() {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth(),
        text = "회원 탈퇴 전 꼭 읽어주세요",
        fontSize = T3,
        textColor = RED,
        textAlign = Alignment.Center,
        fontWeight = FontWeight.Bold
    )
}