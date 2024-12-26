package com.party.presentation.screen.profile.component

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
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T1

@Composable
fun ProfileScaffoldArea(
    onGoToAlarm: () -> Unit,
    onGoSetting: () -> Unit
) {
    ScaffoldCenterBar(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        navigationIcon = {
            TextComponent(
                text = "프로필",
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
                    icon = painterResource(id = R.drawable.icon_alarm),
                    contentDescription = "alarm",
                    onClick = onGoToAlarm,
                    iconColor = BLACK
                )
                WidthSpacer(widthDp = 12.dp)
                DrawableIconButton(
                    modifier = Modifier.size(24.dp),
                    icon = painterResource(id = R.drawable.icon_setting),
                    contentDescription = "setting",
                    onClick = onGoSetting,
                    iconColor = BLACK
                )
            }
        }
    )
}