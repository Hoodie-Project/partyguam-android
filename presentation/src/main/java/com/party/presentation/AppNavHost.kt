package com.party.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.party.common.ConnectivityObserver
import com.party.common.NetworkConnectivityObserver
import com.party.common.Screens
import com.party.common.component.profileEditTendencyTabList
import com.party.common.hasInternetConnection
import com.party.guam.design.WHITE
import com.party.presentation.screen.auth_setting.AuthSettingScreenRoute
import com.party.presentation.screen.detail.choice_carrier_position.ChoiceCarrierPositionScreen
import com.party.presentation.screen.detail.detail_carrier.DetailCarrierScreen
import com.party.presentation.screen.detail.detail_profile.DetailProfileScreen
import com.party.presentation.screen.detail.select_tendency.SelectTendencyCompleteScreen
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen1
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen2
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen3
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen4
import com.party.presentation.screen.guide_permission.GuidePermissionScreenRoute
import com.party.presentation.screen.home_detail_profile.homeDetailProfileGraph
import com.party.presentation.screen.join.joinGraph
import com.party.presentation.screen.login.LoginScreenRoute
import com.party.presentation.screen.main.MainScreen
import com.party.presentation.screen.manage_applicant.ManageApplicantScreenRoute
import com.party.presentation.screen.no_internet.NoInternetScreenRoute
import com.party.presentation.screen.notification.NotificationScreenRoute
import com.party.presentation.screen.party_apply.PartyApplyRoute
import com.party.presentation.screen.party_create.PartyCreateScreenRoute
import com.party.presentation.screen.party_detail.PartyDetailRoute
import com.party.presentation.screen.party_edit.PartyEditScreenRoute
import com.party.presentation.screen.party_edit_recruitment.PartyEditRecruitmentScreenRoute
import com.party.presentation.screen.party_user_manage.PartyUserManageScreenRoute
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
import com.party.presentation.screen.splash.SplashScreenRoute
import com.party.presentation.screen.terms.CustomerInquiriesScreenRoute
import com.party.presentation.screen.terms.PrivacyPolicyScreenRoute
import com.party.presentation.screen.terms.ServiceIntroduceScreenRoute
import com.party.presentation.screen.terms.TermsScreenRoute
import com.party.presentation.screen.user_delete.UserDeleteScreenRoute
import com.party.presentation.screen.webview.WebViewScreenRoute
import kotlinx.coroutines.delay

const val ANIMATION_DURATION = 200

