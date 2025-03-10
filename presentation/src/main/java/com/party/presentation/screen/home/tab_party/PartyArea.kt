package com.party.presentation.screen.home.tab_party

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.PartyListItem1
import com.party.common.component.chip.Chip
import com.party.common.component.no_data.NoDataColumn
import com.party.presentation.enum.StatusType
import com.party.presentation.screen.home.HomeState
import com.party.presentation.screen.home.tab_party.component.FilterArea
import com.party.presentation.screen.home.tab_party.component.FilterDate
import com.party.presentation.screen.home.tab_recruitment.PartyTypeModal

@Composable
fun PartyArea(
    gridState: LazyGridState,
    homeState: HomeState,
    onClick: (Int) -> Unit,
    onPartyTypeModal: (Boolean) -> Unit,
    onSelectPartyType: (String) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
    onActivePartyToggle: (Boolean) -> Unit,
    onChangeOrderByPartyArea: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FilterArea(
            checked = homeState.isActivePartyToggle,
            onToggle = onActivePartyToggle,
            number = homeState.selectedPartyTypeListParty.size,
            isPartyTypeFilterClick = { onPartyTypeModal(true) }
        )

        FilterDate(
            selectedCreateDataOrderByDesc = homeState.isDescPartyArea,
            onChangeOrderByPartyArea = onChangeOrderByPartyArea
        )
        HeightSpacer(heightDp = 12.dp)

        PartyListArea(
            gridState = gridState,
            homeState = homeState,
            onClick = onClick
        )
    }

    if(homeState.isPartyTypeSheetOpen){
        PartyTypeModal(
            titleText = stringResource(id = R.string.recruitment_filter2),
            selectedPartyType = homeState.selectedPartyTypeListParty,
            onClick = onSelectPartyType,
            onModelClose = { onPartyTypeModal(false)},
            onReset = onReset,
            onApply = onApply
        )
    }
}

@Composable
private fun PartyListArea(
    gridState: LazyGridState,
    homeState: HomeState,
    onClick: (Int) -> Unit,
) {
    if(homeState.partyList.parties.isEmpty()){
        HeightSpacer(heightDp = 76.dp)
        NoDataColumn(title = "파티가 없어요.")
    } else {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(
                items = homeState.partyList.parties,
                key = { index, _ ->
                    index
                }
            ){_, item ->
                PartyListItem1(
                    imageUrl = item.image,
                    type = item.partyType.type,
                    title = item.title,
                    recruitmentCount = item.recruitmentCount,
                    typeChip = {
                        Chip(
                            containerColor = StatusType.fromType(item.status).toContainerColor(),
                            contentColor = StatusType.fromType(item.status).toContentColor(),
                            text = StatusType.fromType(item.status).toDisplayText()
                        )
                    },
                    onClick = { onClick(item.id) }
                )
            }
        }
    }
}