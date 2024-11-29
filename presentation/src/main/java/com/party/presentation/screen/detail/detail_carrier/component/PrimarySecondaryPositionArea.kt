package com.party.presentation.screen.detail.detail_carrier.component

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.DetailCarrierData.mainSelectedDetailPosition
import com.party.common.DetailCarrierData.mainSelectedCarrier
import com.party.common.DetailCarrierData.mainSelectedMainPosition
import com.party.common.DetailCarrierData.subSelectedCarrier
import com.party.common.DetailCarrierData.subSelectedDetailPosition
import com.party.common.DetailCarrierData.subSelectedMainPosition
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun PositionArea(
    context: Context,
    onGoToChoiceCarrierPosition: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PositionAreaComponent(
            context = context,
            title = stringResource(id = R.string.detail_carrier3),
            selectedCarrier = mainSelectedCarrier,
            selectedPosition = mainSelectedMainPosition,
            selectedDetailPosition = mainSelectedDetailPosition,
            onGoToChoiceCarrierPosition = {
                onGoToChoiceCarrierPosition(true)
            }
        )
        HeightSpacer(heightDp = 40.dp)
        PositionAreaComponent(
            context = context,
            title = stringResource(id = R.string.detail_carrier4),
            selectedCarrier = subSelectedCarrier,
            selectedPosition = subSelectedMainPosition,
            selectedDetailPosition = subSelectedDetailPosition,
            onGoToChoiceCarrierPosition = {
                onGoToChoiceCarrierPosition(false)
            }
        )
    }
}

@Composable
fun PositionAreaComponent(
    context: Context,
    title: String,
    selectedCarrier: String,
    selectedPosition: String,
    selectedDetailPosition: String,
    onGoToChoiceCarrierPosition: () -> Unit,
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
        AddCarrierCard(
            context = context,
            selectedCarrier = selectedCarrier,
            selectedPosition = selectedPosition,
            selectedDetailPosition = selectedDetailPosition,
            onGoToChoiceCarrierPosition = onGoToChoiceCarrierPosition,
        )
    }
}

@Composable
fun AddCarrierCard(
    context: Context,
    selectedCarrier: String,
    selectedPosition: String,
    selectedDetailPosition: String,
    onGoToChoiceCarrierPosition: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT)
            .noRippleClickable { onGoToChoiceCarrierPosition() },
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
                Text(
                    text = setAddCarrierCardText(context = context, carrier = selectedCarrier, mainCarrier = selectedPosition, detailCarrier = selectedDetailPosition),
                    color = GRAY500,
                    fontSize = B2,
                )
            }
        }
    }
}

fun setAddCarrierCardText(context: Context, carrier: String, mainCarrier: String, detailCarrier: String): String{
    return if(carrier.isEmpty()) "+ ${context.getString(R.string.detail_carrier5)}" else "$carrier  |  $mainCarrier  |  $detailCarrier"
}