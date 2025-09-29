package com.party.presentation.screen.info_center.component.terms

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.GRAY600
import com.party.presentation.screen.info_center.component.DescriptionTitleArea

@Composable
fun TermsAreaItem(
    title: String,
    content: List<String>,
) {
    Column {
        DescriptionTitleArea(
            title = title
        )
        HeightSpacer(heightDp = 20.dp)
        content.forEachIndexed { index, s ->
            TextComponent(
                text = s,
                fontSize = B2,
                textColor = GRAY600,
            )
        }
    }
}