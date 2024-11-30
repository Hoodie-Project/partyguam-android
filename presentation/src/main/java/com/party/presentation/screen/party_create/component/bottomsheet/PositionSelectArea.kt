package com.party.presentation.screen.party_create.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.BLACK
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.screen.home.tab_recruitment.MainPositionListArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel

@Composable
fun PositionSelectArea(
    selectedMainPosition: String,
    selectedSubPosition: PositionList,
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
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
            selectedSubPosition = selectedSubPosition,
            homeViewModel = homeViewModel,
            snackBarHostState = snackBarHostState,
            onSelectSubPosition = onSelectSubPosition,
        )
    }
}

@Composable
fun SubPositionListArea(
    modifier: Modifier = Modifier,
    selectedSubPosition: PositionList,
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    onSelectSubPosition: (PositionList) -> Unit,
){
    val subPositionListState by homeViewModel.positionsState.collectAsStateWithLifecycle()
    val subPositionListResult = subPositionListState.data

    when (subPositionListState) {
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = subPositionListResult as SuccessResponse
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
                        selectedSubPosition = selectedSubPosition,
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
            painter = if (selectedSubPosition.sub == item.sub) painterResource(id = R.drawable.checed_icon) else painterResource(id = R.drawable.uncheckd_icon),
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