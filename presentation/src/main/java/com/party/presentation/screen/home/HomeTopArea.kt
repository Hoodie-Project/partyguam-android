package com.party.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.ICON_SIZE
import com.party.common.ui.theme.LOGO_COLOR_END
import com.party.common.ui.theme.LOGO_COLOR_START

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoText()
        HomeTopBarIconArea()
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
fun HomeTopBarIconArea() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(ICON_SIZE),
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "search",
            tint = BLACK,
        )
        WidthSpacer(widthDp = 12.dp)
        Icon(
            modifier = Modifier.size(ICON_SIZE),
            painter = painterResource(id = R.drawable.alarm_icon),
            contentDescription = "notification",
            tint = BLACK,
        )
    }
}