package com.party.presentation.screen.home.preview

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentPartyResponse
import com.party.domain.model.party.PersonalRecruitmentPositionResponse
import com.party.presentation.screen.home.HomeScreen
import com.party.presentation.screen.home.tab_main.PersonalRecruitmentItem

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        context = LocalContext.current,
        snackBarHostState = SnackbarHostState(),
        navController = rememberNavController()
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
                image = "https://picsum.photos/200/300"
            ),
            position = PersonalRecruitmentPositionResponse(
                id = 1,
                main = "메인",
                sub = "서브"
            )
        )
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
                image = "https://picsum.photos/200/300"
            ),
            position = PersonalRecruitmentPositionResponse(
                id = 1,
                main = "메인",
                sub = "서브"
            )
        )
    )
}