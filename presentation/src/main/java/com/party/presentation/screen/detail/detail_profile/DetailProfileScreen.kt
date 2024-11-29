package com.party.presentation.screen.detail.detail_profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK200
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.model.user.detail.Location
import com.party.navigation.Screens
import com.party.presentation.screen.detail.ProfileIndicatorArea
import com.party.presentation.screen.detail.detail_profile.component.DetailProfileScaffoldArea
import com.party.presentation.screen.detail.detail_profile.component.ProvinceComponent
import com.party.presentation.screen.detail.detail_profile.component.SelectLocationArea
import com.party.presentation.screen.detail.detail_profile.component.SelectLocationComponent
import com.party.presentation.screen.detail.detail_profile.component.SelectProvinceArea
import com.party.presentation.screen.detail.detail_profile.component.SelectedLocationArea
import com.party.presentation.screen.detail.detail_profile.viewmodel.DetailProfileViewModel
import kotlinx.coroutines.flow.collectLatest

const val SELECTED_LOCATION_COUNT = 3

@Composable
fun DetailProfileScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    detailProfileViewModel: DetailProfileViewModel = hiltViewModel(),
) {
    // 선택된 도시
    var selectedProvince by remember {
        mutableStateOf("서울")
    }

    // 선택된 도시의 하위 리스트
    val selectedLocationList by remember {
        mutableStateOf(mutableStateListOf<Pair<String, Int>>())
    }

    LaunchedEffect(key1 = Unit) {
        detailProfileViewModel.saveSuccess.collectLatest {
            navController.navigate(Screens.DetailCarrier)
        }
    }

    LaunchedEffect(key1 = Unit) {
        detailProfileViewModel.saveFail.collectLatest {
            snackBarMessage(snackBarHostState, it)
        }
    }

    LaunchedEffect(key1 = selectedProvince) {
        detailProfileViewModel.getLocationList(province = selectedProvince)
    }

    var list by remember {
        mutableStateOf(emptyList<Location>())
    }

    val locationListState by detailProfileViewModel.getLocationListState.collectAsState()

    when(locationListState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar()}
        is UIState.Success -> {
            val locationListResult = locationListState.data as SuccessResponse<List<Location>>
            list = locationListResult.data ?: emptyList()
        }
        is UIState.Error -> {
            val errorResult = locationListState.data as ErrorResponse
            snackBarMessage(snackBarHostState, errorResult.message ?: "")
        }
        is UIState.Exception -> { }
    }

    //val locationListResult = locationListState.data as? SuccessResponse<List<Location>>

    DetailProfileScreenContent(
        context = context,
        snackBarHostState = snackBarHostState,
        selectedProvince = selectedProvince,
        selectedLocationList = selectedLocationList,
        locationListResult = list,
        onSelectProvince = { selectedProvince = it },
        onSkip = { navController.navigate(Screens.DetailCarrier) },
        onNextButtonClick = {
            if((1..SELECTED_LOCATION_COUNT).contains(selectedLocationList.size)) {
                detailProfileViewModel.saveInterestLocation(
                    locations = InterestLocationList(
                        locations = selectedLocationList.map {
                            InterestLocationRequest(id = it.second)
                        }
                    )
                )
            }
        }
    )
}

@Composable
fun DetailProfileScreenContent(
    context: Context,
    snackBarHostState: SnackbarHostState,
    selectedProvince: String,
    selectedLocationList: MutableList<Pair<String, Int>>,
    locationListResult: List<Location>,
    onSelectProvince: (String) -> Unit,
    onSkip: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DetailProfileScaffoldArea(
                onNavigationClick = {}
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
                ProfileIndicatorArea(
                    container1 = PRIMARY,
                    container2 = GRAY100,
                    container3 = GRAY100,
                    textColor1 = BLACK,
                    textColor2 = GRAY400,
                    textColor3 = GRAY400,
                    indicatorText = stringResource(id = R.string.detail_profile3),
                )

                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.detail_profile7),
                    subExplain = stringResource(id = R.string.detail_profile8),
                )

                SelectLocationArea(
                    selectedProvince = selectedProvince,
                    selectedLocationList = selectedLocationList,
                    onSelectProvince = onSelectProvince,
                    onSelectLocation = { selectedLocationList.add(it) },
                    onDeleteLocation = { selectedLocationList.remove(it) },
                    snackBarHostState = snackBarHostState,
                    context = context,
                    locationListResult = locationListResult,
                )
            }

            SelectedLocationArea(
                selectedProvince = selectedProvince,
                selectedLocationList = selectedLocationList,
                onDelete = { selectedLocationList.remove(it) },
                onSkip = onSkip,
                onNextButtonClick = onNextButtonClick
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DetailProfileScreenContentPreview() {
    DetailProfileScreenContent(
        context = LocalContext.current,
        snackBarHostState = SnackbarHostState(),
        selectedProvince = "서울",
        selectedLocationList = mutableStateListOf(),
        onSelectProvince = {  },
        onSkip = {  },
        onNextButtonClick = {  },
        locationListResult = emptyList()
    )
}

@Preview(showBackground = true)
@Composable
fun SelectProvinceAreaPreview() {
    SelectProvinceArea(
        modifier = Modifier,
        selectedProvince = "서울",
        onSelectProvince = {  }
    )
}

@Preview(showBackground = true)
@Composable
fun SelectedCityComponentPreview(){
    ProvinceComponent(
        containerColor = LIGHT400,
        text = "서울",
        textColor = DARK200,
        onSelectCity = {}
    )
}

@Preview(showBackground = true)
@Composable
fun UnSelectedCityComponentPreview(){
    ProvinceComponent(
        containerColor = WHITE,
        text = "서울",
        textColor = GRAY400,
        onSelectCity = {}
    )
}


// SelectedLocationArea
@Preview(showBackground = true)
@Composable
fun SelectCityComponentPreview() {
    SelectLocationComponent(
        item = Pair("강남구", 1),
        selectedProvinceName = "강남구",
        onDelete = {},
    )
}