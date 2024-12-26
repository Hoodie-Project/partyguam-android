package com.party.presentation.screen.party_detail.tab.recruitment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.bottomsheet.MainPositionBottomSheet
import com.party.common.component.chip.NoBorderBottomSheetChip
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.presentation.screen.party_detail.PartyDetailState

@Composable
fun PartyDetailRecruitmentFilterArea(
    state: PartyDetailState,
    selectedCreateDataOrderByDesc: Boolean,
    onChangeSelected: (Boolean) -> Unit,
    onShowPositionFilter: (Boolean) -> Unit,
    onPositionClick: (String) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NoBorderBottomSheetChip(
            modifier = Modifier,
            chipName = state.selectedPosition,
            isSheetOpen = false,
            onClick = { onShowPositionFilter(!state.isShowPositionFilter) },
            spacer = 0.dp,
            painter = painterResource(id = R.drawable.icon_arrow_drop_down),
        )

        OrderByCreateDtChip(
            text = stringResource(id = R.string.filter1),
            orderByDesc = selectedCreateDataOrderByDesc,
            onChangeSelected = { onChangeSelected(it) }
        )
    }

    if(state.isShowPositionFilter){
        MainPositionBottomSheet(
            selectedPosition = state.selectedPosition,
            onBottomSheetClose = { onShowPositionFilter(false) },
            onPositionClick = onPositionClick,
            onApply = onApply,
            onReset = onReset,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPartyDetailRecruitmentFilterArea() {
    PartyDetailRecruitmentFilterArea(
        state = PartyDetailState(),
        selectedCreateDataOrderByDesc = false,
        onChangeSelected = {},
        onShowPositionFilter = {},
        onPositionClick = {},
        onReset = {},
        onApply = {},
    )
}