package com.party.presentation.screen.join.email.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY500
import com.party.guam.design.T2

@Composable
fun JoinEmailScaffoldArea(
    onNavigationClick: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = { onNavigationClick() }
            )
        },
        title = {
            Text(
                text = "회원가입",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
        actionIcons = {
            Text(
                text = "1/4",
                fontSize = B2,
                color = GRAY500,
                modifier = Modifier.padding(end = 20.dp),
            )
        }
    )
}