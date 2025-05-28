package com.party.guam.design

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography

val customTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

fun getFontFamily(): FontFamily {
    return FontFamily(
        Font(R.font.suit_regular, FontWeight.Normal),
    )
}