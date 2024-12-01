package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.component.PartyListItem1
import com.party.common.component.chip.Chip
import com.party.common.component.no_data.NoDataColumn
import com.party.common.component.party_filter.PartyTypeAndIngAndOrderByFilterArea
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT
import com.party.common.ui.theme.WHITE
import com.party.domain.model.search.PartyType
import com.party.domain.model.search.SearchedPartyData

@Composable
fun SearchPartyArea(
    partyList: List<SearchedPartyData>,
) {
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

}

@Composable
private fun PartyListArea(
    partyList: List<SearchedPartyData>,
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
                    }
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
            SearchedPartyData(
                id = 1,
                partyType = PartyType(
                    id = 1,
                    type = "파티 타입"
                ),
                tag = "스터디",
                title = "스터디할 사람 구합니다~",
                content = "내용",
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                status = "진행중",
                createdAt = "2024-11-29T16:30:30.171Z",
                updatedAt = "2024-11-29T16:30:30.171Z",
                recruitmentCount = 3
            ),
            SearchedPartyData(
                id = 1,
                partyType = PartyType(
                    id = 1,
                    type = "파티 타입"
                ),
                tag = "포트폴리오",
                title = "같이 포르폴리오 할 사람",
                content = "내용",
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                status = "진행중",
                createdAt = "2024-11-29T16:30:30.171Z",
                updatedAt = "2024-11-29T16:30:30.171Z",
                recruitmentCount = 1
            ),
            SearchedPartyData(
                id = 1,
                partyType = PartyType(
                    id = 1,
                    type = "파티 타입"
                ),
                tag = "태그",
                title = "제목",
                content = "내용",
                image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
                status = "완료",
                createdAt = "2024-11-29T16:30:30.171Z",
                updatedAt = "2024-11-29T16:30:30.171Z",
                recruitmentCount = 1
            ),
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchPartyAreaPreview2(
    modifier: Modifier = Modifier
) {
    SearchPartyArea(
        partyList = emptyList()
    )
}