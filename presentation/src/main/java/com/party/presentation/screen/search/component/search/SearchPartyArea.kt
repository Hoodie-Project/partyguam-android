package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.PartyListItem1
import com.party.common.component.chip.Chip
import com.party.common.component.no_data.NoDataColumn
import com.party.common.component.party_filter.PartyTypeAndIngAndOrderByFilterArea
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyItem
import com.party.domain.model.party.PartyTypeItem
import com.party.domain.model.search.PartyType
import com.party.domain.model.search.SearchedPartyData
import com.party.presentation.screen.home.tab_recruitment.PartyTypeModal
import com.party.presentation.screen.home.tab_recruitment.validSelectedPartyType

@Composable
fun SearchPartyArea(
    partyList: List<PartyItem>,
    onPartyTypeApply: (MutableList<String>) -> Unit,
) {
    // 파티 타입 시트 오픈 여부
    var isPartyTypeSheetOpen by rememberSaveable { mutableStateOf(false) }

    // 등록일 순 내림 차순
    var selectedCreateDataOrderByDesc by remember {
        mutableStateOf(true)
    }

    var checked by remember { mutableStateOf(true) }

    // 선택된 파티 타입 리스트
    val selectedPartyTypeList by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PartyTypeAndIngAndOrderByFilterArea(
            checked = checked,
            onToggle = { checked = it },
            isPartyTypeFilterClick = {},
            selectedCreateDataOrderByDesc = selectedCreateDataOrderByDesc,
            onChangeOrderBy = {}
        )

        HeightSpacer(heightDp = 12.dp)
        PartyListArea(
            partyList = partyList
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
                isPartyTypeSheetOpen = false
                onPartyTypeApply(selectedPartyTypeList)
                /*homeViewModel.getPartyList(
                    page = 1,
                    size = 20,
                    sort = "createdAt",
                    order = "DESC",
                    partyTypes = convertToIntList(selectedPartyTypeList)
                )*/
            }
        )
    }

}

@Composable
private fun PartyListArea(
    partyList: List<PartyItem>,
) {
    val listState = rememberLazyGridState()

    if(partyList.isNotEmpty()){
        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(
                items = partyList,
                key = { index, _ ->
                    index
                }
            ) { _, item ->
                PartyListItem1(
                    category = item.tag,
                    title = item.title,
                    recruitmentCount = item.recruitmentCount,
                    typeChip = {
                        Chip(
                            containerColor = if(item.status == "진행중") Color(0xFFD5F0E3) else GRAY600,
                            contentColor = if(item.status == "진행중") Color(0xFF016110) else WHITE,
                            text = item.status,
                        )
                    },
                    onClick = {}
                )
            }
        }
    }else{
        NoDataColumn(
            title = "파티가 없어요.",
            modifier = Modifier
                .padding(60.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchPartyAreaPreview1(
    modifier: Modifier = Modifier
) {
    SearchPartyArea(
        partyList = listOf(
            PartyItem(
                id = 8013,
                partyType = PartyTypeItem(id = 8792, type = "nostra"),
                tag = "consectetuer",
                title = "consectetuer",
                content = "laudem",
                image = null,
                status = "consectetur",
                createdAt = "brute",
                updatedAt = "fringilla",
                recruitmentCount = 8432
            )

        ),
        onPartyTypeApply = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchPartyAreaPreview2(
    modifier: Modifier = Modifier
) {
    SearchPartyArea(
        partyList = emptyList(),
        onPartyTypeApply = {}
    )
}