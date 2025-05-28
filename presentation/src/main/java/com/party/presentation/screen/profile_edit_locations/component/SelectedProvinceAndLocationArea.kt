package com.party.presentation.screen.profile_edit_locations.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.domain.model.user.detail.Location

@Composable
fun SelectedProvinceAndLocationArea(
    selectedProvinceAndLocationList: List<Pair<String, Location>>,
    onDelete: (Pair<String, Location>) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = selectedProvinceAndLocationList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SelectedProvinceAndLocationItem(
                item = item,
                selectedProvinceName = item.second.city,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun SelectedProvinceAndLocationItem(
    item: Pair<String, Location>,
    selectedProvinceName: String,
    onDelete: (Pair<String, Location>) -> Unit,
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, PRIMARY),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${item.first} $selectedProvinceName",
                color = BLACK,
                fontSize = B2,
            )
            WidthSpacer(widthDp = 4.dp)
            DrawableIcon(
                modifier = Modifier
                    .noRippleClickable { onDelete(item) },
                iconSize = 12.dp,
                icon = painterResource(id = R.drawable.icon_close2),
                contentDescription = "close",
                tintColor = BLACK
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedProvinceAndLocationItemPreview() {
    SelectedProvinceAndLocationItem(
        item = Pair("강남구", Location(1, "서울", "강남구")),
        selectedProvinceName = "서울",
        onDelete = {}
    )
}