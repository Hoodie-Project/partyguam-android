package com.party.common.component.chip

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.party.guam.design.B3
import com.party.guam.design.TYPE_COLOR_BACKGROUND
import com.party.guam.design.TAG_COLOR_TEXT
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.TAG_COLOR_BACKGROUND
import com.party.guam.design.TYPE_COLOR_TEXT

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    containerColor: Color = TYPE_COLOR_BACKGROUND,
    contentColor: Color = TYPE_COLOR_TEXT,
    roundedCornerShape: Dp = LARGE_CORNER_SIZE,
    text: String,
    fontSize: TextUnit = B3,
    fontWeight: FontWeight = FontWeight.SemiBold,
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .height(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(roundedCornerShape),
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Category1Preview() {
    Chip(
        text = "포트폴리오"
    )
}

@Preview(showBackground = true)
@Composable
fun Category2Preview() {
    Chip(
        containerColor = TAG_COLOR_BACKGROUND,
        contentColor = TAG_COLOR_TEXT,
        text = "모집중"
    )
}