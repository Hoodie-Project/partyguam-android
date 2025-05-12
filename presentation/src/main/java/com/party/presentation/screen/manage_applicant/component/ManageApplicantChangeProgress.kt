package com.party.presentation.screen.manage_applicant.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY500

@Composable
fun ManageApplicantChangeProgress(
    isProgress: Boolean,
    onChangeProgress: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextComponent(
            text = "진행중",
            fontSize = B2,
            textColor = if(isProgress) DARK100 else GRAY500,
            fontWeight = FontWeight.SemiBold,
        )
        WidthSpacer(widthDp = 2.dp)
        Image(
            painter = if(isProgress) painterResource(id = R.drawable.icon_toggle_on) else painterResource(id = R.drawable.icon_toggle_off),
            contentDescription = "toggle",
            modifier = Modifier.noRippleClickable { onChangeProgress(!isProgress) }
        )
    }

}