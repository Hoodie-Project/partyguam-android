package com.party.presentation.screen.guide_permission.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.party.common.utils.TextComponent
import com.party.guam.design.T2

@Composable
fun GuidePermissionTitleArea() {
    TextComponent(
        text = "다양한 서비스 제공을 위해\n권한을 허용해 주세요.",
        fontSize = T2,
        fontWeight = FontWeight.Bold
    )
}