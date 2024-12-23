package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.component.RecruitmentListItem2
import com.party.common.component.no_data.NoDataColumn
import com.party.presentation.screen.home.HomeState

@Composable
fun RecruitmentColumnListArea(
    listState: LazyListState,
    homeState: HomeState,
    onRecruitmentItemClick: (Int, Int) -> Unit,
) {
    if(homeState.recruitmentList.partyRecruitments.isEmpty()){
        HeightSpacer(heightDp = 76.dp)
        NoDataColumn(title = "모집공고가 없어요.")
    }else {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(
                items = homeState.recruitmentList.partyRecruitments,
                key = { index, _ ->
                    index
                }
            ){_, item ->
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
                    onClick = onRecruitmentItemClick
                )
            }
        }
    }
}