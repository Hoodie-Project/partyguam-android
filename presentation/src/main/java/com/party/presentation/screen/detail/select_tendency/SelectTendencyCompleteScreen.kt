package com.party.presentation.screen.detail.select_tendency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T1
import com.party.common.ui.theme.WHITE
import com.party.common.Screens

@Composable
fun SelectTendencyCompleteScreen(
    navController: NavController,
) {
    SelectTendencyCompleteScreenContent(
        onGoProfile = { navController.navigate(Screens.Profile) },
        onGoHome = { navController.navigate(Screens.Home) }
    )
}

@Composable
fun SelectTendencyCompleteScreenContent(
    onGoProfile: () -> Unit,
    onGoHome: () -> Unit,
) {
    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
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

            FinishButtonArea(
                onGoProfile = onGoProfile,
                onGoHome = onGoHome,
            )

            HeightSpacer(heightDp = 12.dp)
        }
    }

}

@Composable
fun FinishButtonArea(
    onGoProfile: () -> Unit,
    onGoHome: () -> Unit,
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
            onClick = onGoProfile
        )

        WidthSpacer(widthDp = 8.dp)

        TendencyNextButton(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.common4),
            textColor = BLACK,
            containerColor = PRIMARY,
            borderColor = PRIMARY,
            onClick = onGoHome
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectTendencyCompleteScreenContentPreview() {
    SelectTendencyCompleteScreenContent(
        onGoProfile = {},
        onGoHome = {}
    )
}