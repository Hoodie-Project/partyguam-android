package com.party.presentation.screen.home.tab_recruitment

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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LIGHT400
import com.party.domain.model.user.detail.PositionListResponse
import com.party.presentation.screen.detail.choice_carrier_position.positionList
import com.party.presentation.screen.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PositionModal(
    snackBarHostState: SnackbarHostState,
    titleText: String,
    selectedMainPosition: String,
    selectedSubPositionList: List<String>,
    selectedPositionList: MutableList<Pair<String, String>>,
    homeViewModel: HomeViewModel,
    onModelClose: () -> Unit,
    onMainPositionClick: (String) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
    onSelectSubPosition: (String) -> Unit,
    onDelete: (Pair<String, String>) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onModelClose()
        },
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
                ModalTitleArea(
                    titleText = titleText,
                    onModelClose = onModelClose,
                )

                PositionSelectArea(
                    selectedMainPosition = selectedMainPosition,
                    selectedSubPositionList = selectedSubPositionList,
                    homeViewModel = homeViewModel,
                    snackBarHostState = snackBarHostState,
                    onMainPositionClick = onMainPositionClick,
                    onSelectSubPosition = onSelectSubPosition,
                )
            }

            SelectedPositionArea(
                selectedPositionList = selectedPositionList,
                onDelete = onDelete,
            )

            ModalBottomArea(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
                buttonText1 = stringResource(id = R.string.recruitment_modal1),
                buttonText2 = stringResource(id = R.string.recruitment_modal2),
                onReset = onReset,
                onApply = onApply,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedPositionArea(
    selectedPositionList: MutableList<Pair<String, String>>,
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
fun SelectedPositionItem(
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
                    painter = painterResource(id = R.drawable.close2),
                    contentDescription = "close icon",
                    tint = Color.Gray
                )
            }
        }
    }


}

@Composable
fun PositionSelectArea(
    selectedMainPosition: String,
    selectedSubPositionList: List<String>,
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    onMainPositionClick: (String) -> Unit,
    onSelectSubPosition: (String) -> Unit,
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
            homeViewModel = homeViewModel,
            selectedSubPositionList = selectedSubPositionList,
            snackBarHostState = snackBarHostState,
            onSelectSubPosition = onSelectSubPosition
        )
    }
}

@Composable
fun MainPositionListArea(
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
fun SubPositionListArea(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    selectedSubPositionList: List<String>,
    snackBarHostState: SnackbarHostState,
    onSelectSubPosition: (String) -> Unit,
) {
    val subPositionListState by homeViewModel.positionsState.collectAsState()
    val subPositionListResult = subPositionListState.data

    when (subPositionListState) {
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = subPositionListResult as ServerApiResponse.SuccessResponse
            LazyColumn(
                modifier = modifier,
            ) {
                itemsIndexed(
                    items = successResult.data ?: emptyList(),
                    key = { index, _ ->
                        index
                    }
                ) { index, item ->
                    SubPositionListItem(
                        item = item,
                        selectedSubPositionList = selectedSubPositionList,
                        onClick = { onSelectSubPosition(it) }
                    )
                }
            }
        }
        is UIState.Error -> {}
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }
}

@Composable
fun SubPositionListItem(
    item: PositionListResponse,
    selectedSubPositionList: List<String>,
    onClick: (String) -> Unit,
) {
    println("selectedSubPositionList: $selectedSubPositionList")
    Row(
        modifier = Modifier
            .height(52.dp)
            .padding(start = 20.dp)
            .noRippleClickable {
                onClick(item.sub)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = if (selectedSubPositionList.contains(item.sub)) painterResource(id = R.drawable.checed_icon) else painterResource(id = R.drawable.uncheckd_icon),
            contentDescription = "check icon",
        )
        WidthSpacer(widthDp = 6.dp)
        Text(
            text = item.sub,
            color = BLACK,
            fontWeight = if (selectedSubPositionList.contains(item.sub)) FontWeight.Bold else FontWeight.Normal,
            fontSize = B1,
        )
    }
}

@Composable
fun MainPositionListItem(
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
            painter = painterResource(id = R.drawable.arrow_right_icon),
            contentDescription = "arrow right",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPositionListItemPreview(
    modifier: Modifier = Modifier
) {
    MainPositionListItem(
        modifier = modifier,
        containerColor = LIGHT400,
        text = "포지션",
        selectedMainPosition = "포지션",
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SelectedPositionAreaPreview(
    modifier: Modifier = Modifier
) {
    SelectedPositionArea(
        selectedPositionList = mutableListOf(
            Pair("개발자", "Android"),
            Pair("디자이너", "UI/UX")
        ),
        onDelete = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SelectedPositionItemPreview(
    modifier: Modifier = Modifier
) {
    SelectedPositionItem(
        selectedPosition = Pair("개발자", "Android"),
        onDelete = {}
    )
}