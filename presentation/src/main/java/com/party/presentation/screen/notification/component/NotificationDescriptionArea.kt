package com.party.presentation.screen.notification.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.guam.design.B2
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY500

@Composable
fun NotificationDescriptionArea() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(GRAY100),
    ) {
        WidthSpacer(widthDp = 20.dp)
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(GRAY100),
            text = "최근 14일 동안의 알림만 확인 가능합니다.",
            fontSize = B2,
            textColor = GRAY500
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun NotificationDescriptionAreaPreview() {
    NotificationDescriptionArea()
}