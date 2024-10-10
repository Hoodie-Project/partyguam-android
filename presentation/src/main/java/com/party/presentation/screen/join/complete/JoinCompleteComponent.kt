package com.party.presentation.screen.join.complete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.WidthSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.common.R
import com.party.presentation.screen.join.JoinScreenButton

@Composable
fun RowButtonArea(
    navController: NavHostController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        JoinScreenButton(
            modifier = Modifier.weight(0.5f),
            buttonText = stringResource(id = R.string.join_complete2),
            buttonTextColor = BLACK,
            buttonContainerColor = WHITE,
            buttonBorderColor = PRIMARY,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            onClick = { navController.navigate(Screens.Home) }
        )

        WidthSpacer(widthDp = 8.dp)

        JoinScreenButton(
            modifier = Modifier.weight(0.5f),
            buttonText = stringResource(id = R.string.join_complete3),
            buttonTextColor = BLACK ,
            buttonContainerColor = PRIMARY,
            buttonBorderColor = PRIMARY,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
            onClick = { navController.navigate(Screens.DetailProfile) }
        )
    }
}

@Preview
@Composable
fun RowButtonAreaPreview() {
    RowButtonArea(navController = rememberNavController())
}