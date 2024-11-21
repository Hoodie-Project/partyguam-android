package com.party.presentation.screen.party_detail.tab.recruitment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@Composable
fun PartyDetailRecruitmentFilterArea(
    selectedPosition: String,
    selectedCreateDataOrderByDesc: Boolean,
    onChangeSelected: (Boolean) -> Unit,
    onReset: () -> Unit,
    onApply: (String) -> Unit,
) {
    var isBottomSheetOpen by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NoBorderBottomSheetChip(
            modifier = Modifier,
            chipName = selectedPosition,
            isSheetOpen = false,
            onClick = { isBottomSheetOpen = it },
            spacer = 0.dp,
            painter = painterResource(id = R.drawable.arrow_drop_down),
        )

        OrderByCreateDtChip(
            text = stringResource(id = R.string.filter1),
            orderByDesc = selectedCreateDataOrderByDesc,
            onChangeSelected = { onChangeSelected(it) }
        )
    }

    if(isBottomSheetOpen){
        MainPositionBottomSheet(
            selectedPosition = selectedPosition,
            onBottomSheetClose = { isBottomSheetOpen = false },
            onApply = onApply,
            onReset = onReset,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPartyDetailRecruitmentFilterArea() {
    PartyDetailRecruitmentFilterArea(
        selectedPosition = "",
        selectedCreateDataOrderByDesc = false,
        onChangeSelected = {},
        onReset = {},
        onApply = {},
    )
}