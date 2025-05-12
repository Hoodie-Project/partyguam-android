package com.party.presentation.screen.detail.detail_profile.component

import android.content.Context
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
import androidx.compose.material3.SnackbarHostState
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
import com.party.presentation.screen.detail.componentClick
import com.party.presentation.screen.detail.detail_profile.ProvinceList

@Composable
fun SelectLocationArea(
    selectedProvince: String,
    selectedLocationList: MutableList<Pair<String, Int>>,
    onSelectProvince: (String) -> Unit,
    onSelectLocation: (Pair<String, Int>) -> Unit,
    onDeleteLocation: (Pair<String, Int>) -> Unit,
    snackBarHostState: SnackbarHostState,
    context: Context,
    locationListResult: List<Location>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectProvinceArea(
            modifier = Modifier.weight(1f),
            selectedProvince = selectedProvince,
            onSelectProvince = onSelectProvince,
        )

        WidthSpacer(widthDp = 12.dp)

        SelectLocationArea(
            context = context,
            snackBarHostState = snackBarHostState,
            modifier = Modifier.weight(2f),
            selectedProvinceName = selectedProvince,
            selectedLocationList = selectedLocationList,
            onSelectLocation = onSelectLocation,
            onDeleteLocation = onDeleteLocation,
            locationListResult = locationListResult
        )
    }
}

@Composable
fun SelectProvinceArea(
    modifier: Modifier,
    selectedProvince: String,
    onSelectProvince: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, GRAY200),
                shape = RoundedCornerShape(LARGE_CORNER_SIZE)
            )
            .height(320.dp)
    ) {
        val locationList = ProvinceList.entries.map { it.city }

        itemsIndexed(
            items = locationList,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            ProvinceComponent(
                containerColor = if(selectedProvince == item) LIGHT400 else WHITE,
                text = item,
                textColor = if(selectedProvince == item) DARK200 else GRAY400,
                onSelectCity = { onSelectProvince(it) }
            )
        }
    }
}

@Composable
fun SelectLocationArea(
    context: Context,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier,
    selectedProvinceName: String,
    selectedLocationList: MutableList<Pair<String, Int>>,
    onSelectLocation: (Pair<String, Int>) -> Unit,
    onDeleteLocation: (Pair<String, Int>) -> Unit,
    locationListResult: List<Location>
) {
    LazyVerticalGrid(
        modifier = modifier
            .border(
                BorderStroke(1.dp, GRAY200),
                shape = RoundedCornerShape(LARGE_CORNER_SIZE)
            )
            .height(320.dp),
        columns = GridCells.Fixed(2),
    ){
        itemsIndexed(
            items = locationListResult,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            LocationComponent(
                locationResponse = item,
                textColor = isContainProvinceAndSetTextColor(selectedLocationList, item.city),
                border = isContainProvinceAndSetBorder(selectedLocationList, item.city),
                selectedCityName = selectedProvinceName,
                onSelectLocation = { onSelectLocation(it) },
                onDeleteLocation = { onDeleteLocation(it) },
                snackBarHostState = snackBarHostState,
                context = context,
                selectedCountryList = selectedLocationList
            )
        }
    }
}

fun isContainProvinceAndSetTextColor(selectedLocationList: MutableList<Pair<String, Int>>, selectedLocation: String): Color{
    val isContain = selectedLocationList.any { it.first.contains(selectedLocation) }
    return if(isContain) DARK200 else GRAY600
}

fun isContainProvinceAndSetBorder(selectedLocationList: MutableList<Pair<String, Int>>, selectedLocation: String): BorderStroke?{
    val isContain = selectedLocationList.any { it.first.contains(selectedLocation) }
    return if(isContain) BorderStroke(1.dp, PRIMARY) else null
}

@Composable
fun ProvinceComponent(
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
fun LocationComponent(
    context: Context,
    snackBarHostState: SnackbarHostState,
    selectedCityName: String,
    locationResponse: Location,
    selectedCountryList: MutableList<Pair<String, Int>>,
    textColor: Color,
    border: BorderStroke? = null,
    onSelectLocation: (Pair<String, Int>) -> Unit,
    onDeleteLocation: (Pair<String, Int>) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                componentClick(
                    context = context,
                    snackBarHostState = snackBarHostState,
                    selectedProvinceName = selectedCityName,
                    locationResponse = locationResponse,
                    selectedCountryList = selectedCountryList,
                    onSelectCountry = onSelectLocation,
                    onDeleteCountry = onDeleteLocation,
                )
            }
            .padding(1.dp),
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
                text = locationResponse.city,
                color = textColor,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}