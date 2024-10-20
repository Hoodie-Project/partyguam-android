package com.party.presentation.screen.home.tab_main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.presentation.screen.home.HomeListTitleArea
import com.party.presentation.screen.home.HomeViewModel

@Composable
fun PersonalRecruitmentArea(
    homeViewModel: HomeViewModel,
) {
    LaunchedEffect(Unit) {
        homeViewModel.getPersonalRecruitmentList(page = 1, size = 1, sort = "createdAt", order = "DESC")
    }

    HeightSpacer(heightDp = 40.dp)

    HomeListTitleArea(
        title = stringResource(id = R.string.home_list_personal_title),
        description = stringResource(id = R.string.home_list_personal_description),
    )
}