package com.party.presentation.screen.profile_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.Screens
import com.party.common.component.button.CustomButton
import com.party.guam.design.GRAY100
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.enum.OrderDescType
import com.party.presentation.screen.profile.UserProfileState
import com.party.presentation.screen.profile_edit.component.EditArea
import com.party.presentation.screen.profile_edit.component.ProfileEditScaffoldArea
import com.party.presentation.screen.profile_edit.component.UserProfileInfoArea
import com.party.presentation.screen.profile_edit.viewmodel.ProfileEditViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileEditScreenRoute(
    navController: NavHostController,
    profileEditViewModel: ProfileEditViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        profileEditViewModel.getUserProfile()
        profileEditViewModel.getMyParty(1, 50, "createdAt", OrderDescType.DESC.type)
    }

    LaunchedEffect(key1 = Unit) {
        profileEditViewModel.successState.collectLatest {
            navController.popBackStack()
        }
    }

    val userProfileState by profileEditViewModel.state.collectAsStateWithLifecycle()

    ProfileEditScreen(
        userProfileState = userProfileState,
        onNavigationClick = { navController.popBackStack() },
        onGotoProfileEditCareer = { navController.navigate(Screens.ProfileEditCareer)},
        onGotoProfileEditLocation = { navController.navigate(Screens.ProfileEditLocation)},
        onGotoProfileEditTime = { navController.navigate(Screens.ProfileEditTime)},
        onGotoProfileEditTendency = { navController.navigate(Screens.ProfileEditTendency)},
        onGotoProfileEditPortfolio = { navController.navigate(Screens.ProfileEditPortfolio)},
        onGotoWebView = { webViewUrl -> navController.navigate(Screens.WebView(webViewUrl = webViewUrl))},
        onAction = { action ->
            when(action){
                is ProfileEditAction.OnChangeImage -> profileEditViewModel.onAction(action)
                is ProfileEditAction.OnChangeGenderVisible -> profileEditViewModel.onAction(action)
                is ProfileEditAction.OnChangeBirthVisible -> profileEditViewModel.onAction(action)
                is ProfileEditAction.OnModify -> profileEditViewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun ProfileEditScreen(
    userProfileState: UserProfileState,
    onNavigationClick: () -> Unit,
    onGotoProfileEditCareer: () -> Unit,
    onGotoProfileEditLocation: () -> Unit,
    onGotoProfileEditTime: () -> Unit,
    onGotoProfileEditTendency: () -> Unit,
    onGotoProfileEditPortfolio: () -> Unit,
    onGotoWebView: (String) -> Unit,
    onAction: (ProfileEditAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ProfileEditScaffoldArea(
                onNavigationClick = onNavigationClick,
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                // 유저 정보
                HeightSpacer(heightDp = 16.dp)
                UserProfileInfoArea(
                    userProfileState = userProfileState,
                    onSetImage = { image -> onAction(ProfileEditAction.OnChangeImage(image)) },
                    onChangeGenderVisible = { onAction(ProfileEditAction.OnChangeGenderVisible) },
                    onChangeBirthVisible = { onAction(ProfileEditAction.OnChangeBirthVisible) }
                )

                // 구분선
                HeightSpacer(heightDp = 32.dp)
                HorizontalDivider(
                    color = GRAY100,
                    thickness = 12.dp,
                )

                // 수정영역
                EditArea(
                    userProfileState = userProfileState,
                    onGotoProfileEditCareer = onGotoProfileEditCareer,
                    onGotoProfileEditLocation = onGotoProfileEditLocation,
                    onGotoProfileEditTime = onGotoProfileEditTime,
                    onGotoProfileEditTendency = onGotoProfileEditTendency,
                    onGotoProfileEditPortfolio = onGotoProfileEditPortfolio,
                    onGotoWebView = onGotoWebView
                )
            }

            // 수정하기 버튼
            CustomButton(
                onClick = { onAction(ProfileEditAction.OnModify) },
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = MEDIUM_PADDING_SIZE),
                buttonText = "수정하기",
                textWeight = FontWeight.Bold
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditScreenPreview() {
    ProfileEditScreen(
        userProfileState = UserProfileState(),
        onNavigationClick = {},
        onGotoProfileEditCareer = {},
        onGotoProfileEditLocation = {},
        onGotoProfileEditTime = {},
        onGotoProfileEditTendency = {},
        onGotoProfileEditPortfolio = {},
        onAction = {},
        onGotoWebView = {}
    )
}