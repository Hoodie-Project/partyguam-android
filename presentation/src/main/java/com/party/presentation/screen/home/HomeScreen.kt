package com.party.presentation.screen.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.component.floating.NavigateUpFloatingButton
import com.party.common.component.floating.PartyCreateFloatingButton
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.common.component.BottomNavigationBar
import com.party.common.Screens
import com.party.presentation.enum.OrderDescType
import com.party.presentation.screen.home.component.HomeFloatingArea
import com.party.presentation.screen.home.component.HomeTopBar
import com.party.presentation.screen.home.component.HomeTopTabArea
import com.party.presentation.screen.home.tab_main.MainArea
import com.party.presentation.screen.home.tab_party.PartyArea
import com.party.presentation.screen.home.tab_recruitment.RecruitmentArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenRoute(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    homeTopTabList: List<String>,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onRecruitmentItemClick: (Int, Int) -> Unit,
) {
    val homeState by homeViewModel.state.collectAsStateWithLifecycle()

    val gridState = rememberLazyGridState()
    val listState = rememberLazyListState()

    val isFabVisibleParty = remember { derivedStateOf { gridState.firstVisibleItemIndex > 0}}
    val isFabVisibleRecruitment = remember { derivedStateOf { listState.firstVisibleItemIndex > 0}}

    LaunchedEffect(key1 = isFabVisibleParty.value) {
        homeViewModel.updateFabVisibleParty(isScrollParty = isFabVisibleParty.value)
    }

    LaunchedEffect(key1 = isFabVisibleRecruitment.value) {
        homeViewModel.updateFabVisibleRecruitment(isScrollRecruitment = isFabVisibleRecruitment.value)
    }

    LaunchedEffect(Unit) {
        homeViewModel.scrollToUpParty.collectLatest {
            gridState.animateScrollToItem(0)
        }
    }
    LaunchedEffect(key1 = Unit) {
        homeViewModel.scrollToUpRecruitment.collectLatest {
            listState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getPartyList(page = 1, size = 5, sort = "createdAt", order = OrderDescType.DESC.type, titleSearch = null, status = if(homeState.isActivePartyToggle) "active" else "archived")
        homeViewModel.getRecruitmentList(page = 1, size = 8, sort = "createdAt", order = OrderDescType.DESC.type, titleSearch = null)
    }

    HomeScreen(
        context = context,
        snackBarHostState = snackBarHostState,
        navController = navController,
        listState = listState,
        gridState = gridState,
        homeState = homeState,
        homeTopTabList = homeTopTabList,
        onRecruitmentItemClick = onRecruitmentItemClick,
        onGotoSearch = { navController.navigate(Screens.Search) },
        onGotoNotification = { navController.navigate(Screens.Notification)},
        onGotoRecruitmentDetail = { partyId, partyRecruitmentId -> navController.navigate(Screens.RecruitmentDetail(partyId = partyId, partyRecruitmentId = partyRecruitmentId)) },
        onGotoPartyDetail = { partyId -> navController.navigate(Screens.PartyDetail(partyId = partyId)) },
        onNavigateUp = { homeViewModel.scrollToTop() },
        onGoPartyCreate = { navController.navigate(Screens.PartyCreate) },
        onGotoDetailProfile = { navController.navigate(Screens.DetailCarrier)},
        onClickBanner = { navController.navigate(Screens.WebView(webViewUrl = it))},
        onAction = { action ->
            when(action){
                is HomeAction.OnTabClick -> { homeViewModel.onAction(action) }
                is HomeAction.OnPersonalRecruitmentReload -> { homeViewModel.onAction(action) }
                is HomeAction.OnPartyTypeSheetOpen -> { homeViewModel.onAction(action) }
                is HomeAction.OnSelectedPartyType -> { homeViewModel.onAction(action) }
                is HomeAction.OnSelectedPartyTypeReset -> { homeViewModel.onAction(action) }
                is HomeAction.OnPartyTypeApply -> { homeViewModel.onAction(action) }
                is HomeAction.OnActivePartyToggle -> { homeViewModel.onAction(action) }
                is HomeAction.OnDescPartyArea -> { homeViewModel.onAction(action) }
                is HomeAction.OnPositionSheetOpen -> { homeViewModel.onAction(action) }
                is HomeAction.OnPartyTypeSheetOpenRecruitment -> { homeViewModel.onAction(action) }
                is HomeAction.OnDescRecruitment -> { homeViewModel.onAction(action) }
                is HomeAction.OnMainPositionClick -> { homeViewModel.onAction(action) }
                is HomeAction.OnSubPositionClick -> { homeViewModel.onAction(action) }
                is HomeAction.OnDelete -> { homeViewModel.onAction(action) }
                is HomeAction.OnPositionApply -> { homeViewModel.onAction(action) }
                is HomeAction.OnPositionSheetReset -> { homeViewModel.onAction(action) }
                is HomeAction.OnSelectedPartyTypeResetRecruitmentReset -> { homeViewModel.onAction(action) }
                is HomeAction.OnSelectedPartyTypeRecruitment -> { homeViewModel.onAction(action) }
                is HomeAction.OnPartyTypeApplyRecruitment -> { homeViewModel.onAction(action) }
                is HomeAction.OnExpandedFloating -> { homeViewModel.onAction(action) }
            }
        }
    )
}

@Composable
private fun HomeScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    listState: LazyListState,
    gridState: LazyGridState,
    homeState: HomeState,
    homeTopTabList: List<String>,
    onGotoSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onRecruitmentItemClick: (Int, Int) -> Unit,
    onGotoRecruitmentDetail: (Int, Int) -> Unit,
    onGotoPartyDetail: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    onGoPartyCreate: () -> Unit,
    onGotoDetailProfile: () -> Unit,
    onClickBanner: (String) -> Unit,
    onAction: (HomeAction) -> Unit,
){
    val bottomBarHeight = 56.dp // BottomNavigationBar 기본 높이

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .blur(
                    radiusX = if (homeState.isExpandedFloating) 10.dp else 0.dp,
                    radiusY = if (homeState.isExpandedFloating) 10.dp else 0.dp,
                ),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarHostState,
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    context = context,
                    navController = navController,
                    isExpandedFloatingButton = homeState.isExpandedFloating,
                    onResetHome = {
                        onAction(HomeAction.OnTabClick(tabText = homeTopTabList[0]))
                    }
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
                    onGoToAlarm = onGotoNotification,
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
                            onGotoPartyDetail = onGotoPartyDetail,
                            onGotoDetailProfile = onGotoDetailProfile,
                            onClickBanner = onClickBanner,
                        )
                    }

                    homeTopTabList[1] -> {
                        PartyArea(
                            gridState = gridState,
                            homeState = homeState,
                            onClick = { navController.navigate(Screens.PartyDetail(partyId = it)) },
                            onPartyTypeModal = { isOpen -> onAction(HomeAction.OnPartyTypeSheetOpen(isOpen)) },
                            onSelectPartyType = { selectedPartyType -> onAction(HomeAction.OnSelectedPartyType(selectedPartyType)) },
                            onReset = { onAction(HomeAction.OnSelectedPartyTypeReset) },
                            onApply = { onAction(HomeAction.OnPartyTypeApply) },
                            onActivePartyToggle = { onToggle -> onAction(HomeAction.OnActivePartyToggle(onToggle)) },
                            onChangeOrderByPartyArea = { isDesc -> onAction(HomeAction.OnDescPartyArea(isDesc)) }
                        )
                    }

                    homeTopTabList[2] -> {
                        RecruitmentArea(
                            listState = listState,
                            homeState = homeState,
                            onRecruitmentItemClick = onRecruitmentItemClick,
                            onPositionSheetClick = { isOpen -> onAction(HomeAction.OnPositionSheetOpen(isOpen)) },
                            onPartyTypeFilterClick = { isOpen -> onAction(HomeAction.OnPartyTypeSheetOpenRecruitment(isOpen)) },
                            onChangeOrderBy = { selectedPartyType -> onAction(HomeAction.OnDescRecruitment(selectedPartyType)) },
                            onMainPositionClick = { selectedMainPosition -> onAction(HomeAction.OnMainPositionClick(selectedMainPosition)) },
                            onSubPositionClick = { selectedSubPosition -> onAction(HomeAction.OnSubPositionClick(selectedSubPosition)) },
                            onDelete = { position -> onAction(HomeAction.OnDelete(position)) },
                            onPositionSheetReset = { onAction(HomeAction.OnPositionSheetReset) },
                            onPositionApply = { onAction(HomeAction.OnPositionApply) },
                            onPositionSheetClose = { isOpen -> onAction(HomeAction.OnPositionSheetOpen(isOpen)) },
                            onPartyTypeSheetClick = { selectedPartyType -> onAction(HomeAction.OnSelectedPartyTypeRecruitment(selectedPartyType)) },
                            onPartyTypeSheetApply = { onAction(HomeAction.OnPartyTypeApplyRecruitment) },
                            onPartyTypeSheetReset = { onAction(HomeAction.OnSelectedPartyTypeResetRecruitmentReset) },
                        )
                    }
                }
            }
        }

        HomeFloatingArea(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = bottomBarHeight + 20.dp, end = 20.dp)
                .zIndex(1f),
            isExpandedFloatingButton = homeState.isExpandedFloating,
            partyCreateFloating = {
                when(homeState.selectedTabText){
                    homeTopTabList[0], homeTopTabList[1] -> {
                        PartyCreateFloatingButton(
                            isExpandedFloatingButton = homeState.isExpandedFloating,
                            onClick = {
                                onAction(HomeAction.OnExpandedFloating(!homeState.isExpandedFloating))
                            }
                        )
                    }
                }
            },
            navigateUpFloating = {
                NavigateUpFloatingButton(
                    isShowNavigateUpFloatingButton = if(homeState.selectedTabText == homeTopTabList[1]) homeState.isScrollParty else if(homeState.selectedTabText == homeTopTabList[2]) homeState.isScrollRecruitment else false,
                    isExpandedFloatingButton = homeState.isExpandedFloating,
                    onClick = onNavigateUp
                )
            },
            onGoPartyCreate = {
                onGoPartyCreate()
                onAction(HomeAction.OnExpandedFloating(false)) // PartyCreate 화면으로 이동하면 현재 플로팅은 닫아줌
            }
        )

        // 플로팅이 Expand 됐을 때 검은 배경을 깔아줌
        if(homeState.isExpandedFloating){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.7f))
                    .noRippleClickable { onAction(HomeAction.OnExpandedFloating(false)) }
                    .zIndex(0f)
            )
        }
    }
}