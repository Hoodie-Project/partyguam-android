package com.party.common.component.dialog.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.party.guam.design.BLACK

@Composable
fun DialogButton(
    modifier: Modifier,
    buttonText: String,
    buttonTextColor: Color = BLACK,
    containerColor: Color,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = buttonTextColor
        ),
        shape = RectangleShape
    ) {
        Text(
            text = buttonText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}