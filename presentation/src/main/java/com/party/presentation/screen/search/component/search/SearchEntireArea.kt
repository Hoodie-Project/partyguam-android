package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.component.PartyListItem1
import com.party.common.component.RecruitmentListItem1
import com.party.common.component.no_data.NoDataColumn
import com.party.domain.model.search.Party
import com.party.domain.model.search.PartyType
import com.party.domain.model.search.Position
import com.party.domain.model.search.SearchedPartyData
import com.party.domain.model.search.SearchedRecruitmentData

@Composable
fun SearchEntireArea(
    partyList: List<SearchedPartyData>,
    recruitmentList: List<SearchedRecruitmentData>,
) {
    Column {
        SearchedContentTitle(
            title = "파티",
            onClick = {}
        )
        HeightSpacer(heightDp = 20.dp)
        SearchedPartyList(
            partyList = partyList
        )

        HeightSpacer(heightDp = 60.dp)

        SearchedContentTitle(
            title = "모집공고",
            onClick = {}
        )
        HeightSpacer(heightDp = 20.dp)
        SearchedRecruitmentList(
            recruitmentList = recruitmentList
        )
    }
}

@Composable
private fun SearchedPartyList(
    partyList: List<SearchedPartyData>,
) {
    if(partyList.isNotEmpty()){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = partyList,
                key = { index, _ ->
                    index
                }
            ) { _, item ->
                PartyListItem1(
                    imageUrl = item.image,
                    type = item.tag,
                    title = item.title,
                    recruitmentCount = item.recruitmentCount,
                    onClick = {}
                )
            }
        }
    }else {
        NoDataColumn(
            title = "파티가 없어요",
        )
    }

}

@Composable
private fun SearchedRecruitmentList(
    recruitmentList: List<SearchedRecruitmentData>
) {
    if(recruitmentList.isNotEmpty()){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = recruitmentList,
                key = { index, _ ->
                    index
                }
            ) { _, item ->
                RecruitmentListItem1(
                    imageUrl = item.party.image,
                    category = item.party.partyType.type,
                    title = item.party.title,
                    main = item.position.main,
                    sub = item.position.sub,
                    recruitingCount = 1,
                    recruitedCount = 0
                )
            }
        }
    }else {
        NoDataColumn(title = "모집공고가 없어요")
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchEntireAreaPreview() {
    SearchEntireArea(
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
                status = "상태",
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
                status = "상태",
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
                status = "상태",
                createdAt = "2024-11-29T16:30:30.171Z",
                updatedAt = "2024-11-29T16:30:30.171Z",
                recruitmentCount = 1
            ),
        ),
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

@Preview(showBackground = true)
@Composable
private fun SearchEntireAreaPreview2() {
    SearchEntireArea(
        partyList = emptyList(),
        recruitmentList = emptyList()
    )
}