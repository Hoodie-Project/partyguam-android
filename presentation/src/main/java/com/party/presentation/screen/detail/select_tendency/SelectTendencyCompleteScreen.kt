package com.party.presentation.screen.detail.select_tendency

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T1
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens

@Composable
fun SelectTendencyCompleteScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            TextComponent(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(id = R.string.select_tendency8),
                fontWeight = FontWeight.Bold,
                fontSize = T1,
                textAlign = Alignment.Center,
            )
        }

        FinishButtonArea(navController = navController)

        HeightSpacer(heightDp = 12.dp)
    }
}

@Composable
fun FinishButtonArea(
    navController: NavController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TendencyNextButton(
            modifier = Modifier.weight(0.5f),
            navController = navController,
            routeScreens = Screens.SelectTendency2,
            text = stringResource(id = R.string.select_tendency9),
            textColor = BLACK,
            containerColor = WHITE,
            borderColor = PRIMARY
        )

        WidthSpacer(widthDp = 8.dp)

        TendencyNextButton(
            modifier = Modifier.weight(0.5f),
            navController = navController,
            routeScreens = Screens.SelectTendency2,
            text = stringResource(id = R.string.common4),
            textColor = BLACK,
            containerColor = PRIMARY,
            borderColor = PRIMARY
        )
    }
}


@Preview
@Composable
fun SelectTendencyCompleteScreenPreview() {
    SelectTendencyCompleteScreen(navController = rememberNavController())
}

@Preview
@Composable
fun FinishButtonAreaPreview() {
    FinishButtonArea(navController = rememberNavController())
}