package com.party.presentation.screen.detail.select_tendency

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT100
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens


@Composable
fun TendencyBottomArea(
    navController: NavController,
    routeScreens: Screens,
    buttonText: String,
    textColor: Color,
    borderColor: Color,
    containerColor: Color,
) {
    Column(
        
    ) {
        TendencyNextButton(
            navController = navController ,
            routeScreens = routeScreens,
            text = buttonText,
            textColor = textColor,
            borderColor = borderColor,
            containerColor = containerColor
        )
    }
    
}
@Composable
fun TendencyNextButton(
    navController: NavController,
    routeScreens: Screens,
    text: String,
    textColor: Color,
    containerColor: Color,
    borderColor: Color,
) {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        border = BorderStroke(1.dp, borderColor),
    ){
        Text(
            text = text,
            color = textColor,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
        )
    }

    HeightSpacer(heightDp = 20.dp)
    
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        text = stringResource(id = R.string.common3),
        fontSize = B2,
        textDecoration = TextDecoration.Underline,
        textAlign = Alignment.Center,
        textColor = GRAY500,
    )

    HeightSpacer(heightDp = 20.dp)
}
