package com.party.presentation.screen.state.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.guam.design.BLACK
import com.party.guam.design.T1

@Composable
fun StateScaffoldArea(
    onGoToSearch: () -> Unit,
    onGoToAlarm: () -> Unit
) {
    ScaffoldCenterBar(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        navigationIcon = {
            TextComponent(
                text = "활동",
                fontSize = T1,
                fontWeight = FontWeight.Bold,
            )
        },
        actionIcons = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DrawableIconButton(
                    modifier = Modifier.size(24.dp),
                    icon = painterResource(id = R.drawable.icon_search),
                    contentDescription = "search",
                    onClick = onGoToSearch,
                    iconColor = BLACK
                )
                WidthSpacer(widthDp = 12.dp)
                DrawableIconButton(
                    modifier = Modifier.size(24.dp),
                    icon = painterResource(id = R.drawable.icon_alarm),
                    contentDescription = "alarm",
                    onClick = onGoToAlarm,
                    iconColor = BLACK
                )
            }
        }
    )
}