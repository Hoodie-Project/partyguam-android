package com.party.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.Screens
import com.party.common.utils.HeightSpacer
import com.party.domain.model.user.profile.PersonalityOption
import com.party.domain.model.user.profile.PersonalityQuestion
import com.party.domain.model.user.profile.UserCareer
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserPersonality
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileLocation
import com.party.domain.model.user.profile.UserProfilePosition
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.enum.DetailProfileCardType
import com.party.presentation.enum.PersonalityType
import com.party.presentation.screen.profile.component.DetailProfileSettingArea
import com.party.presentation.screen.profile.component.HopeLocationArea
import com.party.presentation.screen.profile.component.HopeTimeArea
import com.party.presentation.screen.profile.component.MyPartyListArea
import com.party.presentation.screen.profile.component.ProfileScaffoldArea
import com.party.presentation.screen.profile.component.ProfileTopArea
import com.party.presentation.screen.profile.component.TendencyArea
import com.party.presentation.screen.profile.component.profileCardTypeList
import com.party.presentation.screen.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenRoute(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        profileViewModel.getUserProfile()
    }

    val userProfileState by profileViewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        userProfileState = userProfileState,
        onGotoNotification = { navController.navigate(Screens.Notification)},
        onGoSetting = { navController.navigate(Screens.ManageAuth) },
        onMyPageCardClick = { partyId -> navController.navigate(Screens.PartyDetail(partyId)) },
        onProfileEditClick = { navController.navigate(Screens.ProfileEdit) },
        onClick = { screens -> navController.navigate(screens) }
    )
}

@Composable
private fun ProfileScreen(
    userProfileState: UserProfileState,
    onGotoNotification: () -> Unit,
    onGoSetting: () -> Unit,
    onMyPageCardClick: (Int) -> Unit,
    onProfileEditClick: () -> Unit,
    onClick: (Screens) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ProfileScaffoldArea(
                onGoToAlarm = onGotoNotification,
                onGoSetting = onGoSetting,
            )
        },
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
                .verticalScroll(scrollState)
        ) {

            // 유저 프로필 영역
            HeightSpacer(heightDp = 16.dp)
            ProfileTopArea(
                userProfileState = userProfileState,
                onProfileEditClick = onProfileEditClick
            )

            val countNotEmpty = profileCardTypeList.count { cardType ->
                when (cardType) {
                    DetailProfileCardType.CAREER_POSITION -> userProfileState.userProfile.userCareers.isNotEmpty()
                    DetailProfileCardType.LIKE_LOCATION -> userProfileState.userProfile.userLocations.isNotEmpty()
                    DetailProfileCardType.LIKE_TIME -> userProfileState.userProfile.userPersonalities.any {
                        it.personalityOption.personalityQuestion.id == PersonalityType.TIME.id
                    }
                    DetailProfileCardType.CHECK_PERSONALITY -> userProfileState.userProfile.userPersonalities.any {
                        it.personalityOption.personalityQuestion.id != PersonalityType.TENDENCY.id
                    }
                }
            }

            if(countNotEmpty in 0.. 3){
                // 세부프로필 설정하기
                HeightSpacer(heightDp = 40.dp)
                DetailProfileSettingArea(
                    userProfileState = userProfileState,
                    onClick = onClick
                )
            }

            // 희망장소
            if(userProfileState.userProfile.userLocations.isNotEmpty()){
                HeightSpacer(heightDp = 40.dp)
                HopeLocationArea(
                    userProfileState = userProfileState
                )
            }

            // 희망시간
            val userPersonalityList = userProfileState.userProfile.userPersonalities.filter { it.personalityOption.personalityQuestion.id == PersonalityType.TIME.id }
            if(userPersonalityList.isNotEmpty()){
                HeightSpacer(heightDp = 60.dp)
                HopeTimeArea(
                    userPersonalityList = userPersonalityList
                )
            }

            // 성향
            val userTendencyList = userProfileState.userProfile.userPersonalities.filter { it.personalityOption.personalityQuestion.id != PersonalityType.TIME.id }
            if(userTendencyList.isNotEmpty()){
                HeightSpacer(heightDp = 60.dp)
                TendencyArea(
                    userTendencyList = userTendencyList
                )
            }

            // 참여파티목록
            HeightSpacer(heightDp = 60.dp)
            MyPartyListArea(
                userProfileState = userProfileState,
                onClick = onMyPageCardClick
            )
            HeightSpacer(heightDp = 20.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenContentPreview() {
    ProfileScreen(
        userProfileState = UserProfileState(
            userProfile = UserProfile(
                nickname = "무화과케이크",
                birth = "1992-03-29",
                birthVisible = true,
                gender = "M",
                genderVisible = true,
                portfolioTitle = "",
                portfolio = "",
                image = "",
                createdAt = "",
                updatedAt = "",
                userPersonalities = listOf(
                    UserPersonality(
                        id = 0,
                        personalityOption = PersonalityOption(
                            id = 0,
                            content = "",
                            personalityQuestion = PersonalityQuestion(
                                id = 0,
                                content = "",
                                responseCount = 0
                            )
                        )
                    )
                ),
                userCareers = listOf(
                    UserCareer(
                        id = 0,
                        years = 2,
                        careerType = "",
                        position = UserProfilePosition(main = "개발자", sub = "안드로이드")
                    )
                ),
                userLocations = listOf(
                    UserLocation(
                        id = 0,
                        location = UserProfileLocation(
                            id = 0,
                            province = "서울",
                            city = "영등포구"
                        )
                    ),
                )
            )
        ),
        onGotoNotification = {},
        onGoSetting = {},
        onMyPageCardClick = {},
        onProfileEditClick = {},
        onClick = {}
    )
}