package com.party.presentation.screen.detail.detail_profile.component

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
fun DetailProfileScaffoldArea(
    onNavigationClick: () -> Unit,
) {
    ScaffoldCenterBar(
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
                onClick = { onNavigationClick() }
            )
        }
    )
}