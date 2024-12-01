package com.party.common.component.chip

import androidx.compose.foundation.BorderStroke
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
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun BorderChip(
    modifier: Modifier = Modifier,
    borderColor: Color,
    containerColor: Color = WHITE,
    contentColor: Color,
    roundedCornerShape: Dp = LARGE_CORNER_SIZE,
    text: String,
    textColor: Color,
    fontSize: TextUnit = B2,
    fontWeight: FontWeight = FontWeight.SemiBold,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .height(36.dp)
            .noRippleClickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(roundedCornerShape),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BorderChipPreview(
    modifier: Modifier = Modifier
) {
    BorderChip(
        borderColor = PRIMARY,
        modifier = modifier,
        contentColor = Color.Black,
        text = "전체",
        textColor = GRAY500
    )
}

@Preview(showBackground = true)
@Composable
fun BorderChipPreview2(
    modifier: Modifier = Modifier
) {
    BorderChip(
        borderColor = GRAY200,
        modifier = modifier,
        contentColor = GRAY500,
        fontWeight = FontWeight.Normal,
        text = "전체",
        textColor = GRAY500
    )
}