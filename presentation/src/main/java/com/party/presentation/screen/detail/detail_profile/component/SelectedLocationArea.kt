package com.party.presentation.screen.detail.detail_profile.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT400
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.DetailProfileNextButton
import com.party.presentation.screen.detail.detail_profile.SELECTED_LOCATION_COUNT

@Composable
fun SelectedLocationArea(
    selectedProvince: String,
    selectedLocationList: MutableList<Pair<String, Int>>,
    onDelete: (Pair<String, Int>) -> Unit,
    onSkip: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SelectedLocationList(
            selectedProvince = selectedProvince,
            selectedLocationList = selectedLocationList,
            onDelete = onDelete,
        )

        HeightSpacer(heightDp = 12.dp)

        DetailProfileNextButton(
            text = stringResource(id = R.string.common1),
            textColor = if((1..SELECTED_LOCATION_COUNT).contains(selectedLocationList.size)) BLACK else GRAY400,
            containerColor = if((1..SELECTED_LOCATION_COUNT).contains(selectedLocationList.size)) PRIMARY else LIGHT400,
            onClick = onNextButtonClick
        )

        HeightSpacer(heightDp = 12.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(id = R.string.common3),
            fontSize = B2,
            textColor = GRAY500,
            align = Alignment.Center,
            textDecoration = TextDecoration.Underline,
            onClick = onSkip
        )
    }
}

@Composable
fun SelectedLocationList(
    selectedProvince: String,
    selectedLocationList: MutableList<Pair<String, Int>>,
    onDelete: (Pair<String, Int>) -> Unit,
) {
    HeightSpacer(heightDp = 16.dp)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = selectedLocationList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SelectLocationComponent(
                item = item,
                selectedProvinceName = selectedProvince,
                onDelete = { onDelete(it) }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SelectLocationComponent(
    item: Pair<String, Int>,
    selectedProvinceName: String,
    onDelete: (Pair<String, Int>) -> Unit,
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
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$selectedProvinceName ${item.first}",
                color = BLACK,
                fontSize = B2,
            )
            IconButton(
                modifier = Modifier.size(36.dp),
                onClick = { onDelete(item) },
                interactionSource = MutableInteractionSource(),
            ) {
                Icon(
                    modifier = Modifier
                        .size(12.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectLocationComponentPreview(modifier: Modifier = Modifier) {
    SelectLocationComponent(
        item = Pair("강남구", 1),
        selectedProvinceName = "서울",
        onDelete = {}
    )
}