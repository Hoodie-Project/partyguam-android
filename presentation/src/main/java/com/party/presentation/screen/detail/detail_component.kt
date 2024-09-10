package com.party.presentation.screen.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.navigation.Screens

@Composable
fun DetailProfileNextButton(
    navController: NavController,
    routeScreens: Screens? = null,
    onBackPressed: () -> Unit = {},
    text: String,
    textColor: Color,
    containerColor: Color,
) {
    Button(
        onClick = {
            if(routeScreens != null) navController.navigate(routeScreens)
            else onBackPressed()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
    ){
        Text(
            text = text,
            color = textColor,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
        )
    }
}