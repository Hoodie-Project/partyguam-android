package com.party.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.party.presentation.screen.home.tab_main.MainArea
import com.party.presentation.screen.home.tab_party.PartyArea
import com.party.presentation.screen.home.tab_recruitment.RecruitmentArea

@Composable
fun HomeScreen(
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    selectedTabText: String,
    homeTopTabList: List<String>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onTabClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeTopBar(
            navController = navController,
        )

        HomeTopTabArea(
            homeTopTabList = homeTopTabList,
            selectedTabText = selectedTabText,
            onTabClick = { onTabClick(it) }
        )
        when(selectedTabText){
            homeTopTabList[0] -> { MainArea(homeViewModel = homeViewModel, snackBarHostState = snackBarHostState) }
            homeTopTabList[1] -> { PartyArea() }
            homeTopTabList[2] -> { RecruitmentArea(homeViewModel = homeViewModel, snackBarHostState = snackBarHostState) }
        }
    }
}