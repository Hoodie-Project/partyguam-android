package com.party.presentation.screen.main

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.party.common.Screens
import com.party.common.component.BottomNavigationBar
import com.party.common.component.homeTopTabList
import com.party.common.component.profileEditTendencyTabList
import com.party.common.component.snackbar.CustomSnackBar
import com.party.common.component.getCurrentScreen
import com.party.guam.design.WHITE
import com.party.presentation.ANIMATION_DURATION
import com.party.presentation.screen.auth_setting.AuthSettingScreenRoute
import com.party.presentation.screen.home.HomeScreenRoute
import com.party.presentation.screen.home_detail_profile.homeDetailProfileGraph
import com.party.presentation.screen.info_center.infoCenterGraph
import com.party.presentation.screen.manage_applicant.ManageApplicantScreenRoute
import com.party.presentation.screen.notification.NotificationScreenRoute
import com.party.presentation.screen.party_apply.PartyApplyRoute
import com.party.presentation.screen.party_create.PartyCreateScreenRoute
import com.party.presentation.screen.party_detail.PartyDetailRoute
import com.party.presentation.screen.party_edit.PartyEditScreenRoute
import com.party.presentation.screen.party_edit_recruitment.PartyEditRecruitmentScreenRoute
import com.party.presentation.screen.party_user_manage.PartyUserManageScreenRoute
import com.party.presentation.screen.profile.ProfileScreenRoute
import com.party.presentation.screen.profile_edit.ProfileEditScreenRoute
import com.party.presentation.screen.profile_edit_career.ProfileEditCareerScreenRoute
import com.party.presentation.screen.profile_edit_locations.ProfileEditLocationScreenRoute
import com.party.presentation.screen.profile_edit_portfolio.ProfileEditPortfolioScreenRoute
import com.party.presentation.screen.profile_edit_tendency.ProfileEditTendencyScreenRoute
import com.party.presentation.screen.profile_edit_time.ProfileEditTimeScreenRoute
import com.party.presentation.screen.recover_auth.RecoverAuthScreenRoute
import com.party.presentation.screen.recruitment_create.RecruitmentCreateScreenRoute
import com.party.presentation.screen.recruitment_create_preview.RecruitmentCreatePreviewScreenRoute
import com.party.presentation.screen.recruitment_detail.RecruitmentDetailRoute
import com.party.presentation.screen.recruitment_edit.RecruitmentEditRoute
import com.party.presentation.screen.recruitment_preview.RecruitmentPreviewScreenRoute
import com.party.presentation.screen.reports.ReportsScreenRoute
import com.party.presentation.screen.search.SearchScreenRoute
import com.party.presentation.screen.state.StateScreenRoute
import com.party.presentation.screen.info_center.ServiceIntroduceScreenRoute
import com.party.presentation.screen.user_delete.UserDeleteScreenRoute
import com.party.presentation.screen.webview.WebViewScreenRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    isFirstActiveFunc: Boolean,
    onChangeFirstFunc: (Boolean) -> Unit,
    onLogout: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val navController = rememberNavController()

    val currentScreen = getCurrentScreen(navController)

    // 백버튼 두 번 누르기 위한 상태
    var backPressedOnce by remember { mutableStateOf(false) }

    // 홈 탭에서 백버튼 처리
    BackHandler(
        enabled = currentScreen == Screens.Home::class.simpleName
    ) {
        if (backPressedOnce) {
            // 두 번째 누름 - 앱 종료
            (context as? Activity)?.finish()
        } else {
            // 첫 번째 누름 - 스낵바 메시지 표시
            backPressedOnce = true
            coroutineScope.launch {
                snackBarHostState.showSnackbar("한 번 더 누르면 앱이 종료됩니다.")
                delay(2000) // 2초 후 상태 리셋
                backPressedOnce = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE)
    ){
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
            ,
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarHostState,
                    snackbar = { data ->
                        CustomSnackBar(
                            message = data.visuals.message
                        )
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                    currentScreen = currentScreen ?: "",
                    onTabClick = { screens ->
                        navController.navigate(route = screens) {
                            popUpTo(navController.graph.findStartDestination().route!!) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            },
        ) { paddingValues ->
            BottomBarGraph(
                context = context,
                snackBarHostState = snackBarHostState,
                isFirstActiveFunc = isFirstActiveFunc,
                onChangeFirstFunc = onChangeFirstFunc,
                navController = navController,
                paddingValues = paddingValues,
                onGotoSearch = { navController.navigate(Screens.Search)},
                onGotoNotification = { navController.navigate(Screens.Notification)},
                onClickBanner = { navController.navigate(route = Screens.WebView(webViewUrl = it))},
                onGotoRecruitmentDetail = { partyId, partyRecruitmentId -> navController.navigate(Screens.RecruitmentDetail(partyId = partyId, partyRecruitmentId = partyRecruitmentId)) },
                onGotoPartyDetail = { navController.navigate(Screens.PartyDetail(it))},
                onGoPartyCreate = { navController.navigate(route = Screens.PartyCreate)},
                onGotoDetailProfile = { navController.navigate(route = Screens.HomeDetailProfile)},
                onGoSetting = { navController.navigate(route = Screens.ManageAuth) },
                onGotoProfileEdit = { navController.navigate(route = Screens.ProfileEdit)},
                onLogout = onLogout
            )
        }
    }
}

@Composable
private fun BottomBarGraph(
    context: Context,
    snackBarHostState: SnackbarHostState,
    isFirstActiveFunc: Boolean,
    onChangeFirstFunc: (Boolean) -> Unit,
    navController: NavHostController,
    paddingValues: PaddingValues,
    onGotoSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onClickBanner: (String) -> Unit,
    onGotoRecruitmentDetail: (Int, Int) -> Unit,
    onGotoPartyDetail: (Int) -> Unit,
    onGoPartyCreate: () -> Unit,
    onGotoDetailProfile: () -> Unit,
    onGoSetting: () -> Unit,
    onGotoProfileEdit: () -> Unit,
    onLogout: () -> Unit,
) {
    NavHost(
        modifier = Modifier
            .background(WHITE)
            .padding(paddingValues)
        ,
        navController = navController,
        startDestination = Screens.Main,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = ANIMATION_DURATION)
            )
        }
    ){
        navigation<Screens.Main>(
            startDestination = Screens.Home,
        ){
            composable<Screens.Home> {
                HomeScreenRoute(
                    context = context,
                    homeTopTabList = homeTopTabList,
                    isFirstActiveFunc = isFirstActiveFunc,
                    onChangeFirstFunc = { onChangeFirstFunc(false)},
                    onGotoSearch = onGotoSearch,
                    onGotoNotification = onGotoNotification,
                    onClickBanner = onClickBanner,
                    onGotoRecruitmentDetail = onGotoRecruitmentDetail,
                    onGotoPartyDetail = onGotoPartyDetail,
                    onGoPartyCreate = onGoPartyCreate,
                    onGotoDetailProfile = onGotoDetailProfile,
                )
            }
            composable<Screens.State> {
                StateScreenRoute(
                    onGoToSearch = onGotoSearch,
                    onGotoNotification = onGotoNotification,
                    onGoPartyCreate = onGoPartyCreate,
                    onGotoPartyDetail = onGotoPartyDetail,
                )
            }
            composable<Screens.Profile> {
                ProfileScreenRoute(
                    navController = navController,
                    onGotoNotification = onGotoNotification,
                    onGoSetting = onGoSetting,
                    onGotoPartyDetail = onGotoPartyDetail,
                    onGotoProfileEdit = onGotoProfileEdit
                )
            }
            composable<Screens.Search> {
                SearchScreenRoute(
                    navController = navController,
                )
            }
            composable<Screens.Notification> {
                NotificationScreenRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.ManageAuth> {
                AuthSettingScreenRoute(
                    context = context,
                    navController = navController,
                    onLogout = onLogout,
                )
            }
            composable<Screens.ProfileEdit> {
                ProfileEditScreenRoute(
                    navController = navController
                )
            }
            composable<Screens.PartyDetail>(
                deepLinks = listOf(
                    navDeepLink<Screens.PartyDetail>(
                        basePath = "rally://Screen.PartyDetail.id/{name}"
                    )
                )
            ) { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.PartyDetail>().partyId

                PartyDetailRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                    partyId = partyId,
                )
            }


            composable<Screens.RecruitmentDetail> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyId
                val partyRecruitmentId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyRecruitmentId
                RecruitmentDetailRoute(
                    navController = navController,
                    partyId = partyId,
                    partyRecruitmentId = partyRecruitmentId,
                )
            }
            composable<Screens.PartyApply> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.PartyApply>().partyId
                val partyRecruitmentId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyRecruitmentId
                PartyApplyRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                    partyId = partyId,
                    partyRecruitmentId = partyRecruitmentId,
                )
            }

            composable<Screens.PartyCreate> {
                PartyCreateScreenRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.RecruitmentCreate> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.RecruitmentCreate>().partyId
                RecruitmentCreateScreenRoute(
                    navController = navController,
                    partyId = partyId
                )
            }
            composable<Screens.PartyEdit> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.PartyEdit>().partyId
                PartyEditScreenRoute(
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    partyId = partyId
                )
            }
            composable<Screens.PartyUserManage> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.PartyUserManage>().partyId
                PartyUserManageScreenRoute(
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    partyId = partyId
                )
            }
            composable<Screens.PartyEditRecruitment> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.PartyEditRecruitment>().partyId
                PartyEditRecruitmentScreenRoute(
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    partyId = partyId
                )
            }
            composable<Screens.ManageApplicant> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.ManageApplicant>().partyId
                ManageApplicantScreenRoute(
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    partyId = partyId
                )
            }
            composable<Screens.RecruitmentEdit> { backStackEntry ->
                val partyRecruitmentId = backStackEntry.toRoute<Screens.RecruitmentEdit>().partyRecruitmentId
                val partyId = backStackEntry.toRoute<Screens.RecruitmentEdit>().partyId
                RecruitmentEditRoute(
                    navController = navController,
                    partyRecruitmentId = partyRecruitmentId,
                    partyId = partyId,
                )
            }
            composable<Screens.UserDelete> {
                UserDeleteScreenRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.ProfileEditCareer> {
                ProfileEditCareerScreenRoute(
                    navController = navController
                )
            }
            composable<Screens.ProfileEditPortfolio> {
                ProfileEditPortfolioScreenRoute(
                    navController = navController
                )
            }
            composable<Screens.ProfileEditTime> {
                ProfileEditTimeScreenRoute(
                    snackBarHostState = snackBarHostState,
                    navController = navController
                )
            }
            composable<Screens.ProfileEditLocation> {
                ProfileEditLocationScreenRoute(
                    snackBarHostState = snackBarHostState,
                    navController = navController
                )
            }
            composable<Screens.ProfileEditTendency> {
                ProfileEditTendencyScreenRoute(
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    profileEditTendencyTabList = profileEditTendencyTabList
                )
            }
            composable<Screens.ServiceIntroduce> {
                ServiceIntroduceScreenRoute(
                    navController = navController
                )
            }

            infoCenterGraph(
                context = context,
                navController = navController,
            )

            composable<Screens.Reports> { backStackEntry ->
                val typeId = backStackEntry.toRoute<Screens.Reports>().typeId
                ReportsScreenRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                    typeId = typeId
                )
            }
            composable<Screens.WebView> { backStackEntry ->
                val webViewUrl = backStackEntry.toRoute<Screens.WebView>().webViewUrl
                WebViewScreenRoute(
                    webViewUrl = webViewUrl,
                )
            }
            composable<Screens.RecruitmentPreview> { backStackEntry ->
                val recruitmentId = backStackEntry.toRoute<Screens.RecruitmentPreview>().recruitmentId
                val description = backStackEntry.toRoute<Screens.RecruitmentPreview>().description
                val recruitingCount = backStackEntry.toRoute<Screens.RecruitmentPreview>().recruitingCount
                val main = backStackEntry.toRoute<Screens.RecruitmentPreview>().main
                val sub = backStackEntry.toRoute<Screens.RecruitmentPreview>().sub
                RecruitmentPreviewScreenRoute(
                    navController = navController,
                    partyRecruitmentId = recruitmentId,
                    description = description,
                    recruitingCount = recruitingCount,
                    main = main,
                    sub = sub,
                )
            }
            composable<Screens.RecruitmentCreatePreview> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.RecruitmentCreatePreview>().partyId
                val description = backStackEntry.toRoute<Screens.RecruitmentCreatePreview>().description
                val recruitingCount = backStackEntry.toRoute<Screens.RecruitmentCreatePreview>().recruitingCount
                val main = backStackEntry.toRoute<Screens.RecruitmentCreatePreview>().main
                val sub = backStackEntry.toRoute<Screens.RecruitmentCreatePreview>().sub
                RecruitmentCreatePreviewScreenRoute(
                    context = context,
                    navController = navController,
                    partyId = partyId,
                    description = description,
                    recruitingCount = recruitingCount,
                    main = main,
                    sub = sub,
                )
            }
            composable<Screens.RecoverAuth> { backStackEntry ->
                val email = backStackEntry.toRoute<Screens.RecoverAuth>().email
                val deletedAt = backStackEntry.toRoute<Screens.RecoverAuth>().deletedAt
                val recoverAccessToken = backStackEntry.toRoute<Screens.RecoverAuth>().recoverAccessToken
                RecoverAuthScreenRoute(
                    navController = navController,
                    email = email,
                    deletedAt = deletedAt,
                    recoverAccessToken = recoverAccessToken,
                )
            }

            homeDetailProfileGraph(
                navController = navController,
                snackBarHostState = snackBarHostState,
            )
        }
    }
}