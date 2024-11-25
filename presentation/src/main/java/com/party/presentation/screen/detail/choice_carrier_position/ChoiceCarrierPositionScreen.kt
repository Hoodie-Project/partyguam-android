package com.party.presentation.screen.detail.choice_carrier_position

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.party.common.DetailCarrierData.mainSelectedCarrier
import com.party.common.DetailCarrierData.mainSelectedDetailPosition
import com.party.common.DetailCarrierData.mainSelectedDetailPositionId
import com.party.common.DetailCarrierData.mainSelectedMainPosition
import com.party.common.DetailCarrierData.subSelectedCarrier
import com.party.common.DetailCarrierData.subSelectedDetailPosition
import com.party.common.DetailCarrierData.subSelectedDetailPositionId
import com.party.common.DetailCarrierData.subSelectedMainPosition
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.bottomsheet.list.positionList
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.PRIMARY
import com.party.presentation.screen.detail.DetailProfileNextButton
import com.party.presentation.screen.detail.detail_carrier.DetailCarrierViewModel

@Composable
fun ChoiceCarrierPositionScreen(
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    isMain: Boolean,
    detailCarrierViewModel: DetailCarrierViewModel = hiltViewModel(),
) {
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
                selectedCarrier = if(isMain) mainSelectedCarrier else subSelectedCarrier,
                onSelect = { if(isMain) mainSelectedCarrier = it else subSelectedCarrier = it },
            )
            HeightSpacer(heightDp = 20.dp)
            SelectPositionArea(
                title = stringResource(id = R.string.choice_carrier_position2),
                list = positionList,
                selectedPosition = if(isMain) mainSelectedMainPosition else subSelectedMainPosition,
                onSelect = {
                    if(isMain) mainSelectedMainPosition = it else subSelectedMainPosition = it
                    detailCarrierViewModel.getPositions()
                },
            )
            HeightSpacer(heightDp = 20.dp)
            DetailPositionArea(
                snackBarHostState = snackBarHostState,
                selectedDetailPosition = if(isMain) mainSelectedDetailPosition else subSelectedDetailPosition,
                detailCarrierViewModel = detailCarrierViewModel,
                onSelect = {
                    if(isMain) {
                        mainSelectedDetailPosition = it.sub
                        mainSelectedDetailPositionId = it.id
                    } else {
                        subSelectedDetailPosition = it.sub
                        subSelectedDetailPositionId = it.id
                    }
                },
            )
        }

        HeightSpacer(heightDp = 12.dp)

        DetailProfileNextButton(
            text = stringResource(id = R.string.choice_carrier_position3),
            textColor = nextButtonContainerAndTextColor(
                if(isMain) mainSelectedCarrier else subSelectedCarrier,
                if(isMain) mainSelectedMainPosition else subSelectedMainPosition,
                if(isMain) mainSelectedDetailPosition else subSelectedDetailPosition,
            ).second,
            containerColor = nextButtonContainerAndTextColor(
                if(isMain) mainSelectedCarrier else subSelectedCarrier,
                if(isMain) mainSelectedMainPosition else subSelectedMainPosition,
                if(isMain) mainSelectedDetailPosition else subSelectedDetailPosition,
            ).first,
            onClick = { navController.popBackStack() },
        )
    }
}

fun nextButtonContainerAndTextColor(
    selectedCarrier: String,
    selectedPosition: String,
    selectedDetailPosition: String,
) = if(selectedCarrier.isNotEmpty() && selectedPosition.isNotEmpty() && selectedDetailPosition.isNotEmpty()) PRIMARY to BLACK else LIGHT200 to GRAY400