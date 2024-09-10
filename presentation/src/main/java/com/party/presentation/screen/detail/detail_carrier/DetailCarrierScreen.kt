package com.party.presentation.screen.detail.detail_carrier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.presentation.screen.detail.detail_profile.ProfileIndicatorArea

@Composable
fun DetailCarrierScreen(
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
            ProfileIndicatorArea(
                container1 = PRIMARY,
                container2 = PRIMARY,
                container3 = GRAY100,
                textColor1 = BLACK,
                textColor2 = BLACK,
                textColor3 = GRAY400,
            )

            HeightSpacer(heightDp = 32.dp)

            TextComponent(
                text = stringResource(id = R.string.detail_carrier1),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.detail_carrier2),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 40.dp)

            PositionArea()
        }

        DetailCarrierBottomArea(
            navController = navController
        )
    }
}

@Preview
@Composable
fun PreviewDetailCarrierScreen() {
    DetailCarrierScreen(
        navController = rememberNavController()
    )
}