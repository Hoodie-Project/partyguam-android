package com.party.presentation.screen.profile_edit_tendency.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.COMPONENT_AREA_HEIGHT
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.domain.model.user.detail.PersonalityListOption

@Composable
fun SelectTendencyItem(
    item: PersonalityListOption,
    containerColor: Color,
    fontWeight: FontWeight,
    iconColor: Color,
    textColor: Color,
    onSelect: (PersonalityListOption) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(COMPONENT_AREA_HEIGHT)
            .noRippleClickable {
                onSelect(item)
            },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "check",
                    tint = iconColor,
                    modifier = Modifier
                        .size(20.dp)
                )
                WidthSpacer(widthDp = 6.dp)
                Text(
                    text = item.content,
                    fontWeight = fontWeight,
                    fontSize = T3,
                    color = textColor
                )
            }
        }
    }
}