package com.party.presentation.screen.profile_edit.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.T3
import com.party.domain.model.user.profile.UserPersonality

@Composable
fun UserTimeArea(
    userPersonalityList:  List<UserPersonality>
) {
    Row {
        userPersonalityList.forEachIndexed { _, userPersonality ->
            Chip(
                text = userPersonality.personalityOption.content,
                containerColor = LIGHT400,
                fontWeight = FontWeight.Normal,
                fontSize = T3,
            )
            WidthSpacer(widthDp = 8.dp)
        }
    }
}