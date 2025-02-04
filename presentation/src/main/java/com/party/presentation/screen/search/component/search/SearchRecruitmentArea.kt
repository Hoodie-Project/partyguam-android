package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.component.RecruitmentListItem2
import com.party.common.component.bottomsheet.PositionBottomSheet
import com.party.common.component.no_data.NoDataColumn
import com.party.common.component.party_filter.PositionAndPartyTypeAndOrderByArea
import com.party.domain.model.party.RecruitmentItem
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.party.RecruitmentParty
import com.party.domain.model.party.RecruitmentPartyType
import com.party.domain.model.party.RecruitmentPosition
import com.party.presentation.screen.home.tab_recruitment.PartyTypeModal
import com.party.presentation.screen.search.SearchState

@Composable
fun SearchRecruitmentArea(
    searchState: SearchState,
    onPartyTypeModel: (Boolean) -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
    onClick: (String) -> Unit,
    onReset: () -> Unit,
    onPartyTypeApply2: () -> Unit,
    onPositionSheetClose: (Boolean) -> Unit,
    onPositionSheetClick: () -> Unit,
    onMainPositionClick: (String) -> Unit,
    onSubPositionClick: (String) -> Unit,
    onDelete: (Pair<String, String>) -> Unit,
    onPositionApply: () -> Unit,
    onRecruitmentClick: (Int, Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PositionAndPartyTypeAndOrderByArea(
            isDescRecruitment = searchState.isDescRecruitment,
            onPositionSheetClick = onPositionSheetClick,
            onPartyTypeFilterClick = { onPartyTypeModel(true) },
            onChangeOrderBy = onChangeOrderBy,
            selectedPartyTypeCount = searchState.selectedTypeListSize,
        )

        HeightSpacer(heightDp = 12.dp)

        when {
            searchState.isLoadingRecruitment -> LoadingProgressBar()
            searchState.recruitmentSearchedList.partyRecruitments.isEmpty() -> NoDataColumn(title = "모집공고가 없어요.", modifier = Modifier.padding(60.dp))
            searchState.recruitmentSearchedList.partyRecruitments.isNotEmpty() -> {
                RecruitmentListArea(
                    recruitmentList = searchState.recruitmentSearchedList.partyRecruitments,
                    onRecruitmentClick = onRecruitmentClick
                )
            }
        }
    }

    if(searchState.isPositionSheetOpen){
        PositionBottomSheet(
            selectedMainPosition = searchState.selectedMainPosition,
            getSubPositionList = searchState.getSubPositionList.map { it.sub to it.id },
            selectedSubPositionList = searchState.selectedSubPositionList.map { it.sub to it.id },
            selectedMainAndSubPositionList = searchState.selectedMainAndSubPosition,
            onSheetClose = { onPositionSheetClose(false) },
            onMainPositionClick = onMainPositionClick,
            onSubPositionClick = onSubPositionClick,
            onDelete = onDelete,
            onReset = onReset,
            onApply = onPositionApply,
        )
    }

    if(searchState.isPartyTypeSheetOpenRecruitment){
        PartyTypeModal(
            titleText = stringResource(id = R.string.recruitment_filter2),
            selectedPartyType = searchState.selectedTypeListRecruitment,
            onClick = { onClick(it) },
            onModelClose = { onPartyTypeModel(false) },
            onReset = onReset,
            onApply = onPartyTypeApply2,
        )
    }
}

@Composable
private fun RecruitmentListArea(
    recruitmentList: List<RecruitmentItem>,
    onRecruitmentClick: (Int, Int) -> Unit,
) {
    if(recruitmentList.isNotEmpty()){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = recruitmentList,
                key = { index, _ ->
                    index
                }
            ){ _, item ->
                RecruitmentListItem2(
                    id = item.id,
                    partyId = item.party.id,
                    imageUrl = item.party.image,
                    category = item.party.partyType.type,
                    title = item.party.title,
                    main = item.position.main,
                    sub = item.position.sub,
                    recruitingCount = item.recruitingCount,
                    recruitedCount = item.recruitedCount,
                    onClick = onRecruitmentClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchRecruitmentAreaPreview1() {
    SearchRecruitmentArea(
        onClick = {},
        onReset = {},
        onPartyTypeApply2 = {},
        onPartyTypeModel = {},
        onChangeOrderBy = {},
        searchState = SearchState(),
        onPositionSheetClose = {},
        onPositionSheetClick = {},
        onMainPositionClick = {},
        onSubPositionClick = {},
        onDelete = {},
        onPositionApply = {},
        onRecruitmentClick = { _ , _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchRecruitmentAreaPreview2() {
    SearchRecruitmentArea(
        onClick = {},
        onReset = {},
        onPartyTypeApply2 = {},
        onPartyTypeModel = {},
        onChangeOrderBy = {},
        searchState = SearchState(
            recruitmentSearchedList = RecruitmentList(
                total = 8358,
                partyRecruitments = listOf(
                    RecruitmentItem(
                        id = 5240,
                        recruitingCount = 9385,
                        recruitedCount = 4189,
                        content = "viverra",
                        createdAt = "discere",
                        party = RecruitmentParty(
                            id = 4570,
                            title = "veniam",
                            image = null,
                            partyType = RecruitmentPartyType(
                                id = 5355,
                                type = "dolore"
                            )
                        ),
                        position = RecruitmentPosition(
                            id = 4195,
                            main = "eloquentiam",
                            sub = "reprehendunt"
                        )
                    )
                )
            ),
        ),
        onPositionSheetClose = {},
        onPositionSheetClick = {},
        onMainPositionClick = {},
        onSubPositionClick = {},
        onDelete = {},
        onPositionApply = {},
        onRecruitmentClick = { _ , _ -> }
    )
}