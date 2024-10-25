package com.party.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.party.common.R
import com.party.presentation.screen.home.tab_main.MainArea
import com.party.presentation.screen.home.tab_party.PartyArea
import com.party.presentation.screen.home.tab_recruitment.RecruitmentArea

@Composable
fun HomeScreen(
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val homeTopTabList = listOf(
        stringResource(id = R.string.home_top_tab1),
        stringResource(id = R.string.home_top_tab2),
        stringResource(id = R.string.home_top_tab3)
    )

    var selectedTabText by remember {
        mutableStateOf(homeTopTabList[0])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        HomeTopBar(
            navController = navController,
        )

        HomeTopTabArea(
            homeTopTabList = homeTopTabList,
            selectedTabText = selectedTabText,
            onClick = { selectedTabText = it }
        )

        when(selectedTabText){
            homeTopTabList[0] -> { MainArea(homeViewModel = homeViewModel, snackBarHostState = snackBarHostState) }
            homeTopTabList[1] -> { PartyArea() }
            homeTopTabList[2] -> { RecruitmentArea(homeViewModel = homeViewModel) }
        }
    }
}