@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }

    val statusFlow = NetworkConnectivityObserver(context).getFlow()
    val status: ConnectivityObserver.Status by statusFlow.collectAsStateWithLifecycle(ConnectivityObserver.Status.Init)

    var isFirstActiveFunc by remember { mutableStateOf(true) }

    var showNoInternet by remember { mutableStateOf(false) }
    LaunchedEffect(status) {
        when (status) {
            ConnectivityObserver.Status.Losing -> {
                delay(500)
                showNoInternet = true
            }

            ConnectivityObserver.Status.UnAvailable -> {
                delay(1000) // 잠깐 기다려보고
                if (status == ConnectivityObserver.Status.UnAvailable) {
                    showNoInternet = true
                }
            }

            ConnectivityObserver.Status.Lost -> {
                showNoInternet = true
            }

            ConnectivityObserver.Status.Available -> {
                //onConnectedNetwork(true)
                showNoInternet = false
            }
            ConnectivityObserver.Status.Init -> {
                delay(300) // 약간의 대기 (네트워크 세팅 시간 고려)
                val hasInternet = hasInternetConnection()
                showNoInternet = !hasInternet
            }
        }
    }

    if(showNoInternet){
        NoInternetScreenRoute()
    } else {
        NavHost(
            navController = navController,
            startDestination = Screens.Splash,
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .systemBarsPadding(),
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
            composable<Screens.Splash> {
                SplashScreenRoute(
                    navController = navController,
                )
            }
            composable<Screens.GuidePermission> {
                GuidePermissionScreenRoute(
                    context = context,
                    navController = navController,
                )
            }
            composable<Screens.Login> {
                LoginScreenRoute(
                    navController = navController,
                    context = context,
                    snackBarHostState = snackBarHostState
                )
            }
            joinGraph(
                context = context,
                navController = navController,
                snackBarHostState = snackBarHostState,
            )
            composable<Screens.DetailProfile> { backStackEntry ->
                val userNickName = backStackEntry.toRoute<Screens.DetailProfile>().userNickName
                DetailProfileScreen(
                    context = context,
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    userNickName = userNickName,
                )
            }
            composable<Screens.DetailCarrier> { backStackEntry ->
                val userNickName = backStackEntry.toRoute<Screens.DetailCarrier>().userNickName
                DetailCarrierScreen(
                    context = context,
                    snackBarHostState = snackBarHostState,
                    navController = navController,
                    userNickName = userNickName,
                )
            }
            composable<Screens.ChoiceCarrierPosition> { backStackEntry ->
                val isMain = backStackEntry.toRoute<Screens.ChoiceCarrierPosition>().isMain
                ChoiceCarrierPositionScreen(
                    navController = navController,
                    isMain = isMain,
                )
            }
            composable<Screens.SelectTendency1> {
                SelectTendencyScreen1(
                    navController = navController,
                    snackBarHostState = snackBarHostState
                )
            }
            composable<Screens.SelectTendency2> {
                SelectTendencyScreen2(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.SelectTendency3> {
                SelectTendencyScreen3(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.SelectTendency4> {
                SelectTendencyScreen4(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.SelectTendencyComplete> {
                SelectTendencyCompleteScreen(
                    navController = navController,
                )
            }

            composable<Screens.Main> { backStackEntry ->
                val tabName = backStackEntry.toRoute<Screens.Main>().tabName
                MainScreen(
                    tabName = tabName,
                    isFirstActiveFunc = isFirstActiveFunc,
                    onChangeFirstFunc = { isFirstActiveFunc = it },
                    onGotoSearch = { navController.navigate(route = Screens.Search)},
                    onGotoNotification = { navController.navigate(route = Screens.Notification) },
                    onClickBanner = { navController.navigate(route = Screens.WebView(webViewUrl = it))},
                    onGotoRecruitmentDetail = { partyId, partyRecruitmentId -> navController.navigate(Screens.RecruitmentDetail(partyId = partyId, partyRecruitmentId = partyRecruitmentId)) },
                    onGotoPartyDetail = { partyId -> navController.navigate(Screens.PartyDetail(partyId = partyId)) },
                    onGoPartyCreate = { navController.navigate(route = Screens.PartyCreate)},
                    onGotoDetailProfile = { navController.navigate(route = Screens.HomeDetailProfile)}
                )
            }
            composable<Screens.RecruitmentDetail> { backStackEntry ->
                val partyId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyId
                val partyRecruitmentId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyRecruitmentId
                RecruitmentDetailRoute(
                    navController = navController,
                    partyId = partyId,
                    partyRecruitmentId = partyRecruitmentId,
                    onTabClick = { selectedMainTab ->
                        navController.navigate(route = Screens.Main(tabName = selectedMainTab.name) ){
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
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
                    onTabClick = { selectedMainTab ->
                        navController.navigate(route = Screens.Main(tabName = selectedMainTab.name) ){
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
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
            composable<Screens.Search> {
                SearchScreenRoute(
                    navController = navController,
                    onTabClick = { selectedMainTab ->
                        navController.navigate(route = Screens.Main(tabName = selectedMainTab.name) ){
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
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
            composable<Screens.ManageAuth> {
                AuthSettingScreenRoute(
                    context = context,
                    navController = navController
                )
            }
            composable<Screens.UserDelete> {
                UserDeleteScreenRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }
            composable<Screens.ProfileEdit> {
                ProfileEditScreenRoute(
                    navController = navController
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
            composable<Screens.CustomerInquiries> {
                CustomerInquiriesScreenRoute(
                    context = context,
                    navController = navController
                )
            }
            composable<Screens.ServiceIntroduce> {
                ServiceIntroduceScreenRoute(
                    navController = navController
                )
            }
            composable<Screens.Terms> {
                TermsScreenRoute(
                    navController = navController
                )
            }
            composable<Screens.PrivacyPolicy> {
                PrivacyPolicyScreenRoute(
                    navController = navController
                )
            }
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
                    onTabClick = { selectedMainTab ->
                        navController.navigate(route = Screens.Main(tabName = selectedMainTab.name) ){
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
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
            composable<Screens.Notification> {
                NotificationScreenRoute(
                    navController = navController,
                    snackBarHostState = snackBarHostState,
                )
            }

            homeDetailProfileGraph(
                navController = navController,
                snackBarHostState = snackBarHostState,
            )
        }
    }
}