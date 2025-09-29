package com.party.presentation.screen.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.Screens
import com.party.common.component.snackbar.CustomSnackBar
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ProfileIndicatorSection
import com.party.common.utils.ScreenExplainArea
import com.party.common.utils.StepInfo
import com.party.common.utils.StepStatus
import com.party.common.utils.TextComponent
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LIGHT400
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.component.DetailProfileNextButton
import com.party.presentation.screen.detail.action.DetailProfileAction
import com.party.presentation.screen.detail.component.DetailProfileScaffoldArea
import com.party.presentation.screen.detail.state.DetailProfileState
import com.party.presentation.screen.detail.viewmodel.DetailProfileViewModel
import com.party.presentation.screen.home_detail_profile.component.LocationSection
import com.party.presentation.screen.home_detail_profile.component.SelectedProvinceAndSubLocationSection
import kotlinx.coroutines.flow.collectLatest

const val SELECTED_LOCATION_COUNT = 3

@Composable
fun DetailProfileLocationScreenRoute(
    viewModel: DetailProfileViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = state.selectedProvince) {
        viewModel.getLocationList()
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.locationLimitExceeded.collectLatest {
            snackBarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.successSaveInterestLocation.collectLatest {
            navController.navigate(route = Screens.DetailProfileCareer)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.existSavedData.collectLatest {
            snackBarHostState.showSnackbar("이미 저장된 데이터가 있습니다.")
        }
    }

    DetailProfileLocationScreen(
        snackBarHostState = snackBarHostState,
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            viewModel.onAction(action)
        },
        onGotoNext = {
            viewModel.saveInterestLocation()
        },
        onSkip = { navController.navigate(route = Screens.DetailProfileCareer)}
    )
}

@Composable
private fun DetailProfileLocationScreen(
    snackBarHostState: SnackbarHostState,
    state: DetailProfileState,
    onNavigationClick: () -> Unit = {},
    onAction: (DetailProfileAction) -> Unit,
    onGotoNext: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    CustomSnackBar(
                        message = data.visuals.message
                    )
                }
            )
        },
        topBar = {
            DetailProfileScaffoldArea(
                onNavigationClick = onNavigationClick,
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
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                val steps = listOf(
                    StepInfo("1", "관심지역", StepStatus.CURRENT),
                    StepInfo("2", "경력/포지션", StepStatus.PENDING),
                    StepInfo("3", "성향선택(1/4)", StepStatus.PENDING)
                )

                ProfileIndicatorSection(steps = steps)

                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.detail_profile7),
                    subExplain = stringResource(id = R.string.detail_profile8),
                )

                HeightSpacer(heightDp = 46.dp)
                LocationSection(
                    selectedProvince = state.selectedProvince,
                    onClickProvince = { provinceName -> onAction(DetailProfileAction.OnClickProvince(provinceName = provinceName))},
                    subLocationList = state.subLocationList,
                    selectedProvinceAndSubLocationList = state.selectedProvinceAndSubLocationList,
                    onClickSubLocation = { selectedLocation -> onAction(DetailProfileAction.OnClickSubLocation(location = selectedLocation))}
                )
            }

            HeightSpacer(heightDp = 16.dp)
            SelectedProvinceAndSubLocationSection(
                selectedProvinceAndSubLocationList = state.selectedProvinceAndSubLocationList,
                onDelete = { selectedProvinceAndSubLocation -> onAction(DetailProfileAction.OnDeleteSelectedLocation(locationPair = selectedProvinceAndSubLocation))}
            )
            HeightSpacer(heightDp = 16.dp)

            DetailProfileNextButton(
                text = stringResource(id = R.string.common1),
                textColor = if((1..SELECTED_LOCATION_COUNT).contains(state.selectedProvinceAndSubLocationList.size)) BLACK else GRAY400,
                containerColor = if((1..SELECTED_LOCATION_COUNT).contains(state.selectedProvinceAndSubLocationList.size)) PRIMARY else LIGHT400,
                onClick = onGotoNext
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                ,
                text = stringResource(id = R.string.common3),
                fontSize = B2,
                textColor = GRAY500,
                align = Alignment.Center,
                textDecoration = TextDecoration.Underline,
                onClick = onSkip
            )
        }
    }
}