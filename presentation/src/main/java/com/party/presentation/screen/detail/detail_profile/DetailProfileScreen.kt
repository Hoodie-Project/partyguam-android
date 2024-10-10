package com.party.presentation.screen.detail.detail_profile

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.presentation.screen.detail.ProfileIndicatorArea

const val SELECTED_LOCATION_COUNT = 3

@Composable
fun DetailProfileScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    detailProfileViewModel: DetailProfileViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        detailProfileViewModel.getAccessToken().join()
    }

    val accessToken by detailProfileViewModel.accessToken.collectAsState()

    var selectedProvince by remember {
        mutableStateOf("서울")
    }

    LaunchedEffect (key1 = selectedProvince){
        detailProfileViewModel.getLocationList(accessToken = "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjZTVmUyL1VFRWpaQ2pYcW5wY3AyS3c9PSIsImlhdCI6MTcyODU1NzE3MCwiZXhwIjoxNzYwMTE0NzcwfQ.H9Q5nYOwCUrIijOR1g0t3YmdUQm403MsmxdeyOUXDlQLBreXNbj7z5od6csVjo8Xr4jidHeILQTOVINDsJoFiQ", province = selectedProvince)
    }

    val selectedLocationList by remember {
        mutableStateOf(mutableStateListOf<Pair<String, Int>>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                onSelectProvince = {
                    selectedProvince = it
                },
                onSelectLocation = { selectedLocationList.add(it) },
                onDeleteLocation = { selectedLocationList.remove(it) },
                snackBarHostState = snackBarHostState,
                context = context,
                detailProfileViewModel = detailProfileViewModel,
            )
        }

        SelectedLocationArea(
            snackBarHostState = snackBarHostState,
            navController = navController,
            selectedProvince = selectedProvince,
            selectedLocationList = selectedLocationList,
            onDelete = { selectedLocationList.remove(it) },
            detailProfileViewModel = detailProfileViewModel,
            accessToken = "Bearer $accessToken",
        )
    }
}