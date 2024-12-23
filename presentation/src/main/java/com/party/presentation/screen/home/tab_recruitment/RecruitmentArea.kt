package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.bottomsheet.PositionBottomSheet
import com.party.presentation.screen.home.HomeState
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.shared.SharedViewModel

@Composable
fun RecruitmentArea(
    homeState: HomeState,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    sharedViewModel: SharedViewModel,
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

    DisposableEffect(Unit) {
        onDispose {
            sharedViewModel.isScrollRecruitmentArea = false
        }
    }

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
            homeState = homeState,
            onRecruitmentItemClick = onRecruitmentItemClick,
            sharedViewModel = sharedViewModel
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