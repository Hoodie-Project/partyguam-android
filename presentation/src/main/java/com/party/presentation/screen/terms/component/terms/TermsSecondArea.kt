package com.party.presentation.screen.terms.component.terms

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY600
import com.party.presentation.screen.terms.component.DescriptionTitleArea
import com.party.presentation.screen.terms.component.TermsContent

@Composable
fun TermsSecondArea() {
    Column {
        DescriptionTitleArea(
            title = TermsContent.SECOND.title
        )
        HeightSpacer(heightDp = 20.dp)
        TermsContent.SECOND.description.forEachIndexed { index, s ->
            if(index == 1){
                HeightSpacer(heightDp = 20.dp)
            }
            TextComponent(
                text = s,
                fontSize = B2,
                textColor = GRAY600,
            )
        }
    }
}