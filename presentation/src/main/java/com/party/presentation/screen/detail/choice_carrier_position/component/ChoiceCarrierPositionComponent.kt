package com.party.presentation.screen.detail.choice_carrier_position.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.DARK100
import com.party.guam.design.GRAY200
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT400
import com.party.guam.design.PRIMARY
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.domain.model.user.detail.PositionList

@Composable
fun SelectCarrierArea(
    title: String,
    list: List<String>,
    selectedCarrier: String,
    onSelectCarrier: (String) -> Unit,
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
                containerColor = if(selectedCarrier == item) LIGHT400 else WHITE,
                borderColor = if(selectedCarrier == item) DARK100 else GRAY200,
                text = item,
                fontWeight = if(selectedCarrier == item) FontWeight.SemiBold else FontWeight.Normal,
                onSelect = { onSelectCarrier(it) },
            )
        }
    }
}

@Composable
fun SelectPositionArea(
    title: String,
    list: List<String>,
    selectedPosition: String,
    onSelectMainPosition: (String) -> Unit,
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
                containerColor = if(selectedPosition == item) LIGHT400 else WHITE,
                borderColor = if(selectedPosition == item) DARK100 else GRAY200,
                text = item,
                fontWeight = if(selectedPosition == item) FontWeight.SemiBold else FontWeight.Normal,
                onSelect = { onSelectMainPosition(it) },
            )
        }
    }
}

@Composable
fun SelectCarrierAndPositionComponent(
    containerColor: Color,
    borderColor: Color,
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
        border = BorderStroke(1.dp, borderColor),
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
    detailPositionListResult: List<PositionList>,
    selectedDetailPosition: String,
    onSelectSubPosition: (PositionList) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(
            items = detailPositionListResult,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            SubPositionAreaComponent(
                item = item,
                selected = item.sub == selectedDetailPosition,
                onSelect = {onSelectSubPosition(it)}
            )
        }
    }
}

@Composable
fun SubPositionAreaComponent(
    item: PositionList,
    selected: Boolean,
    onSelect: (PositionList) -> Unit,
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
        TextComponent(
            text = item.sub,
            fontSize = B2,
            textColor = BLACK,
            onClick = { onSelect(item) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectCarrierAndPositionComponentPreview1(){
    SelectCarrierAndPositionComponent(
        containerColor = DARK100,
        borderColor = LIGHT400,
        text = "신입",
        fontWeight = FontWeight.SemiBold,
        onSelect = {  }
    )
}

@Preview(showBackground = true)
@Composable
private fun SelectCarrierAndPositionComponentPreview2(){
    SelectCarrierAndPositionComponent(
        containerColor = WHITE,
        borderColor = GRAY200,
        text = "신입",
        fontWeight = FontWeight.Normal,
        onSelect = {  }
    )
}