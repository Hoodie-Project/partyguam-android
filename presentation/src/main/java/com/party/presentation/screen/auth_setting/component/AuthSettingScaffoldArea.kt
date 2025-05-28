package com.party.presentation.screen.auth_setting.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.guam.design.BLACK
import com.party.guam.design.T2

@Composable
fun AuthSettingScaffoldArea(
    onNavigationBack: () -> Unit
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = onNavigationBack
            )
        },
        title = {
            Text(
                text = "계정 설정",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
    )
}