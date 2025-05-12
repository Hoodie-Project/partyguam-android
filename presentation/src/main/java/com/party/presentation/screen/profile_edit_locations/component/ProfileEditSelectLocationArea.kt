package com.party.presentation.screen.profile_edit_locations.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.DARK200
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.Location
import com.party.presentation.screen.detail.detail_profile.ProvinceList

@Composable
fun ProfileEditSelectLocationArea(
    modifier: Modifier,
    selectedProvince: String,
    selectedLocationList: List<Location>,
    locationList: List<Location>,
    onSelectProvince: (String) -> Unit,
    onSelectLocation: (Location) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        SelectProvinceArea(
            modifier = Modifier.weight(1f),
            selectedProvince = selectedProvince,
            onSelectProvince = onSelectProvince
        )

        WidthSpacer(widthDp = 12.dp)

        SelectLocationArea(
            modifier = Modifier.weight(2f),
            selectedLocationList = selectedLocationList,
            locationList = locationList,
            onSelectLocation = onSelectLocation
        )
    }
}

@Composable
private fun SelectProvinceArea(
    modifier: Modifier,
    selectedProvince: String,
    onSelectProvince: (String) -> Unit,
) {
    val locationList = ProvinceList.entries.map { it.city }

    LazyColumn(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, GRAY200),
                shape = RoundedCornerShape(LARGE_CORNER_SIZE)
            )
    ) {
        itemsIndexed(
            items = locationList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SelectProvinceItem(
                containerColor = if(selectedProvince == item) LIGHT400 else WHITE,
                text = item,
                textColor = if(selectedProvince == item) DARK200 else GRAY400,
                onSelectCity = { onSelectProvince(it) }
            )
        }
    }
}

@Composable
private fun SelectProvinceItem(
    containerColor: Color,
    text: String,
    textColor: Color,
    onSelectCity: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                onSelectCity(text)
            },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = text,
                color = textColor,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun SelectLocationArea(
    modifier: Modifier,
    selectedLocationList: List<Location>,
    locationList: List<Location>,
    onSelectLocation: (Location) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .border(border = BorderStroke(1.dp, GRAY200), shape = RoundedCornerShape(LARGE_CORNER_SIZE)),
        columns = GridCells.Fixed(2),
    ){
        itemsIndexed(
            items = locationList,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            LocationComponent(
                text = item.city,
                textColor = if(selectedLocationList.contains(item)) DARK200 else GRAY600 ,
                border = if(selectedLocationList.contains(item)) BorderStroke(1.dp, PRIMARY) else null,
                onClick = { onSelectLocation(item) }
            )
        }
    }
}

@Composable
private fun LocationComponent(
    text: String,
    textColor: Color,
    border: BorderStroke? = null,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .padding(horizontal = 1.dp, vertical = 0.5.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = border,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = text,
                color = textColor,
                fontSize = T3,
            )
        }
    }
}