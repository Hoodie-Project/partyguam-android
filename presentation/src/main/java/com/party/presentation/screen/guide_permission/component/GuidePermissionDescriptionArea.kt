package com.party.presentation.screen.guide_permission.component

import androidx.compose.runtime.Composable
import com.party.common.utils.TextComponent
import com.party.guam.design.B3
import com.party.guam.design.GRAY500

@Composable
fun GuidePermissionDescriptionArea(){
    TextComponent(
        text = "해당 기능 사용을 원하실 때만 동의를 받고 있으며, 비동의 시에도 해당 기능 외 서비스 이용이 가능해요",
        textColor = GRAY500,
        fontSize = B3
    )
}