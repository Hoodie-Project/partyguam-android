package com.party.presentation.screen.detail.detail_profile

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    context: Context,
    detailProfileViewModel: DetailProfileViewModel = hiltViewModel(),
) {
    var selectedProvince by remember {
        mutableStateOf("")
    }

    val selectedLocationList by remember {
        mutableStateOf(mutableStateListOf<String>())
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
                onSelectLocation = { selectedProvince = it },
                onSelectCountry = {
                    selectedLocationList.add(it)
                },
                onDeleteCountry = { selectedLocationList.remove(it) },
                snackBarHostState = snackBarHostState,
                context = context,
                detailProfileViewModel = detailProfileViewModel,
            )
        }

        SelectedLocationArea(
            navController = navController,
            selectedProvince = selectedProvince,
            selectedLocationList = selectedLocationList,
            onDelete = { selectedLocationList.remove(it) },
        )
    }
}