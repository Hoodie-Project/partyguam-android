package com.party.presentation.screen.home_detail_profile.component

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
import com.party.domain.model.user.detail.Location
import com.party.guam.design.BLACK
import com.party.guam.design.DARK200
import com.party.guam.design.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY600
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT400
import com.party.guam.design.PRIMARY
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.detail_profile.provinceList

@Composable
fun LocationSection(
    selectedProvince: String,
    onClickProvince: (String) -> Unit,
    subLocationList: List<Location>,
    selectedProvinceAndSubLocationList: List<Pair<String, Location>>,
    onClickSubLocation: (Location) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
    ) {
        ProvinceListSection(
            modifier = Modifier.weight(1f),
            selectedProvince = selectedProvince,
            onClickProvince = onClickProvince
        )

        WidthSpacer(widthDp = 12.dp)

        SubLocationSection(
            modifier = Modifier.weight(2f),
            subLocationList = subLocationList,
            selectedProvinceAndSubLocationList = selectedProvinceAndSubLocationList,
            onClickSubLocation = onClickSubLocation
        )
    }
}

@Composable
private fun ProvinceListSection(
    modifier: Modifier,
    selectedProvince: String,
    onClickProvince: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, GRAY200),
                shape = RoundedCornerShape(LARGE_CORNER_SIZE)
            )
    ) {
        itemsIndexed(
            items = provinceList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            ProvinceCard(
                containerColor = if(selectedProvince == item) LIGHT400 else WHITE,
                text = item,
                textColor = if(selectedProvince == item) BLACK else GRAY400,
                fontWeight = if(selectedProvince == item) FontWeight.Bold else FontWeight.SemiBold,
                onClickProvince = { onClickProvince(item) }
            )
        }
    }
}

@Composable
private fun ProvinceCard(
    containerColor: Color,
    text: String,
    textColor: Color,
    fontWeight: FontWeight,
    onClickProvince: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                onClickProvince(text)
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
                fontWeight = fontWeight
            )
        }
    }
}

@Composable
private fun SubLocationSection(
    modifier: Modifier = Modifier,
    subLocationList: List<Location>,
    selectedProvinceAndSubLocationList: List<Pair<String, Location>>,
    onClickSubLocation: (Location) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .border(
                BorderStroke(1.dp, GRAY200),
                shape = RoundedCornerShape(LARGE_CORNER_SIZE)
            )
        ,
        columns = GridCells.Fixed(2),
    ){
        itemsIndexed(
            items = subLocationList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SubLocationCard(
                subLocationName = item.city,
                textColor = selectedLocationColor(selectedProvinceAndSubLocationList, item.city),
                border = if (selectedLocationColor(selectedProvinceAndSubLocationList, item.city) == DARK200) BorderStroke(1.dp, PRIMARY) else null,
                onClickSubLocation = { onClickSubLocation(item) }
            )
        }
    }
}

@Composable
private fun SubLocationCard(
    subLocationName: String,
    textColor: Color,
    border: BorderStroke? = null,
    onClickSubLocation: () -> Unit,
) {
    Card(
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                onClickSubLocation()
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
                text = subLocationName,
                color = textColor,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

private fun selectedLocationColor(selectedLocationList: List<Pair<String, Location>>, selectedSubLocation: String): Color{
    val isContain = selectedLocationList.any { it.second.city.contains(selectedSubLocation) }
    return if(isContain) DARK200 else GRAY600
}