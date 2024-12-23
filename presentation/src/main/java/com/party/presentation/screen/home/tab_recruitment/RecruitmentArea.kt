package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.bottomsheet.PositionBottomSheet
import com.party.presentation.screen.home.HomeState

@Composable
fun RecruitmentArea(
    listState: LazyListState,
    homeState: HomeState,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    onPositionSheetClick: (Boolean) -> Unit,
    onPartyTypeFilterClick: (Boolean) -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
    onPositionSheetClose: (Boolean) -> Unit,
    onMainPositionClick: (String) -> Unit,
    onSubPositionClick: (String) -> Unit,
    onPositionSheetReset: () -> Unit,
    onDelete: (Pair<String, String>) -> Unit,
    onPositionApply: () -> Unit,
    onPartyTypeSheetClick: (String) -> Unit,
    onPartyTypeSheetReset: () -> Unit,
    onPartyTypeSheetApply: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeightSpacer(heightDp = 20.dp)
        SelectFilterArea(
            stringResource(id = R.string.recruitment_filter1),
            stringResource(id = R.string.recruitment_filter2),
            isPositionFilterClick = { onPositionSheetClick(it) },
            isPartyTypeFilterClick = { onPartyTypeFilterClick(it) },
            isPositionSheetOpen = homeState.isPositionSheetOpen,
            isPartyTypeSheetOpen = homeState.isPartyTypeSheetOpenRecruitment,
            selectedCreateDataOrderByDesc = homeState.isDescRecruitment,
            onChangeOrderBy = { onChangeOrderBy(it) },
            selectedPartyTypeList = homeState.selectedPartyTypeListRecruitment
        )
        HeightSpacer(heightDp = 16.dp)
        RecruitmentColumnListArea(
            listState = listState,
            homeState = homeState,
            onRecruitmentItemClick = onRecruitmentItemClick,
        )
    }

    if(homeState.isPositionSheetOpen){
        PositionBottomSheet(
            selectedMainPosition = homeState.selectedMainPosition,
            getSubPositionList = homeState.getSubPositionList.map { it.sub to it.id },
            selectedSubPositionList = homeState.selectedSubPositionList.map { it.sub to it.id },
            selectedMainAndSubPositionList = homeState.selectedMainAndSubPosition,
            onSheetClose = { onPositionSheetClose(false) },
            onMainPositionClick = onMainPositionClick,
            onSubPositionClick = onSubPositionClick,
            onDelete = onDelete,
            onReset = onPositionSheetReset,
            onApply = onPositionApply,
        )
    }

    if(homeState.isPartyTypeSheetOpenRecruitment){
        PartyTypeModal(
            titleText = stringResource(id = R.string.recruitment_filter2),
            selectedPartyType = homeState.selectedPartyTypeListRecruitment,
            onClick = { onPartyTypeSheetClick(it) },
            onModelClose = { onPartyTypeFilterClick(false) },
            onReset = onPartyTypeSheetReset,
            onApply = onPartyTypeSheetApply,
        )
    }
}