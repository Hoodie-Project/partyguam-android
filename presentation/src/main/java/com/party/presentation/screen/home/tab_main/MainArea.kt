package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.presentation.screen.home.HomeViewModel

@Composable
fun MainArea(
    homeViewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    onGoRecruitment: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        BannerArea()

        PersonalRecruitmentArea(
            homeViewModel = homeViewModel,
            snackBarHostState = snackBarHostState
        )

        HeightSpacer(heightDp = 60.dp)

        NewRecruitmentArea(
            homeViewModel = homeViewModel,
            snackBarHostState = snackBarHostState,
            onGoRecruitment = { onGoRecruitment() }
        )

        HeightSpacer(heightDp = 60.dp)

        PartyListArea(
            homeViewModel = homeViewModel,
            snackBarHostState = snackBarHostState
        )

        HeightSpacer(heightDp = 60.dp)
    }
}