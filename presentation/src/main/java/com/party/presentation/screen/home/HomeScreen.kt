package com.party.presentation.screen.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.party.common.Screens
import com.party.common.component.BottomNavigationBar
import com.party.common.component.dialog.OneButtonDialog
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.floating.NavigateUpFloatingButton
import com.party.common.component.floating.PartyCreateFloatingButton
import com.party.common.utils.noRippleClickable
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.guam.firebase.FirebaseAnalyticsHelper
import com.party.presentation.BuildConfig
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.SortType
import com.party.presentation.screen.home.component.HomeFloatingArea
import com.party.presentation.screen.home.component.HomeTopBar
import com.party.presentation.screen.home.component.HomeTopTabArea
import com.party.presentation.screen.home.tab_main.MainArea
import com.party.presentation.screen.home.tab_party.PartyArea
import com.party.presentation.screen.home.tab_recruitment.RecruitmentArea
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenRoute(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    homeTopTabList: List<String>,
    isFirstActiveFunc: Boolean,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onChangeFirstFunc: () -> Unit,
    onRecruitmentItemClick: (Int, Int) -> Unit,
) {
    // 릴리즈 빌드일 경우만 적용
    if (!BuildConfig.DEBUG) {
        FirebaseAnalyticsHelper.logEvent(
            name = "home_screen"
        )
    }

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
        homeViewModel.getPartyList(page = 1, size = 5, sort = SortType.CREATED_AT.type, order = OrderDescType.DESC.type, titleSearch = null, status = if(homeState.isActivePartyToggle) "active" else "archived")
        homeViewModel.getRecruitmentList(page = 1, size = 8, sort = SortType.CREATED_AT.type, order = OrderDescType.DESC.type, titleSearch = null)
    }

    LaunchedEffect(key1 = homeState.selectedMainPosition) {
        homeViewModel.getSubPositionList(homeState.selectedMainPosition)
    }

    LaunchedEffect(key1 = Unit) {
        if(isFirstActiveFunc){
            val localAppVersion = getAppVersion(context = context)
            delay(500L)
            homeViewModel.checkAppVersion(localAppVersion = localAppVersion ?: "1.0.0")
            onChangeFirstFunc()
        }
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
            homeViewModel.onAction(action)
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
                    radiusX = if (homeState.isExpandedFloating || homeState.isShowForceUpdateDialog || homeState.isShowChoiceUpdateDialog) 10.dp else 0.dp,
                    radiusY = if (homeState.isExpandedFloating || homeState.isShowForceUpdateDialog || homeState.isShowChoiceUpdateDialog) 10.dp else 0.dp,
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

        // 강제업데이트
        if(homeState.isShowForceUpdateDialog){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.2f))
                    .noRippleClickable {}
            ) {
                OneButtonDialog(
                    dialogTitle = "최신 버전의 앱이 있어요",
                    description = "최적의 사용 환경을 위해 최신 버전의\n앱으로 업데이트 해주세요",
                    buttonText = "업데이트",
                    onCancel = {},
                    onConfirm = {
                        onAction(HomeAction.OnShowForceUpdateDialog(false))
                        goToPlayStore(context)
                    },
                )
            }
        }

        // 선택 업데이트
        if(homeState.isShowChoiceUpdateDialog){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.2f))
                    .noRippleClickable {
                    }
            ) {
                TwoButtonDialog(
                    dialogTitle = "최신 버전의 앱이 있어요",
                    description = "최적의 사용 환경을 위해 최신 버전의\n앱으로 업데이트 해주세요",
                    cancelButtonText = "다음에",
                    confirmButtonText = "업데이트",
                    onCancel = {
                        onAction(HomeAction.OnShowChoiceUpdateDialog(false))
                    },
                    onConfirm = {
                        onAction(HomeAction.OnShowChoiceUpdateDialog(false))
                        goToPlayStore(context)
                    }
                )
            }
        }
    }
}

fun compareAppVersions(localAppVersion: String?, serverAppVersion: String?): Int {
    if (localAppVersion.isNullOrEmpty() || serverAppVersion.isNullOrEmpty()) {
        return -1 // 업데이트 필요 (기본적으로 최신 버전이 있다고 가정)
    }

    val currentParts = localAppVersion.split(".").map { it.toIntOrNull() ?: 0 }
    val latestParts = serverAppVersion.split(".").map { it.toIntOrNull() ?: 0 }

    val maxLength = maxOf(currentParts.size, latestParts.size)

    for (i in 0 until maxLength) {
        val current = currentParts.getOrElse(i) { 0 }
        val latest = latestParts.getOrElse(i) { 0 }

        if (current < latest) return -1  // 업데이트 필요
        if (current > latest) return 1   // 현재 버전이 최신 (보통 발생하지 않음)
    }
    return 0  // 동일 버전
}

fun getAppVersion(context: Context): String? {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName // ex) "1.0.0"
        //val versionCode = PackageInfoCompat.getLongVersionCode(packageInfo) // 버전 코드 (API 28 이상)
        versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "버전 정보 없음"
    }
}

fun goToPlayStore(
    context: Context,
){
    val appPackageName = "com.party.guam"
    try {
        // Play 스토어 앱으로 이동
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    } catch (e: ActivityNotFoundException) {
        // Play 스토어 앱이 없으면 브라우저로 이동
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}