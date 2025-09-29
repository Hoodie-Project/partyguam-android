package com.party.presentation.screen.info_center.component.privacy_policy

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY600
import com.party.presentation.screen.info_center.component.DescriptionTitleArea

@Composable
fun PrivacyPolicyItem(
    title: String,
    content: List<Pair<String, List<String>>>
) {
    Column {
        DescriptionTitleArea(
            title = title
        )
        HeightSpacer(heightDp = 20.dp)
        content.forEachIndexed { index, pair ->
            if(pair.first.isNotEmpty()){
                Column {
                    TextComponent(
                        text = pair.first,
                        fontSize = B2,
                        textColor = BLACK,
                        fontWeight = FontWeight.SemiBold,
                    )
                    HeightSpacer(heightDp = 4.dp)
                    pair.second.forEachIndexed { index, s ->
                        TextComponent(
                            text = s,
                            fontSize = B2,
                            textColor = GRAY600,
                        )
                    }
                    HeightSpacer(heightDp = 4.dp)
                }
            }else {
                TextComponent(
                    text = pair.second[0],
                    fontSize = B2,
                    textColor = GRAY600,
                )
                //HeightSpacer(heightDp = 20.dp)
            }
        }
    }
}