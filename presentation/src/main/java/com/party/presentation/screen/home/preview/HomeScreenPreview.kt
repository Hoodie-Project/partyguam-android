package com.party.presentation.screen.home.preview

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.party.domain.model.party.PartyItem
import com.party.domain.model.party.PartyTypeItem
import com.party.domain.model.party.PersonalRecruitmentItem
import com.party.domain.model.party.PersonalRecruitmentParty
import com.party.domain.model.party.PersonalRecruitmentPartyType
import com.party.domain.model.party.PersonalRecruitmentPosition
import com.party.presentation.screen.home.HomeScreen
import com.party.presentation.screen.home.PartyCategory
import com.party.presentation.screen.home.tab_main.PartyItem
import com.party.presentation.screen.home.tab_main.PersonalRecruitmentItem
import com.party.presentation.shared.SharedViewModel

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        context = LocalContext.current,
        snackBarHostState = SnackbarHostState(),
        navController = rememberNavController(),
        selectedTabText = "메인",
        homeTopTabList = listOf("메인", "파티", "모집"),
        onTabClick = {},
        onGoRecruitment = {},
        onRecruitmentItemClick = { _, _ -> },
        sharedViewModel = SharedViewModel()
    )
}

@Preview(showBackground = true)
@Composable
fun PersonalRecruitmentItemPreview() {
    PersonalRecruitmentItem(
        personalRecruitmentLisItemResponse = PersonalRecruitmentItem(
            id = 1,
            partyId = 1,
            positionId = 1,
            content = "내용",
            recruitingCount = 1,
            recruitedCount = 1,
            createdAt = "2021-09-01",
            party = PersonalRecruitmentParty(
                title = "파티 제목",
                image = "https://picsum.photos/200/300",
                partyType = PersonalRecruitmentPartyType(
                    id = 1,
                    type = "타입"
                )
            ),
            position = PersonalRecruitmentPosition(
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
        personalRecruitmentLisItemResponse = PersonalRecruitmentItem(
            id = 1,
            partyId = 1,
            positionId = 1,
            content = "내용",
            recruitingCount = 1,
            recruitedCount = 1,
            createdAt = "2021-09-01",
            party = PersonalRecruitmentParty(
                title = "파티 제목파티 제목파티 제목파티 제목파티 제목파티 제목파티 제목123",
                image = "https://picsum.photos/200/300",
                partyType = PersonalRecruitmentPartyType(
                    id = 1,
                    type = "타입"
                )
            ),
            position = PersonalRecruitmentPosition(
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
        partyItemResponse = PartyItem(
            id = 1,
            partyType = PartyTypeItem(
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

@Preview(showBackground = true)
@Composable
fun PartyCategoryPreview(
    modifier: Modifier = Modifier
) {
    PartyCategory(category = "미정")
}