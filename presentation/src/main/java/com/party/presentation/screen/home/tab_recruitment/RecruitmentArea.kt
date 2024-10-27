package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.presentation.screen.home.HomeViewModel

@Composable
fun RecruitmentArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
) {
    var isPositionSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isPartyTypeSheetOpen by rememberSaveable { mutableStateOf(false) }

    val selectedPartyType by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeightSpacer(heightDp = 20.dp)
        SelectFilterArea(
            stringResource(id = R.string.recruitment_filter1),
            stringResource(id = R.string.recruitment_filter2),
            stringResource(id = R.string.recruitment_filter3),
            isPositionFilterClick = { isPositionSheetOpen = it },
            isPartyTypeFilterClick = { isPartyTypeSheetOpen = it },
            isPositionSheetOpen = isPositionSheetOpen,
            isPartyTypeSheetOpen = isPartyTypeSheetOpen
        )
        HeightSpacer(heightDp = 16.dp)
        RecruitmentColumnListArea(
            homeViewModel = homeViewModel,
            snackBarHostState = snackBarHostState
        )
    }

    if(isPartyTypeSheetOpen){
        PartyTypeModal(
            titleText = stringResource(id = R.string.recruitment_filter2),
            selectedPartyType = selectedPartyType,
            onClick = { validSelectedPartyType(selectedPartyType, it) },
            onModelClose = { isPartyTypeSheetOpen = false },
            onReset = { selectedPartyType.clear() },
            onApply = {}
        )
    }
}

fun validSelectedPartyType(
    selectedPartyType: MutableList<String>,
    selectText: String,
) {
    if(selectText == "전체"){
        if(selectedPartyType.contains(selectText)){ // 이미 선택되어 있던거 해제
            selectedPartyType.remove(selectText)
        }else {
            selectedPartyType.clear()
            selectedPartyType.add(selectText)
        }
    }else {
        if(selectedPartyType.contains(selectText)){
            selectedPartyType.remove(selectText)
        }else {
            selectedPartyType.add(selectText)
        }
    }
}