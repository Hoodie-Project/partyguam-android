package com.party.presentation.screen.detail.select_tendency.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2

@Composable
fun SelectTendencyScaffoldArea(
    onNavigationClick: () -> Unit,
    onClose: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = onNavigationClick
            )
        },
        title = {
            Text(
                text = "세부프로필",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
        actionIcons = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_close2),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = onClose
            )
        }
    )
}