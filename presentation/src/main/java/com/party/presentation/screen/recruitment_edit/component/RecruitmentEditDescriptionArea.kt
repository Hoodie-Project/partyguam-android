package com.party.presentation.screen.recruitment_edit.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.T2

@Composable
fun RecruitmentEditDescriptionArea(
    title: String,
    description: String,
    icon: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextComponent(
                modifier = Modifier
                    .wrapContentWidth(),
                text = title,
                fontSize = T2,
                fontWeight = FontWeight.SemiBold,
            )
            WidthSpacer(widthDp = 4.dp)
            icon()
        }
        HeightSpacer(heightDp = 8.dp)
        TextComponent(
            modifier = Modifier
                .fillMaxWidth(),
            text = description,
            fontSize = B1,
        )
    }
}