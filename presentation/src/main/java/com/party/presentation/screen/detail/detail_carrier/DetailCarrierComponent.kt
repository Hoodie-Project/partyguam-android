package com.party.presentation.screen.detail.detail_carrier

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.detail.DetailProfileNextButton

@Composable
fun PositionArea(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PositionAreaComponent(
            title = stringResource(id = R.string.detail_carrier3),
            navController = navController,
        )
        HeightSpacer(heightDp = 40.dp)
        PositionAreaComponent(
            title = stringResource(id = R.string.detail_carrier4),
            navController = navController,
        )
    }
}

@Composable
fun PositionAreaComponent(
    title: String,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = title,
            fontSize = T3,
        )
        HeightSpacer(heightDp = 12.dp)
        AddCarrierButtonCard(
            navController = navController
        )
    }
}

@Composable
fun AddCarrierButtonCard(
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT)
            .noRippleClickable {
                navController.navigate(Screens.ChoiceCarrierPosition)
            },
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY200),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(12.dp),
                    tint = GRAY500
                )
                WidthSpacer(widthDp = 2.dp)
                Text(
                    text = stringResource(id = R.string.detail_carrier5),
                    color = GRAY500,
                    fontSize = B2,
                )
            }
        }
    }
}

@Composable
fun DetailCarrierBottomArea(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        DetailProfileNextButton(
            navController = navController,
            routeScreens = Screens.SelectTendency,
            text = stringResource(id = R.string.common1),
            textColor = GRAY400,
            containerColor = LIGHT400,
        )

        HeightSpacer(heightDp = 12.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(id = R.string.common3),
            fontSize = B2,
            textColor = GRAY500,
            textAlign = Alignment.Center,
            textDecoration = TextDecoration.Underline,
        )
    }
}