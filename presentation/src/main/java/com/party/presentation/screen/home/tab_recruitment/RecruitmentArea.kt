package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.party.presentation.screen.detail.choice_carrier_position.positionList
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.shared.SharedViewModel

@Composable
fun RecruitmentArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {

    DisposableEffect(Unit) {
        onDispose {
            sharedViewModel.isScrollRecruitmentArea = false
        }
    }

    var isPositionSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isPartyTypeSheetOpen by rememberSaveable { mutableStateOf(false) }

    // 선택된 메인 포지션
    var selectedMainPosition by rememberSaveable {
        mutableStateOf(positionList[0])
    }

    // 선택된 서브 포지션 리스트
    var selectedSubPositionList by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    // 선택된 포지션 리스트 (메인 + 서브 합친 것)
    val selectedPositionList by remember {
        mutableStateOf(mutableStateListOf<Pair<String, String>>())
    }

    // 선택된 파티 타입 리스트
    val selectedPartyTypeList by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    // 등록일 순 내림 차순
    var selectedCreateDataOrderByDesc by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = selectedMainPosition) {
        homeViewModel.getSubPositionList(main = selectedMainPosition)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeightSpacer(heightDp = 20.dp)
        SelectFilterArea(
            stringResource(id = R.string.recruitment_filter1),
            stringResource(id = R.string.recruitment_filter2),
            stringResource(id = R.string.filter1),
            isPositionFilterClick = { isPositionSheetOpen = it },
            isPartyTypeFilterClick = { isPartyTypeSheetOpen = it },
            isPositionSheetOpen = isPositionSheetOpen,
            isPartyTypeSheetOpen = isPartyTypeSheetOpen,
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            onChangeOrderBy = { selectedCreateDataOrderByDesc = it },
            selectedPartyTypeList = selectedPartyTypeList
        )
        HeightSpacer(heightDp = 16.dp)
        RecruitmentColumnListArea(
            homeViewModel = homeViewModel,
            snackBarHostState = snackBarHostState,
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            selectedPartyType = selectedPartyTypeList,
            onRecruitmentItemClick = onRecruitmentItemClick,
            sharedViewModel = sharedViewModel
        )
    }

    if(isPositionSheetOpen){
        PositionModal(
            snackBarHostState = snackBarHostState,
            titleText = stringResource(id = R.string.recruitment_filter1),
            selectedMainPosition = selectedMainPosition,
            selectedSubPositionList = selectedSubPositionList,
            selectedPositionList = selectedPositionList,
            homeViewModel = homeViewModel,
            onMainPositionClick = {
                selectedMainPosition = it
            },
            onModelClose = { isPositionSheetOpen = false },
            onReset = {
                selectedMainPosition = positionList[0]
                selectedSubPositionList.clear()
                selectedPositionList.clear()
            },
            onApply = {},
            onSelectSubPosition = {
                if (selectedSubPositionList.contains(it)) {
                    selectedSubPositionList.remove(it)
                    selectedPositionList.remove(Pair(selectedMainPosition, it))
                }else{
                    selectedSubPositionList.add(it)
                    selectedPositionList.add(Pair(selectedMainPosition, it))
                }
            },
            onDelete = {
                selectedSubPositionList.remove(it.second)
                selectedPositionList.remove(it)
            }
        )
    }

    if(isPartyTypeSheetOpen){
        PartyTypeModal(
            titleText = stringResource(id = R.string.recruitment_filter2),
            selectedPartyType = selectedPartyTypeList,
            onClick = { validSelectedPartyType(selectedPartyTypeList, it) },
            onModelClose = { isPartyTypeSheetOpen = false },
            onReset = { selectedPartyTypeList.clear() },
            onApply = {

            }
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