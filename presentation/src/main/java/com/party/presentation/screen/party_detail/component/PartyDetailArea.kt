package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.ui.theme.GRAY100
import com.party.presentation.enum.StatusType
import com.party.presentation.screen.party_detail.PartyDetailState
import com.party.presentation.screen.party_detail.tab.PartyDetailTabArea
import com.party.presentation.screen.party_detail.tab.home.PartyDetailDescriptionArea
import com.party.presentation.screen.party_detail.tab.member.PartyDetailUserArea
import com.party.presentation.screen.party_detail.tab.recruitment.PartyDetailRecruitmentArea

@Composable
fun PartyDetailArea(
    state: PartyDetailState,
    partyDetailTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
    onShowPositionFilter: (Boolean) -> Unit,
    onPositionClick: (String) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
    onAddRecruitment: () -> Unit,
    onChangeOrderBy: (Boolean) -> Unit,
    onChangeProgress: (Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            PartyDetailImageArea(
                image = state.partyDetail.image
            )
            HeightSpacer(heightDp = 20.dp)
        }
        item {
            PartyDetailCategoryArea(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                tag = StatusType.fromType(state.partyDetail.status).toDisplayText(),
                partyType = state.partyDetail.partyType.type
            )
            HeightSpacer(heightDp = 12.dp)
        }

        item {
            PartyDetailTitleArea(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                title = state.partyDetail.title,
            )
            HeightSpacer(heightDp = 32.dp)

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = GRAY100,
                thickness = 12.dp,
            )
        }

        item {
            PartyDetailTabArea(
                partyDetailTabList = partyDetailTabList,
                selectedTabText = selectedTabText,
                onTabClick = {onTabClick(it)}
            )
            HeightSpacer(heightDp = 24.dp)
        }

        item {
            when (selectedTabText) {
                partyDetailTabList[0] -> {
                    PartyDetailDescriptionArea(
                        modifier = Modifier
                            .padding(horizontal = 20.dp),
                        content = state.partyDetail.content
                    )
                }
                partyDetailTabList[1] -> {
                    PartyDetailUserArea(
                        state = state,
                    )
                }
                partyDetailTabList[2] -> {
                    PartyDetailRecruitmentArea(
                        state = state,
                        onShowPositionFilter = onShowPositionFilter,
                        onPositionClick = onPositionClick,
                        onReset = onReset,
                        onApply = onApply,
                        onAddRecruitment = onAddRecruitment,
                        onChangeOrderBy = onChangeOrderBy,
                        onChangeProgress = onChangeProgress
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailAreaPreview() {
    PartyDetailArea(
        state = PartyDetailState(),
        partyDetailTabList = listOf("홈", "파티원", "모집공고"),
        selectedTabText = "홈",
        onTabClick = {},
        onApply = {},
        onReset = {},
        onAddRecruitment = {},
        onChangeOrderBy = {},
        onShowPositionFilter = {},
        onPositionClick = {},
        onChangeProgress = {}
    )
}