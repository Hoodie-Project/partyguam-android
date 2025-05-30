package com.party.common.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonText: String = "지원하기",
    textSize: TextUnit = B2,
    textWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
    containerColor: Color = PRIMARY,
    contentColor: Color = BLACK,
    borderColor: Color = PRIMARY,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ){
        Text(
            text = buttonText,
            fontSize = textSize,
            fontWeight = textWeight,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomButtonPreview() {
    CustomButton(
        buttonText = "지원하기",
        onClick = {},
    )
}