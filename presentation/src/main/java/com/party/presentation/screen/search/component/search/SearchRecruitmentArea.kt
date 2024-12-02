package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.RecruitmentListItem2
import com.party.common.component.no_data.NoDataColumn
import com.party.domain.model.search.Party
import com.party.domain.model.search.PartyType
import com.party.domain.model.search.Position
import com.party.domain.model.search.SearchedRecruitmentData

@Composable
fun SearchRecruitmentArea(
    recruitmentList: List<SearchedRecruitmentData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RecruitmentListArea(
            recruitmentList = recruitmentList
        )
    }
}

@Composable
private fun RecruitmentListArea(
    recruitmentList: List<SearchedRecruitmentData>
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
                    imageUrl = item.party.image,
                    category = item.party.partyType.type,
                    title = item.party.title,
                    main = item.position.main,
                    sub = item.position.sub,
                    recruitingCount = item.recruitingCount,
                    recruitedCount = item.recruitedCount
                ) {

                }
            }
        }
    }else {
        NoDataColumn(
            title = "모집공고가 없어요.",
            modifier = Modifier
                .padding(60.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchRecruitmentAreaPreview1() {
    SearchRecruitmentArea(
        recruitmentList = emptyList()
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchRecruitmentAreaPreview2() {
    SearchRecruitmentArea(
        recruitmentList = listOf(
            SearchedRecruitmentData(
                id = 1,
                content = "내용",
                recruitingCount = 1,
                recruitedCount = 0,
                createdAt = "2024-11-29T16:30:30.171Z",
                party = Party(
                    id = 1,
                    title = "안드로이드 개발자 모집합니다",
                    image = "https://cdn",
                    partyType = PartyType(
                        id = 1,
                        type = "포르폴리오"
                    )
                ),
                position = Position(
                    id = 1,
                    main = "개발자",
                    sub = "안드로이드"
                )
            ),
            SearchedRecruitmentData(
                id = 1,
                content = "내용",
                recruitingCount = 1,
                recruitedCount = 0,
                createdAt = "2024-11-29T16:30:30.171Z",
                party = Party(
                    id = 1,
                    title = "같이 해커톤 할 사람이요",
                    image = "https://cdn",
                    partyType = PartyType(
                        id = 1,
                        type = "파티 타입"
                    )
                ),
                position = Position(
                    id = 1,
                    main = "메인",
                    sub = "서브"
                )
            ),
            SearchedRecruitmentData(
                id = 1,
                content = "내용",
                recruitingCount = 1,
                recruitedCount = 0,
                createdAt = "2024-11-29T16:30:30.171Z",
                party = Party(
                    id = 1,
                    title = "같이 개발할 사람",
                    image = "https://cdn",
                    partyType = PartyType(
                        id = 1,
                        type = "해커톤"
                    )
                ),
                position = Position(
                    id = 1,
                    main = "개발자",
                    sub = "안드로이드"
                )
            ),
        )
    )
}