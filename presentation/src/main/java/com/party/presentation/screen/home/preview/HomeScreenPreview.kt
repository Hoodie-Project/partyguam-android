package com.party.presentation.screen.home.preview

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.party.domain.model.party.PartyItemResponse
import com.party.domain.model.party.PartyTypeItemResponse
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentPartyResponse
import com.party.domain.model.party.PersonalRecruitmentPartyTypeResponse
import com.party.domain.model.party.PersonalRecruitmentPositionResponse
import com.party.presentation.screen.home.HomeScreen
import com.party.presentation.screen.home.tab_main.PartyItem
import com.party.presentation.screen.home.tab_main.PersonalRecruitmentItem

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        snackBarHostState = SnackbarHostState(),
        navController = rememberNavController(),
    )
}

@Preview(showBackground = true)
@Composable
fun PersonalRecruitmentItemPreview() {
    PersonalRecruitmentItem(
        personalRecruitmentLisItemResponse = PersonalRecruitmentItemResponse(
            id = 1,
            partyId = 1,
            positionId = 1,
            content = "내용",
            recruitingCount = 1,
            recruitedCount = 1,
            createdAt = "2021-09-01",
            party = PersonalRecruitmentPartyResponse(
                title = "파티 제목",
                image = "https://picsum.photos/200/300",
                partyType = PersonalRecruitmentPartyTypeResponse(
                    id = 1,
                    type = "타입"
                )
            ),
            position = PersonalRecruitmentPositionResponse(
                id = 1,
                main = "메인",
                sub = "서브"
            )
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PersonalRecruitmentItemPreview2() {
    PersonalRecruitmentItem(
        personalRecruitmentLisItemResponse = PersonalRecruitmentItemResponse(
            id = 1,
            partyId = 1,
            positionId = 1,
            content = "내용",
            recruitingCount = 1,
            recruitedCount = 1,
            createdAt = "2021-09-01",
            party = PersonalRecruitmentPartyResponse(
                title = "파티 제목파티 제목파티 제목파티 제목파티 제목파티 제목파티 제목123",
                image = "https://picsum.photos/200/300",
                partyType = PersonalRecruitmentPartyTypeResponse(
                    id = 1,
                    type = "타입"
                )
            ),
            position = PersonalRecruitmentPositionResponse(
                id = 1,
                main = "메인",
                sub = "서브"
            )
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PartyItemPreview() {
    PartyItem(
        partyItemResponse = PartyItemResponse(
            id = 1,
            partyType = PartyTypeItemResponse(
                id = 1,
                type = "타입"
            ),
            tag = "태그",
            title = "제목",
            content = "내용",
            image = "https://picsum.photos/200/300",
            status = "상태",
            createdAt = "2021-09-01",
            updatedAt = "2021-09-01",
            recruitmentCount = 2
        ),
        onClick = {}
    )
}