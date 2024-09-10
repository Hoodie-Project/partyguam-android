package com.party.presentation.screen.detail.choice_carrier_position

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.PRIMARY
import com.party.navigation.Screens
import com.party.presentation.screen.detail.DetailProfileNextButton

@Composable
fun ChoiceCarrierPositionScreen(
    navController: NavController,
) {
    var selectedCarrier by remember {
        mutableStateOf("")
    }

    var selectedPosition by remember {
        mutableStateOf("")
    }

    var selectedDetailPosition by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            SelectCarrierArea(
                title = stringResource(id = R.string.choice_carrier_position1),
                list = carrierList,
                selectedCarrier = selectedCarrier,
                onSelect = {
                    selectedCarrier = it
                },
            )
            HeightSpacer(heightDp = 20.dp)
            SelectPositionArea(
                title = stringResource(id = R.string.choice_carrier_position2),
                list = positionList,
                selectedPosition = selectedPosition,
                onSelect = {
                    selectedPosition = it
                },
            )
            HeightSpacer(heightDp = 20.dp)
            DetailPositionArea(
                selectedPosition = selectedPosition,
                selectedDetailPosition = selectedDetailPosition,
                onSelect = {
                    selectedDetailPosition = it
                },
            )
        }

        HeightSpacer(heightDp = 12.dp)

        DetailProfileNextButton(
            navController = navController,
            routeScreens = null,
            text = stringResource(id = R.string.choice_carrier_position3),
            textColor = nextButtonContainerAndTextColor(selectedCarrier, selectedPosition, selectedDetailPosition).second,
            containerColor = nextButtonContainerAndTextColor(selectedCarrier, selectedPosition, selectedDetailPosition).first,
            onBackPressed = {
                navController.popBackStack()
            }
        )
    }
}

fun nextButtonContainerAndTextColor(
    selectedCarrier: String,
    selectedPosition: String,
    selectedDetailPosition: String,
) = if(selectedCarrier.isNotEmpty() && selectedPosition.isNotEmpty() && selectedDetailPosition.isNotEmpty()) PRIMARY to BLACK else LIGHT200 to GRAY400