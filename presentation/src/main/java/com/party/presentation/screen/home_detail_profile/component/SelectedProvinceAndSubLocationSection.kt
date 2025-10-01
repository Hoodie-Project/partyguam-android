package com.party.presentation.screen.home_detail_profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.domain.model.user.detail.Location
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE

@Composable
fun SelectedProvinceAndSubLocationSection(
    selectedProvinceAndSubLocationList: List<Pair<String, Location>>,
    onDelete: (Pair<String, Location>) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = selectedProvinceAndSubLocationList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SelectedProvinceAndSubLocationCard(
                item = item,
                selectedProvinceName = item.first,
                onDelete = { onDelete(it) }
            )
        }
    }
}

@Composable
private fun SelectedProvinceAndSubLocationCard(
    item: Pair<String, Location>,
    selectedProvinceName: String,
    onDelete: (Pair<String, Location>) -> Unit
) {
    Card(
        modifier = Modifier
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, PRIMARY),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$selectedProvinceName ${item.second.city}",
                color = BLACK,
                fontSize = fs(B2),
            )
            WidthSpacer(4.dp)
            Icon(
                modifier = Modifier
                    .size(12.dp)
                    .noRippleClickable {
                        onDelete(item)
                    },
                imageVector = Icons.Default.Close,
                contentDescription = "close",
            )
        }
    }
}