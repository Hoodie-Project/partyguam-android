package com.party.presentation.screen.profile_edit_tendency.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.icon.DrawableIcon
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.PRIMARY

@Composable
fun ResetTextAndIconArea(
    onReset: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = "초기화",
            fontSize = B2,
            textColor = PRIMARY,
            onClick = onReset
        )
        DrawableIcon(
            icon = painterResource(id = R.drawable.icon_reload),
            contentDescription = "reload",
            iconSize = 16.dp,
            tintColor = PRIMARY,
            modifier = Modifier.noRippleClickable { onReset() }
        )
    }
}