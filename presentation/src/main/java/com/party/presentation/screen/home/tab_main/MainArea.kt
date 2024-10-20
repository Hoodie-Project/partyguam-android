package com.party.presentation.screen.home.tab_main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.party.presentation.screen.home.HomeViewModel

@Composable
fun MainArea(
    homeViewModel: HomeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BannerArea()

        PersonalRecruitmentArea(homeViewModel = homeViewModel)
    }
}