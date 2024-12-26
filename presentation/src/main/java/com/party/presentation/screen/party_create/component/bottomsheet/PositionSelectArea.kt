package com.party.presentation.screen.party_create.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.bottomsheet.list.positionList
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.LIGHT400
import com.party.domain.model.user.detail.PositionList

@Composable
fun PositionSelectArea(
    selectedMainPosition: String,
    subPositionList: List<PositionList>,
    selectedSubPosition: PositionList,
    onMainPositionClick: (String) -> Unit,
    onSelectSubPosition: (PositionList) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MainPositionListArea(
            modifier = Modifier
                .weight(0.2f),
            selectedMainPosition = selectedMainPosition,
            onMainPositionClick = onMainPositionClick,
        )

        SubPositionListArea(
            modifier = Modifier
                .weight(0.3f),
            subPositionList = subPositionList,
            selectedSubPosition = selectedSubPosition,
            onSelectSubPosition = onSelectSubPosition,
        )
    }
}

@Composable
private fun SubPositionListArea(
    modifier: Modifier = Modifier,
    subPositionList: List<PositionList>,
    selectedSubPosition: PositionList,
    onSelectSubPosition: (PositionList) -> Unit,
){
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(
            items = subPositionList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SubPositionListItem(
                item = item,
                selectedSubPosition = selectedSubPosition,
                onClick = { onSelectSubPosition(it) }
            )
        }
    }
}

@Composable
private fun SubPositionListItem(
    item: PositionList,
    selectedSubPosition: PositionList,
    onClick: (PositionList) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(52.dp)
            .padding(start = 20.dp)
            .noRippleClickable {
                onClick(item)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = if (selectedSubPosition.sub == item.sub) painterResource(id = R.drawable.icon_checed) else painterResource(id = R.drawable.icon_uncheckd),
            contentDescription = "check icon",
        )
        WidthSpacer(widthDp = 6.dp)
        Text(
            text = item.sub,
            color = BLACK,
            fontWeight = if (selectedSubPosition.sub == item.sub) FontWeight.Bold else FontWeight.Normal,
            fontSize = B1,
        )
    }
}

@Composable
private fun MainPositionListArea(
    modifier: Modifier,
    selectedMainPosition: String,
    onMainPositionClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(
            items = positionList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            MainPositionListItem(
                modifier = Modifier.fillMaxWidth(),
                text = item,
                selectedMainPosition = selectedMainPosition,
                containerColor = if (item == selectedMainPosition) LIGHT400 else White,
                onClick = { onMainPositionClick(it) }
            )
        }
    }
}

@Composable
private fun MainPositionListItem(
    modifier: Modifier,
    text: String,
    selectedMainPosition: String,
    containerColor: Color,
    onClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .noRippleClickable { onClick(text) }
            .background(containerColor)
            .padding(start = 20.dp, end = 12.dp)
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = text,
            fontWeight = if (text == selectedMainPosition) FontWeight.Bold else FontWeight.Normal,
        )
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.icon_arrow_right),
            contentDescription = "arrow right",
        )
    }
}