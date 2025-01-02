package com.party.presentation.screen.profile_edit_locations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.ScreenExplainArea
import com.party.common.component.button.ResetAndApplyButtonArea
import com.party.common.snackBarMessage
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.profile_edit_locations.component.ProfileEditLocationScaffoldArea
import com.party.presentation.screen.profile_edit_locations.component.ProfileEditSelectLocationArea
import com.party.presentation.screen.profile_edit_locations.component.SelectedProvinceAndLocationArea
import com.party.presentation.screen.profile_edit_locations.viewmodel.ProfileEditLocationViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileEditLocationScreenRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    profileEditLocationViewModel: ProfileEditLocationViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        profileEditLocationViewModel.fourth.collectLatest {
            snackBarMessage(snackBarHostState, "최대 3개까지 선택할 수 있어요.")
        }
    }

    LaunchedEffect(key1 = Unit) {
        profileEditLocationViewModel.saveSuccess.collectLatest {
            navController.popBackStack()
        }
    }

    val state by profileEditLocationViewModel.state.collectAsStateWithLifecycle()

    ProfileEditLocationScreen(
        snackBarHostState = snackBarHostState,
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            when (action) {
                is ProfileEditLocationAction.OnSelectProvince -> { profileEditLocationViewModel.onAction(action)}
                is ProfileEditLocationAction.OnSelectLocation -> { profileEditLocationViewModel.onAction(action)}
                is ProfileEditLocationAction.OnDeleteProvinceAndLocation -> { profileEditLocationViewModel.onAction(action)}
                is ProfileEditLocationAction.OnReset -> { profileEditLocationViewModel.onAction(action)}
                is ProfileEditLocationAction.OnApply -> { profileEditLocationViewModel.onAction(action)}
            }
        }
    )
}

@Composable
private fun ProfileEditLocationScreen(
    snackBarHostState: SnackbarHostState,
    state: ProfileEditLocationState,
    onNavigationClick: () -> Unit,
    onAction: (ProfileEditLocationAction) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            ProfileEditLocationScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                ScreenExplainArea(
                    mainExplain = "관심 지역을 선택하고\n파티를 추천 받아보세요!",
                    subExplain = "거주 지역이나 자주 찾는 지역을 선택해 주세요.\n(최대 3곳)"
                )

                // Location Select Area
                HeightSpacer(heightDp = 20.dp)
                ProfileEditSelectLocationArea(
                    modifier = Modifier.fillMaxHeight(),
                    selectedProvince = state.selectedProvince,
                    selectedLocationList = state.selectedLocationList,
                    locationList = state.getLocationList,
                    onSelectProvince = { selectedProvince -> onAction(ProfileEditLocationAction.OnSelectProvince(selectedProvince)) },
                    onSelectLocation = { selectedLocation -> onAction(ProfileEditLocationAction.OnSelectLocation(selectedLocation)) }
                )
            }

            // Reset and Apply Button Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // selected province and location list
                HeightSpacer(heightDp = 16.dp)
                SelectedProvinceAndLocationArea(
                    selectedProvinceAndLocationList = state.selectedProvinceAndLocationList,
                    onDelete = { selectedItem -> onAction(ProfileEditLocationAction.OnDeleteProvinceAndLocation(selectedItem)) }
                )
                HeightSpacer(heightDp = 20.dp)

                ResetAndApplyButtonArea(
                    onReset = { onAction(ProfileEditLocationAction.OnReset) },
                    onApply = { onAction(ProfileEditLocationAction.OnApply) }
                )
                HeightSpacer(heightDp = 12.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditLocationScreenPreview() {
    ProfileEditLocationScreen(
        snackBarHostState = SnackbarHostState(),
        state = ProfileEditLocationState(),
        onNavigationClick = {},
        onAction = {}
    )
}