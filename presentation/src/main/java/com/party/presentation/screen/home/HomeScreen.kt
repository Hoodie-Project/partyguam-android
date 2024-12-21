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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.component.FloatingButtonArea
import com.party.presentation.screen.home.component.HomeTopBar
import com.party.presentation.screen.home.component.HomeTopTabArea
import com.party.presentation.screen.home.tab_main.MainArea
import com.party.presentation.screen.home.tab_party.PartyArea
import com.party.presentation.screen.home.tab_recruitment.RecruitmentArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.shared.SharedViewModel

@Composable
fun HomeScreenRoute(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    homeTopTabList: List<String>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onRecruitmentItemClick: (Int, Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val homeState by homeViewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        context = context,
        snackBarHostState = snackBarHostState,
        navController = navController,
        homeState = homeState,
        homeTopTabList = homeTopTabList,
        homeViewModel = homeViewModel,
        onRecruitmentItemClick = onRecruitmentItemClick,
        sharedViewModel = sharedViewModel,
        onGotoSearch = { navController.navigate(Screens.Search) },
        onGotoRecruitmentDetail = { partyId, partyRecruitmentId -> navController.navigate(Screens.RecruitmentDetail(partyId = partyId, partyRecruitmentId = partyRecruitmentId)) },
        onGotoPartyDetail = { partyId -> navController.navigate(Screens.PartyDetail(partyId = partyId)) },
        onAction = { action ->
            when(action){
                is HomeAction.OnTabClick -> { homeViewModel.onAction(action) }
                is HomeAction.OnPersonalRecruitmentReload -> { homeViewModel.onAction(action) }
            }
        }
    )
}

@Composable
private fun HomeScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    homeState: HomeState,
    homeTopTabList: List<String>,
    homeViewModel: HomeViewModel,
    onGotoSearch: () -> Unit,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    sharedViewModel: SharedViewModel,
    onGotoRecruitmentDetail: (Int, Int) -> Unit,
    onGotoPartyDetail: (Int) -> Unit,
    onAction: (HomeAction) -> Unit,
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
                HomeTopBar(
                    onGoToSearch = onGotoSearch,
                    onGoToAlarm = {}
                )

                HomeTopTabArea(
                    homeTopTabList = homeTopTabList,
                    selectedTabText = homeState.selectedTabText,
                    onTabClick = { selectedTabText -> onAction(HomeAction.OnTabClick(tabText = selectedTabText)) }
                )
                when (homeState.selectedTabText) {
                    homeTopTabList[0] -> {
                        MainArea(
                            homeState = homeState,
                            onReload = { onAction(HomeAction.OnPersonalRecruitmentReload) },
                            onGoRecruitment = { onAction(HomeAction.OnTabClick(tabText = homeTopTabList[2])) },
                            onGoParty = { onAction(HomeAction.OnTabClick(tabText = homeTopTabList[1])) },
                            onGotoRecruitmentDetail = onGotoRecruitmentDetail,
                            onGotoPartyDetail = onGotoPartyDetail
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
            currentScreens = Screens.Home,
            onExpanded = {
                isExpandedFloatingButton = it},
            sharedViewModel = sharedViewModel,
            navHostController = navController,
            selectedTabText = homeState.selectedTabText
        )
    }
}