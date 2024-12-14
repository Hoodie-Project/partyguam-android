package com.party.presentation.screen.party_user_manage.component

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
fun PartyUserScaffoldArea(
    onNavigationBack: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = { onNavigationBack() }
            )
        },
        title = {
            Text(
                text = "파티원 관리",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
        actionIcons = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_menu),
                iconColor = BLACK,
                iconSize = 32.dp,
                contentDescription = "",
                onClick = {  }
            )
        }
    )
}