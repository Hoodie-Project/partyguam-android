package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.common.ui.theme.GRAY100
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyType
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.user.PartyAuthority
import com.party.presentation.screen.party_detail.tab.PartyDetailTabArea
import com.party.presentation.screen.party_detail.tab.home.PartyDetailDescriptionArea
import com.party.presentation.screen.party_detail.tab.member.PartyDetailUserArea
import com.party.presentation.screen.party_detail.tab.recruitment.PartyDetailRecruitmentArea

@Composable
fun PartyDetailArea(
    snackBarHostState: SnackbarHostState,
    partyDetailTabList: List<String>,
    partyDetail: PartyDetail,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
    partyUsersState: UIState<ServerApiResponse<PartyUsers>>,
    partyRecruitmentState: UIState<ServerApiResponse<List<PartyRecruitment>>>,
    partyAuthorityState: UIState<ServerApiResponse<PartyAuthority>>,
    selectedPosition: String,
    onReset: () -> Unit,
    onApply: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            PartyDetailImageArea(
                image = partyDetail.image
            )
            HeightSpacer(heightDp = 20.dp)
        }
        item {
            PartyDetailCategoryArea(
                tag = partyDetail.tag,
                partyType = partyDetail.partyType.type
            )
            HeightSpacer(heightDp = 12.dp)
        }

        item {
            PartyDetailTitleArea(
                title = partyDetail.title
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
                        content = partyDetail.content
                    )
                }
                partyDetailTabList[1] -> {
                    PartyDetailUserArea(
                        snackBarHostState = snackBarHostState,
                        partyUsersState = partyUsersState,
                        partyAuthorityState = partyAuthorityState
                    )
                }
                partyDetailTabList[2] -> {
                    PartyDetailRecruitmentArea(
                        snackBarHostState = snackBarHostState,
                        partyRecruitmentState = partyRecruitmentState,
                        selectedPosition = selectedPosition,
                        onReset = onReset,
                        onApply = onApply,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PartyDetailAreaPreview() {
    val partyDetail = PartyDetail(
        id = 5686,
        partyType = PartyType(id = 9282, type = "포트폴리오"),
        tag = "모집중",
        title = "파티 제목 입니다~~~~~~~",
        content = "새로운 프로젝트를 위해 모여 함께 아이디어를 나누고 계획을 세우는 파티를 개최합니다!. 새로운 프로젝트를 위해 모여 함께 아이디어를 나눕시다!",
        image = "viris",
        status = "error",
        createdAt = "recteque",
        updatedAt = "venenatis"
    )

    PartyDetailArea(
        snackBarHostState = SnackbarHostState(),
        partyDetailTabList = listOf("홈", "파티원", "모집공고"),
        partyDetail = partyDetail,
        selectedTabText = "홈",
        onTabClick = {},
        partyUsersState = UIState.Idle,
        partyRecruitmentState = UIState.Idle,
        partyAuthorityState = UIState.Idle,
        onApply = {},
        onReset = {},
        selectedPosition = "개발자"
    )
}