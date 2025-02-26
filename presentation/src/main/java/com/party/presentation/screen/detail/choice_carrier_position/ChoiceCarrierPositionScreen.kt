package com.party.presentation.screen.detail.choice_carrier_position

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.bottomsheet.list.positionList
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.screen.detail.DetailProfileNextButton
import com.party.presentation.screen.detail.choice_carrier_position.component.ChoiceCarrierPositionScaffoldArea
import com.party.presentation.screen.detail.choice_carrier_position.component.DetailPositionArea
import com.party.presentation.screen.detail.choice_carrier_position.component.SelectCarrierArea
import com.party.presentation.screen.detail.choice_carrier_position.component.SelectPositionArea
import com.party.presentation.screen.detail.choice_carrier_position.component.carrierList
import com.party.presentation.screen.detail.detail_carrier.viewmodel.DetailCarrierViewModel

@Composable
fun ChoiceCarrierPositionScreen(
    navController: NavController,
    isMain: Boolean,
    detailCarrierViewModel: DetailCarrierViewModel = hiltViewModel(),
) {
    val detailPositionListState by detailCarrierViewModel.positionsState.collectAsStateWithLifecycle()

    var list by remember {
        mutableStateOf(emptyList<PositionList>())
    }

    when(detailPositionListState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar()}
        is UIState.Success -> {
            val detailPositionListResult = detailPositionListState.data as SuccessResponse<List<PositionList>>
            list = detailPositionListResult.data ?: emptyList()
        }
        is UIState.Error -> { }
        is UIState.Exception -> { }
    }

    ChoiceCarrierPositionScreenContent(
        isMain = isMain,
        detailPositionListResult = list,
        onClick = { navController.popBackStack() },
        onSelectCarrier = {
            if(isMain) mainSelectedCarrier = it else subSelectedCarrier = it
        },
        onSelectMainPosition = {
            if(isMain) {
                mainSelectedMainPosition = it
                detailCarrierViewModel.getPositions(
                    mainSelectedMainPosition
                )
            } else {
                subSelectedMainPosition = it
                detailCarrierViewModel.getPositions(
                    subSelectedMainPosition
                )
            }
        },
        onSelectSubPosition = {
            if(isMain) {
                mainSelectedDetailPosition = it.sub
                mainSelectedDetailPositionId = it.id
            } else {
                subSelectedDetailPosition = it.sub
                subSelectedDetailPositionId = it.id
            }
        },
        onNavigationClick = { navController.popBackStack() }
    )
}

@Composable
fun ChoiceCarrierPositionScreenContent(
    isMain: Boolean,
    detailPositionListResult: List<PositionList>,
    onClick: () -> Unit,
    onSelectCarrier: (String) -> Unit,
    onSelectMainPosition: (String) -> Unit,
    onSelectSubPosition: (PositionList) -> Unit,
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            ChoiceCarrierPositionScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        }
    ) {
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
                HeightSpacer(heightDp = 20.dp)
                SelectCarrierArea(
                    title = stringResource(id = R.string.choice_carrier_position1),
                    list = carrierList,
                    selectedCarrier = if(isMain) mainSelectedCarrier else subSelectedCarrier,
                    onSelectCarrier = onSelectCarrier,
                )
                HeightSpacer(heightDp = 20.dp)
                SelectPositionArea(
                    title = stringResource(id = R.string.choice_carrier_position2),
                    list = positionList,
                    selectedPosition = if(isMain) mainSelectedMainPosition else subSelectedMainPosition,
                    onSelectMainPosition = onSelectMainPosition,
                )
                HeightSpacer(heightDp = 20.dp)
                DetailPositionArea(
                    detailPositionListResult = detailPositionListResult,
                    selectedDetailPosition = if(isMain) mainSelectedDetailPosition else subSelectedDetailPosition,
                    onSelectSubPosition = onSelectSubPosition,
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
                onClick = onClick
            )
        }
    }
}

fun nextButtonContainerAndTextColor(
    selectedCarrier: String,
    selectedPosition: String,
    selectedDetailPosition: String,
) = if(selectedCarrier.isNotEmpty() && selectedPosition.isNotEmpty() && selectedDetailPosition.isNotEmpty()) PRIMARY to BLACK else LIGHT200 to GRAY400

@Preview(showBackground = true)
@Composable
fun ChoiceCarrierPositionScreenContentPreview() {
    ChoiceCarrierPositionScreenContent(
        isMain = true,
        detailPositionListResult = emptyList(),
        onClick = {},
        onSelectCarrier = {},
        onSelectMainPosition = {},
        onSelectSubPosition = {},
        onNavigationClick = {}
    )
}