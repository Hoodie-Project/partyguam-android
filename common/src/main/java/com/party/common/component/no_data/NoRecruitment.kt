package com.party.common.component.no_data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.icon.DrawableIcon
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.GRAY500

@Composable
fun NoRecruitment() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DrawableIcon(
                icon = painterResource(id = R.drawable.info),
                contentDescription = "info",
                modifier = Modifier
                    .size(24.dp),
                tintColor = GRAY500
            )
            HeightSpacer(heightDp = 6.dp)
            TextComponent(
                text = "모집 공고가 없어요.",
                fontSize = B1,
                fontWeight = FontWeight.SemiBold,
                textColor = GRAY500,
            )
        }
    }
}