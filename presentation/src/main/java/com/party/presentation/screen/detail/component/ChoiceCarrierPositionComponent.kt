package com.party.presentation.screen.detail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.T3

@Composable
fun SelectCarrierAndPositionComponent(
    containerColor: Color,
    borderColor: Color,
    text: String,
    fontWeight: FontWeight,
    onSelect: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT)
            .noRippleClickable { onSelect(text) },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontWeight = fontWeight,
                fontSize = fs(T3),
            )
        }
    }
}