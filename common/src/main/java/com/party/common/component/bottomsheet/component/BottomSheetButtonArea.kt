package com.party.common.component.bottomsheet.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun BottomSheetButtonArea(
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        ResetButton(
            modifier = Modifier
                .weight(1f),
            onClick = onReset,
        )
        WidthSpacer(widthDp = 8.dp)
        ApplyButton(
            modifier = Modifier
                .weight(2f),
            onClick = onApply,
        )
    }
}

@Composable
fun ResetButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, PRIMARY),
        colors = ButtonDefaults.buttonColors(
            containerColor = WHITE,
        ),
    ) {
        Text(
            text = "초기화",
            fontWeight = FontWeight.Bold,
            fontSize = B2,
            color = BLACK,
        )
    }
}

@Composable
fun ApplyButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, PRIMARY),
        colors = ButtonDefaults.buttonColors(
            containerColor = PRIMARY,
        ),
    ) {
        Text(
            text = "적용하기",
            fontWeight = FontWeight.Bold,
            fontSize = B2,
            color = BLACK,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetButtonAreaPreview(
    modifier: Modifier = Modifier
) {
    BottomSheetButtonArea(
        onReset = {},
        onApply = {},
    )
}