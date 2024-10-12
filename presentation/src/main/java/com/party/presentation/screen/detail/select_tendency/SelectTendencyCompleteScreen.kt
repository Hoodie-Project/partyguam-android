package com.party.presentation.screen.detail.select_tendency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.BLACK
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
            text = stringResource(id = R.string.select_tendency9),
            textColor = BLACK,
            containerColor = WHITE,
            borderColor = PRIMARY,
            onClick = {}
        )

        WidthSpacer(widthDp = 8.dp)

        TendencyNextButton(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.common4),
            textColor = BLACK,
            containerColor = PRIMARY,
            borderColor = PRIMARY,
            onClick = {
                navController.navigate(Screens.Home)
            }
        )
    }
}