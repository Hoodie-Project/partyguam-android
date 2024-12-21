package com.party.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PersonalRecruitmentItem
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.model.party.PersonalRecruitmentParty
import com.party.domain.model.party.PersonalRecruitmentPartyType
import com.party.domain.model.party.PersonalRecruitmentPosition
import com.party.presentation.screen.home.component.HomeTopTabArea
import com.party.presentation.screen.home.tab_main.PersonalRecruitmentListArea
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val fakeUiState: MutableState<UIState<ServerApiResponse<PersonalRecruitmentList>>> = mutableStateOf(UIState.Loading)

    @Test
    fun 홈화면의_상단탭에는_메인_파티_모집공고만_보여야한다(){
        val homeTopTabList = listOf(
            "메인",
            "파티",
            "모집공고",
        )

        composeTestRule.setContent {
            HomeTopTabArea(
                homeTopTabList = homeTopTabList,
                selectedTabText = homeTopTabList[0],
                onTabClick = {}
            )
        }

        composeTestRule.onNodeWithText("메인").assertExists()
        composeTestRule.onNodeWithText("파티").assertExists()
        composeTestRule.onNodeWithText("모집공고").assertExists()
    }

    @Test
    fun 로딩_상태일때는_맞춤공고_리스트가_노출되지_않는다() {
        composeTestRule.setContent {
            PersonalRecruitmentListArea(
                personalRecruitmentListResponse = personalRecruitmentListResponse
            )
        }

        // when
        fakeUiState.value = UIState.Loading

        // then
        composeTestRule
            .onNodeWithText("파티 제목")
            .assertDoesNotExist()
    }

    @Test
    fun UIState가_Success_상태이면_맞춤모집공고_데이터가_화면에_노출된다(){
        composeTestRule.setContent {
            PersonalRecruitmentListArea(
                personalRecruitmentListResponse = personalRecruitmentListResponse
            )
        }

        // when
        val fakeData: ServerApiResponse<PersonalRecruitmentList> = ServerApiResponse.SuccessResponse(
            PersonalRecruitmentList(
                partyRecruitments = listOf(personalRecruitmentLisItemResponse),
                total = 1
            )
        )
        fakeUiState.value = UIState.Success(fakeData)

        // then
        composeTestRule
            .onNodeWithText("파티를 모집합니다")
            .assertExists()

        // then
        composeTestRule
            .onNodeWithText("개발자 | Android")
            .assertExists()
    }

    /*@Test
    fun 홈화면에서_Floating_Button_이_보여야한다(){
        // then
        composeTestRule
            .onNodeWithTag("test_floating_button11")
            .assertExists()
    }*/

    companion object {
        private val personalRecruitmentLisItemResponse = PersonalRecruitmentItem(
            id = 1,
            partyId = 1,
            positionId = 1,
            content = "내용",
            recruitingCount = 1,
            recruitedCount = 1,
            createdAt = "2021-09-01",
            party = PersonalRecruitmentParty(
                title = "파티를 모집합니다",
                image = "https://picsum.photos/200/300",
                partyType = PersonalRecruitmentPartyType(
                    id = 1,
                    type = "타입"
                )
            ),
            position = PersonalRecruitmentPosition(
                id = 1,
                main = "개발자",
                sub = "Android"
            )
        )

        private val personalRecruitmentListResponse = PersonalRecruitmentList(
            partyRecruitments = listOf(personalRecruitmentLisItemResponse),
            total = 1
        )
    }
}