package com.party.presentation.screen.profile

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.profile.PersonalityOption
import com.party.domain.model.user.profile.PersonalityQuestion
import com.party.domain.model.user.profile.UserCareer
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserPersonality
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileLocation
import com.party.domain.model.user.profile.UserProfilePosition
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.enum.PersonalityType
import com.party.presentation.screen.profile.component.DetailProfileSettingArea
import com.party.presentation.screen.profile.component.HopeLocationArea
import com.party.presentation.screen.profile.component.HopeTimeArea
import com.party.presentation.screen.profile.component.MyPartyListArea
import com.party.presentation.screen.profile.component.ProfileScaffoldArea
import com.party.presentation.screen.profile.component.ProfileTopArea
import com.party.presentation.screen.profile.component.TendencyArea
import com.party.presentation.screen.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenRoute(
    context: Context,
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        profileViewModel.getUserProfile()
    }

    val userProfileState by profileViewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        context = context,
        navController = navController,
        userProfileState = userProfileState,
        onGoSetting = { navController.navigate(Screens.ManageAuth) },
        onMyPageCardClick = { partyId -> navController.navigate(Screens.PartyDetail(partyId)) },
        onProfileEditClick = { navController.navigate(Screens.ProfileEdit) },
        onClick = { screens -> navController.navigate(screens) }
    )
}

@Composable
private fun ProfileScreen(
    context: Context,
    navController: NavHostController,
    userProfileState: UserProfileState,
    onGoSetting: () -> Unit,
    onMyPageCardClick: (Int) -> Unit,
    onProfileEditClick: () -> Unit,
    onClick: (Screens) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ProfileScaffoldArea(
                onGoToAlarm = {},
                onGoSetting = onGoSetting,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
            )
        }
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

            // 세부프로필 설정하기
            HeightSpacer(heightDp = 40.dp)
            DetailProfileSettingArea(
                userProfileState = userProfileState,
                onClick = onClick
            )

            // 희망장소
            if(userProfileState.userProfile.userLocations.isNotEmpty()){
                HeightSpacer(heightDp = 60.dp)
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
        context = LocalContext.current,
        navController = rememberNavController(),
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
        onGoSetting = {},
        onMyPageCardClick = {},
        onProfileEditClick = {},
        onClick = {}
    )
}