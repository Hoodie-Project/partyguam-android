package com.party.presentation.screen.join.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.T2

@Composable
fun JoinTitleSection(
    onNavigateBack: () -> Unit = {},
    title: String = "회원가입",
    actionIcon: @Composable () -> Unit = {}
){
    ScaffoldCenterBar(
        navigationIcon = {
            Row {
                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_arrow_back),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "back",
                    onClick = { onNavigateBack() }
                )
            }

        },
        title = {
            Text(
                text = title,
                fontSize = fs(T2),
                fontWeight = FontWeight.Bold
            )
        },
        actionIcons = {
            actionIcon()
        }
    )
}
