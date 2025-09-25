package com.party.presentation.screen.home_detail_profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.domain.model.user.detail.PositionList
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.DARK100
import com.party.guam.design.GRAY200
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.LIGHT400
import com.party.guam.design.PRIMARY
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.choice_carrier_position.component.SelectCarrierAndPositionComponent

@Composable
fun SelectCareerSection(
    title: String,
    list: List<String>,
    selectedCareer: String,
    onSelectCareer: (String) -> Unit,
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
                containerColor = if(selectedCareer == item) LIGHT400 else WHITE,
                borderColor = if(selectedCareer == item) DARK100 else GRAY200,
                text = item,
                fontWeight = if(selectedCareer == item) FontWeight.SemiBold else FontWeight.Normal,
                onSelect = { onSelectCareer(it) },
            )
        }
    }
}

@Composable
fun SelectMainPositionSection(
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
fun SelectedSubPositionSection(
    subPositionList: List<PositionList>,
    selectedDetailPosition: String,
    onClickSubPosition: (PositionList) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(
            items = subPositionList,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            SubPositionItem(
                item = item,
                selected = item.sub == selectedDetailPosition,
                onClickSubPosition = onClickSubPosition
            )
        }
    }
}

@Composable
private fun SubPositionItem(
    item: PositionList,
    selected: Boolean,
    onClickSubPosition: (PositionList) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(LARGE_BUTTON_HEIGHT),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.size(16.dp),
            selected = selected,
            onClick = { onClickSubPosition(item) },
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
            onClick = { onClickSubPosition(item) }
        )
    }
}