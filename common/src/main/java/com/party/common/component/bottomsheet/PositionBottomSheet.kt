package com.party.common.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.common.component.bottomsheet.component.BottomSheetButtonArea
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.common.component.bottomsheet.list.positionList
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LIGHT400

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PositionBottomSheet(
    selectedMainPosition: String,
    getSubPositionList: List<Pair<String, Int>>,
    selectedSubPositionList: List<Pair<String, Int>>,
    selectedMainAndSubPositionList: List<Pair<String, String>>,
    onSheetClose: () -> Unit,
    onMainPositionClick: (String) -> Unit,
    onSubPositionClick: (String) -> Unit,
    onDelete: (Pair<String, String>) -> Unit,
    onReset: () -> Unit = {},
    onApply: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onSheetClose() },
        containerColor = White,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                BottomSheetTitleArea(
                    titleText = "직무",
                    onSheetClose = onSheetClose
                )

                PositionSelectArea(
                    selectedMainPosition = selectedMainPosition,
                    getSubPositionList = getSubPositionList,
                    selectedSubPositionList = selectedSubPositionList,
                    onMainPositionClick = onMainPositionClick,
                    onSubPositionClick = onSubPositionClick
                )
            }

            SelectedPositionArea(
                selectedPositionList = selectedMainAndSubPositionList,
                onDelete = onDelete,
            )

            BottomSheetButtonArea(
                isActiveApplyButton = selectedMainAndSubPositionList.isNotEmpty(),
                isActiveResetButton = selectedMainAndSubPositionList.isNotEmpty(),
                onReset = onReset,
                onApply = onApply,
            )
        }
    }
}

@Composable
private fun PositionSelectArea(
    selectedMainPosition: String,
    getSubPositionList: List<Pair<String, Int>>,
    selectedSubPositionList: List<Pair<String, Int>>,
    onMainPositionClick: (String) -> Unit,
    onSubPositionClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MainPositionListArea(
            modifier = Modifier.weight(0.2f),
            selectedMainPosition = selectedMainPosition,
            onMainPositionClick = onMainPositionClick,
        )

        SubPositionListArea(
            modifier = Modifier.weight(0.3f),
            getSubPositionList = getSubPositionList,
            selectedSubPositionList = selectedSubPositionList,
            onSubPositionClick = onSubPositionClick
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
private fun SubPositionListArea(
    modifier: Modifier,
    getSubPositionList: List<Pair<String, Int>>,
    selectedSubPositionList: List<Pair<String, Int>>,
    onSubPositionClick: (String) -> Unit,
){
    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(
            items = getSubPositionList,
            key = { index, _ ->
                index
            }
        ) { index, item ->
            SubPositionListItem(
                sub = item.first,
                selectedSubPositionList = selectedSubPositionList,
                onSubPositionClick = { onSubPositionClick(it) }
            )
        }
    }
}

@Composable
private fun SubPositionListItem(
    sub: String,
    selectedSubPositionList: List<Pair<String, Int>>,
    onSubPositionClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(52.dp)
            .padding(start = 20.dp)
            .noRippleClickable { onSubPositionClick(sub) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            contentDescription = "check icon",
            painter = if (selectedSubPositionList.any { it.first == sub }) {
                painterResource(id = R.drawable.icon_checed)
            } else {
                painterResource(id = R.drawable.icon_uncheckd)
            },
        )
        WidthSpacer(widthDp = 6.dp)
        Text(
            text = sub,
            color = BLACK,
            fontWeight = if (selectedSubPositionList.any { it.first == sub }) FontWeight.Bold else FontWeight.Normal,
            fontSize = B1,
        )
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectedPositionArea(
    selectedPositionList: List<Pair<String, String>>,
    onDelete: (Pair<String, String>) -> Unit,
) {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 10.dp)
    )

    FlowRow(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (i in 1..selectedPositionList.size){
            SelectedPositionItem(
                selectedPosition = selectedPositionList[i - 1],
                onDelete = { onDelete(it) }
            )
        }
    }
}

@Composable
private fun SelectedPositionItem(
    selectedPosition: Pair<String, String>,
    onDelete: (Pair<String, String>) -> Unit,
) {
    Card(
        onClick = { onDelete(selectedPosition) },
        modifier = Modifier
            .wrapContentWidth()
            .height(32.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GRAY100,
        )
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "${selectedPosition.first} | ${selectedPosition.second}",
                color = BLACK,
                fontSize = B2,
            )
            WidthSpacer(widthDp = 4.dp)
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = { onDelete(selectedPosition) }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.icon_close2),
                    contentDescription = "close icon",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun PositionBottomSheetPreview() {
    PositionBottomSheet(
        selectedMainPosition = "개발자",
        selectedMainAndSubPositionList = emptyList(),
        selectedSubPositionList = emptyList(),
        getSubPositionList = emptyList(),
        onApply = {},
        onReset = {},
        onDelete = {},
        onMainPositionClick = { _ -> },
        onSubPositionClick = { _ -> },
        onSheetClose = {},

    )
}