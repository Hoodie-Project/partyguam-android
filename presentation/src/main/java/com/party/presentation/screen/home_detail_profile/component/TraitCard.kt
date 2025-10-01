package com.party.presentation.screen.home_detail_profile.component

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
import com.party.common.utils.WidthSpacer
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.COMPONENT_AREA_HEIGHT
import com.party.guam.design.GRAY200
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.T3

@Composable
fun TraitCard(
    containerColor: Color,
    item: String,
    fontWeight: FontWeight,
    iconColor: Color,
    textColor: Color,
    onSelect: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(COMPONENT_AREA_HEIGHT)
            .noRippleClickable {
                onSelect()
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
                    text = item,
                    fontWeight = fontWeight,
                    fontSize = fs(T3),
                    color = textColor
                )
            }
        }
    }
}