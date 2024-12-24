package com.party.presentation.screen.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.party.domain.model.user.profile.UserProfile
import com.party.navigation.BottomNavigationBar
import com.party.presentation.screen.profile.component.ProfileScaffoldArea
import com.party.presentation.screen.profile.component.ProfileTopArea
import com.party.presentation.screen.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    context: Context,
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val userProfileState by profileViewModel.state.collectAsStateWithLifecycle()

    ProfileScreenContent(
        context = context,
        navController = navController,
        userProfileState = userProfileState,
    )
}

@Composable
fun ProfileScreenContent(
    context: Context,
    navController: NavHostController,
    userProfileState: UserProfileState,
) {
    Scaffold(
        topBar = {
            ProfileScaffoldArea(
                onGoToAlarm = {},
                onGoSetting = {},
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
        ) {

            // 유저 프로필 영역
            HeightSpacer(heightDp = 16.dp)
            ProfileTopArea(
                userProfileState = userProfileState,
                onProfileEditClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenContentPreview() {
    ProfileScreenContent(
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
                userPersonalities = emptyList(),
                userCareers = emptyList(),
                userLocations = emptyList()
            )
        )
    )
}