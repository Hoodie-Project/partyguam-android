package com.party.presentation.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.BLACK

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
        HomeLogoImage()
        HomeTopBarIconArea(
            onGoToSearch = onGoToSearch,
            onGoToAlarm = onGoToAlarm
        )
    }
}

@Composable
fun HomeLogoImage() {
    Image(
        painter = painterResource(id = R.drawable.home_logo),
        contentDescription = "",
        modifier = Modifier
            .width(91.dp)
            .height(34.dp)
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