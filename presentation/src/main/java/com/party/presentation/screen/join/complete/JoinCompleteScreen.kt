package com.party.presentation.screen.join.complete

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.T1
import com.party.common.R

@Composable
fun JoinCompleteScreen(
    navController: NavHostController,
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
                text = stringResource(id = R.string.join_complete1),
                fontWeight = FontWeight.Bold,
                fontSize = T1,
                textAlign = Alignment.Center,
            )
        }

        RowButtonArea(navController = navController)

        HeightSpacer(heightDp = 12.dp)
    }
}

@Preview
@Composable
fun JoinCompleteScreenPreview() {
    JoinCompleteScreen(navController = rememberNavController())
}