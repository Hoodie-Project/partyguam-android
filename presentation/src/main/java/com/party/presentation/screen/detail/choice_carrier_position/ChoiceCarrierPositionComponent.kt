package com.party.presentation.screen.detail.choice_carrier_position

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.TextComponent
import com.party.common.UIState
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.PositionListResponse
import com.party.presentation.screen.detail.detail_carrier.DetailCarrierViewModel

@Composable
fun SelectCarrierArea(
    title: String,
    list: List<String>,
    selectedCarrier: String,
    onSelect: (String) -> Unit,
) {
    TextComponent(
        text = title,
        fontSize = T3,
        fontWeight = FontWeight.SemiBold,
    )
    HeightSpacer(heightDp = 12.dp)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        itemsIndexed(
            items = list,
            key = { index, item ->
                index
            }
        ){_, item ->
            SelectCarrierAndPositionComponent(
                containerColor = if(selectedCarrier == item) PRIMARY else WHITE,
                text = item,
                fontWeight = FontWeight.Normal,
                onSelect = { onSelect(it) },
            )
        }
    }
}

@Composable
fun SelectPositionArea(
    title: String,
    list: List<String>,
    selectedPosition: String,
    onSelect: (String) -> Unit,
) {
    TextComponent(
        text = title,
        fontSize = T3,
        fontWeight = FontWeight.SemiBold,
    )
    HeightSpacer(heightDp = 12.dp)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        itemsIndexed(
            items = list,
            key = { index, _ ->
                index
            }
        ){_, item ->
            SelectCarrierAndPositionComponent(
                containerColor = if(selectedPosition == item) PRIMARY else WHITE,
                text = item,
                fontWeight = FontWeight.Normal,
                onSelect = { onSelect(it) },
            )
        }
    }
}

@Composable
fun SelectCarrierAndPositionComponent(
    containerColor: Color,
    text: String,
    fontWeight: FontWeight,
    onSelect: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT)
            .noRippleClickable { onSelect(text) },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontWeight = fontWeight,
                fontSize = T3,
            )
        }
    }
}

@Composable
fun DetailPositionArea(
    snackBarHostState: SnackbarHostState,
    selectedDetailPosition: String,
    detailCarrierViewModel: DetailCarrierViewModel,
    onSelect: (PositionListResponse) -> Unit,
) {
    val detailPositionListState by detailCarrierViewModel.positionsState.collectAsState()
    val detailPositionListResult = detailPositionListState.data

    when(detailPositionListState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = detailPositionListResult as SuccessResponse

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                itemsIndexed(
                    items = successResult.data ?: emptyList(),
                    key = { index, _ ->
                        index
                    }
                ){ _, item ->
                    SubPositionAreaComponent(
                        item = item,
                        selected = item.sub == selectedDetailPosition,
                        onSelect = {onSelect(it)}
                    )
                }
            }
        }
        is UIState.Error -> {}
        is UIState.Exception -> {
            snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
        }
    }
}

@Composable
fun SubPositionAreaComponent(
    item: PositionListResponse,
    selected: Boolean,
    onSelect: (PositionListResponse) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(LARGE_BUTTON_HEIGHT),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.size(16.dp),
            selected = selected,
            onClick = { onSelect(item) },
            colors = RadioButtonDefaults.colors(
                selectedColor = PRIMARY,
                unselectedColor = GRAY200,
            )
        )
        WidthSpacer(widthDp = 6.dp)
        Text(
            text = item.sub,
            fontSize = B2,
            color = BLACK
        )
    }
}