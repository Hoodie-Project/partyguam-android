package com.party.presentation.screen.party_user_manage.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.bottomsheet.MainPositionBottomSheet
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.party_user_manage.PartyUserState

@Composable
fun PartyUserFilterArea(
    partyUserState: PartyUserState,
    isPartyTypeFilterClick: (Boolean) -> Unit,
    onShowPositionFilter: (Boolean) -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
    onReset: () -> Unit,
    onApply: (String) -> Unit,
) {
    var selectedMainPosition by remember { mutableStateOf("전체") }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectFilterItem(
            filterName = partyUserState.selectedMainPosition,
            onClick = { isPartyTypeFilterClick(true) }
        )

        OrderByCreateDtChip(
            text = "참여일순",
            orderByDesc = partyUserState.isDesc,
            onChangeSelected = { onChangeOrderBy(it) }
        )
    }

    if(partyUserState.isOpenPositionBottomSheet){
        MainPositionBottomSheet(
            selectedPosition = selectedMainPosition,
            onBottomSheetClose = { onShowPositionFilter(false) },
            onPositionClick = {
                selectedMainPosition = it
            },
            onApply = { onApply(selectedMainPosition) },
            onReset = onReset,
        )
    }
}

@Composable
private fun SelectFilterItem(
    filterName: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = filterName,
                color = if(filterName == "전체") GRAY500 else BLACK,
                fontSize = B2
            )
            WidthSpacer(widthDp = 2.dp)
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.arrow_down_icon),
                contentDescription = "Arrow Down",
                tint = GRAY400,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyUserFilterAreaPreview() {
    PartyUserFilterArea(
        partyUserState = PartyUserState(),
        isPartyTypeFilterClick = {},
        onShowPositionFilter = {},
        onChangeOrderBy = {},
        onReset = {},
        onApply = {},
    )
}