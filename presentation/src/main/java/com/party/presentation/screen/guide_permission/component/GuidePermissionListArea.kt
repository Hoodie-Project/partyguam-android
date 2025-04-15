package com.party.presentation.screen.guide_permission.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY100

@Composable
fun GuidePermissionListArea() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        GuidePermissionItem()
    }
}

@Composable
private fun GuidePermissionItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        PermissionImageArea(
            painter = painterResource(R.drawable.icon_alarm),
            description = "alarm",
        )
        WidthSpacer(12.dp)

        PermissionItem(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            permissionItemTitle = "알림 (선택)",
            permissionitemDescription = "파티 모집, 마감, 참여 등 정보 안내"
        )
    }
}

@Composable
private fun PermissionImageArea(
    painter: Painter,
    description: String,
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(GRAY100),
        contentAlignment = Alignment.Center
    ){
        DrawableIcon(
            icon = painter,
            iconSize = 24.dp,
            contentDescription = description,
            tintColor = Color.Black
        )
    }
}

@Composable
private fun PermissionItem(
    modifier: Modifier,
    permissionItemTitle: String,
    permissionitemDescription: String,
) {
    Column(
        modifier = modifier
    ) {
        TextComponent(
            text = permissionItemTitle,
            fontWeight = FontWeight.SemiBold,
            fontSize = B2
        )
        HeightSpacer(4.dp)
        TextComponent(
            text = permissionitemDescription,
            fontWeight = FontWeight.Normal,
            fontSize = B2
        )
    }
}