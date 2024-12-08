package com.party.presentation.screen.home.tab_party

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.PartyListItem1
import com.party.common.component.chip.Chip
import com.party.common.component.no_data.NoDataColumn
import com.party.common.snackBarMessage
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyList
import com.party.presentation.enum.PartyType
import com.party.presentation.enum.StatusType
import com.party.presentation.screen.home.tab_party.component.FilterArea
import com.party.presentation.screen.home.tab_party.component.FilterDate
import com.party.presentation.screen.home.tab_recruitment.PartyTypeModal
import com.party.presentation.screen.home.tab_recruitment.validSelectedPartyType
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.shared.SharedViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PartyArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    sharedViewModel: SharedViewModel,
    onClick: (Int) -> Unit,
) {
    DisposableEffect(Unit) {
        onDispose {
            sharedViewModel.isScrollPartyArea = false
        }
    }

    var isPartyTypeSheetOpen by rememberSaveable { mutableStateOf(false) }

    // 선택된 파티 타입 리스트
    val selectedPartyTypeList by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    var checked by remember { mutableStateOf(true) }

    // 등록일 순 내림 차순
    var selectedCreateDataOrderByDesc by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        homeViewModel.getPartyList(page = 1, size = 10, sort = "createdAt", order = "DESC", titleSearch = null, status = null)
    }

    val getPartyListState by homeViewModel.getPartyListState.collectAsStateWithLifecycle()
    val partyListResponse = getPartyListState.data

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FilterArea(
            checked = checked,
            onToggle = { checked = it },
            isPartyTypeFilterClick = {
                isPartyTypeSheetOpen = it
            }
        )

        FilterDate(
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            onChangeOrderBy = { selectedCreateDataOrderByDesc = it }
        )
        HeightSpacer(heightDp = 12.dp)

        when(getPartyListState){
            is UIState.Idle -> {}
            is UIState.Loading -> { LoadingProgressBar() }
            is UIState.Success -> {
                val successResult = partyListResponse as SuccessResponse<PartyList>
                PartyListArea(
                    partyListResponse = successResult.data,
                    selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
                    checked = checked,
                    sharedViewModel = sharedViewModel,
                    onClick = onClick
                )
            }
            is UIState.Error -> {}
            is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
        }
    }

    if(isPartyTypeSheetOpen){
        PartyTypeModal(
            titleText = stringResource(id = R.string.recruitment_filter2),
            selectedPartyType = selectedPartyTypeList,
            onClick = { validSelectedPartyType(selectedPartyTypeList, it) },
            onModelClose = { isPartyTypeSheetOpen = false },
            onReset = { selectedPartyTypeList.clear() },
            onApply = {
                isPartyTypeSheetOpen = false
                homeViewModel.getPartyList(
                    page = 1,
                    size = 20,
                    sort = "createdAt",
                    order = "DESC",
                    partyTypes = convertToIntList(selectedPartyTypeList),
                    titleSearch = null,
                    status = null,
                )
            }
        )
    }
}

fun convertToIntList(selectedPartyTypeList: MutableList<String>): List<Int> {
    return selectedPartyTypeList.mapNotNull { name ->
        PartyType.entries.find { it.type == name }?.id
    }
}

@Composable
private fun PartyListArea(
    partyListResponse: PartyList?,
    selectedCreateDataOrderByDesc: Boolean,
    checked: Boolean,
    sharedViewModel: SharedViewModel,
    onClick: (Int) -> Unit,
) {
    val listState = rememberLazyGridState()
    val isFabVisible = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    sharedViewModel.isScrollPartyArea = isFabVisible.value

    LaunchedEffect(Unit) {
        sharedViewModel.scrollToUp.collectLatest {
            listState.animateScrollToItem(0)
        }
    }

    val list = partyListResponse?.parties?.let { parties ->
        if (selectedCreateDataOrderByDesc) {
            parties.sortedByDescending { it.createdAt } // 내림차순 정렬
        } else {
            parties.sortedBy { it.createdAt } // 오름차순 정렬
        }
    } ?: emptyList()

    val list2 = list.filter {
        if (checked) {
            it.status == "active"
        } else {
            it.status != "active"
        }
    }

    if(list2.isEmpty()){
        HeightSpacer(heightDp = 76.dp)
        NoDataColumn(title = "파티가 없어요.")
    }else{
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(
                items = list2,
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
                            containerColor = if(item.status == StatusType.ACTIVE.type) Color(0xFFD5F0E3) else GRAY600,
                            contentColor = if(item.status == StatusType.ACTIVE.type) Color(0xFF016110) else WHITE,
                            text = if (item.status == StatusType.ACTIVE.type) "진행중" else "종료",
                        )
                    },
                    onClick = { onClick(item.id) }
                )
            }
        }
    }
}