package com.party.presentation.screen.join.complete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.guam.design.T1
import com.party.common.R
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.common.Screens

@Composable
fun JoinCompleteScreenRoute(
    navController: NavHostController,
) {
    JoinCompleteScreen(
        onGotoHome = { navController.navigate(Screens.Home) },
        onGotoDetailProfile = { navController.navigate(Screens.DetailProfile) }
    )
}

@Composable
private fun JoinCompleteScreen(
    onGotoHome: () -> Unit,
    onGotoDetailProfile: () -> Unit
) {
    Scaffold {
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
                    text = stringResource(id = R.string.join_complete1),
                    fontWeight = FontWeight.Bold,
                    fontSize = T1,
                    align = Alignment.Center,
                )
            }

            RowButtonArea(
                onGotoHome = onGotoHome,
                onGotoDetailProfile = onGotoDetailProfile
            )

            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview
@Composable
private fun JoinCompleteScreenPreview() {
    JoinCompleteScreenRoute(navController = rememberNavController())
}