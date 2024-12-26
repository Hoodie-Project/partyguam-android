package com.party.presentation.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.LOGO_COLOR_END
import com.party.common.ui.theme.LOGO_COLOR_START

@Composable
fun HomeTopBar(
    onGoToSearch: () -> Unit,
    onGoToAlarm: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoText()
        HomeTopBarIconArea(
            onGoToSearch = onGoToSearch,
            onGoToAlarm = onGoToAlarm
        )
    }
}

@Composable
fun LogoText() {
    Text(
        text = stringResource(id = R.string.home1),
        fontSize = 24.sp,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(LOGO_COLOR_START, LOGO_COLOR_END),
            )
        )
    )
}

@Composable
fun HomeTopBarIconArea(
    onGoToSearch: () -> Unit,
    onGoToAlarm: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DrawableIconButton(
            modifier = Modifier.size(24.dp),
            icon = painterResource(id = R.drawable.icon_search),
            contentDescription = "",
            onClick = onGoToSearch,
            iconColor = BLACK
        )
        WidthSpacer(widthDp = 12.dp)
        DrawableIconButton(
            modifier = Modifier.size(24.dp),
            icon = painterResource(id = R.drawable.icon_alarm),
            contentDescription = "",
            onClick = onGoToAlarm,
            iconColor = BLACK
        )
    }
}