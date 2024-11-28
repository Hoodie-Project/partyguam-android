package com.party.presentation.screen.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.component.FloatingButtonArea
import com.party.presentation.screen.home.tab_main.MainArea
import com.party.presentation.screen.home.tab_party.PartyArea
import com.party.presentation.screen.home.tab_recruitment.RecruitmentArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.shared.SharedViewModel

@Composable
fun HomeScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    selectedTabText: String,
    homeTopTabList: List<String>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onTabClick: (String) -> Unit,
    onGoRecruitment: () -> Unit,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    HomeScreenContent(
        context = context,
        snackBarHostState = snackBarHostState,
        navController = navController,
        selectedTabText = selectedTabText,
        homeTopTabList = homeTopTabList,
        homeViewModel = homeViewModel,
        onTabClick = onTabClick,
        onGoRecruitment = onGoRecruitment,
        onRecruitmentItemClick = onRecruitmentItemClick,
        sharedViewModel = sharedViewModel
    )
}

@Composable
fun HomeScreenContent(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    selectedTabText: String,
    homeTopTabList: List<String>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onTabClick: (String) -> Unit,
    onGoRecruitment: () -> Unit,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    sharedViewModel: SharedViewModel,
){
    var isExpandedFloatingButton by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.then(if (isExpandedFloatingButton) { Modifier.blur(10.dp) } else { Modifier }),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarHostState,
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    context = context,
                    navController = navController,
                    isExpandedFloatingButton = isExpandedFloatingButton,
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WHITE)
                    .padding(it)
                    .padding(horizontal = MEDIUM_PADDING_SIZE)
            ) {
                HomeTopBar()

                HomeTopTabArea(
                    homeTopTabList = homeTopTabList,
                    selectedTabText = selectedTabText,
                    onTabClick = { onTabClick(it) }
                )
                when (selectedTabText) {
                    homeTopTabList[0] -> {
                        MainArea(
                            homeViewModel = homeViewModel,
                            snackBarHostState = snackBarHostState,
                            onGoRecruitment = onGoRecruitment
                        )
                    }

                    homeTopTabList[1] -> {
                        PartyArea(
                            homeViewModel = homeViewModel,
                            snackBarHostState = snackBarHostState,
                            sharedViewModel = sharedViewModel,
                            onClick = { navController.navigate(Screens.PartyDetail(partyId = it)) }
                        )
                    }

                    homeTopTabList[2] -> {
                        RecruitmentArea(
                            homeViewModel = homeViewModel,
                            snackBarHostState = snackBarHostState,
                            onRecruitmentItemClick = onRecruitmentItemClick,
                            sharedViewModel = sharedViewModel
                        )
                    }
                }
            }
        }

        if (isExpandedFloatingButton) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.7f))
                    .noRippleClickable { isExpandedFloatingButton = false }
                    .zIndex(0f)
            )
        }

        FloatingButtonArea(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 98.dp, end = 20.dp)
                .zIndex(1f),
            isExpandedFloatingButton = isExpandedFloatingButton,
            selectedTabText = selectedTabText,
            currentScreens = Screens.Home,
            onExpanded = {
                isExpandedFloatingButton = it},
            sharedViewModel = sharedViewModel,
            navHostController = navController
        )
    }
}