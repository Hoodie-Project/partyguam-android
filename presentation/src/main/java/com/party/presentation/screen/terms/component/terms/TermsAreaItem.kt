package com.party.presentation.screen.terms.component.terms

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY600
import com.party.presentation.screen.terms.component.DescriptionTitleArea

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