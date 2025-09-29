package com.party.presentation.screen.info_center.component.terms

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.GRAY600
import com.party.presentation.screen.info_center.component.DescriptionTitleArea
import com.party.presentation.screen.info_center.component.TermsContent

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