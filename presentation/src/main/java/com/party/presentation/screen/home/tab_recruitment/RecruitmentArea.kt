package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.presentation.screen.home.HomeViewModel
import com.party.presentation.screen.home.tab_main.RecruitmentListArea

@Composable
fun RecruitmentArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeightSpacer(heightDp = 20.dp)
        SelectFilterArea(
            stringResource(id = R.string.recruitment_filter1),
            stringResource(id = R.string.recruitment_filter2),
            stringResource(id = R.string.recruitment_filter3),
        )
        HeightSpacer(heightDp = 16.dp)
        RecruitmentColumnListArea(
            homeViewModel = homeViewModel,
            snackBarHostState = snackBarHostState
        )
    }
}