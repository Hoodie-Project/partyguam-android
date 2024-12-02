package com.party.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.party.common.component.homeTopTabList
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.detail.choice_carrier_position.ChoiceCarrierPositionScreen
import com.party.presentation.screen.detail.detail_carrier.DetailCarrierScreen
import com.party.presentation.screen.detail.detail_profile.DetailProfileScreen
import com.party.presentation.screen.detail.select_tendency.SelectTendencyCompleteScreen
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen1
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen2
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen3
import com.party.presentation.screen.detail.select_tendency.SelectTendencyScreen4
import com.party.presentation.screen.home.HomeScreen
import com.party.presentation.screen.home.viewmodel.HomeViewModel
import com.party.presentation.screen.join.birthday.JoinBirthDayScreen
import com.party.presentation.screen.join.complete.JoinCompleteScreen
import com.party.presentation.screen.join.email.JoinEmailScreen
import com.party.presentation.screen.join.gender.JoinGenderScreen
import com.party.presentation.screen.join.nickname.JoinNickNameScreen
import com.party.presentation.screen.login.LoginScreen
import com.party.presentation.screen.party_apply.PartyApplyScreen
import com.party.presentation.screen.party_apply.viewmodel.PartyApplyViewModel
import com.party.presentation.screen.party_create.PartyCreateScreen
import com.party.presentation.screen.party_create.viewmodel.PartyCreateViewModel
import com.party.presentation.screen.party_detail.PartyDetailScreen
import com.party.presentation.screen.party_detail.viewmodel.PartyViewModel
import com.party.presentation.screen.profile.ProfileScreen
import com.party.presentation.screen.recruitment_create.RecruitmentCreateScreen
import com.party.presentation.screen.recruitment_create.viewmodel.RecruitmentCreateViewModel
import com.party.presentation.screen.recruitment_detail.RecruitmentDetailScreen
import com.party.presentation.screen.search.SearchScreen
import com.party.presentation.screen.search.viewmodel.SearchViewModel
import com.party.presentation.screen.splash.SplashScreen
import com.party.presentation.screen.state.StateScreen
import com.party.presentation.shared.SharedViewModel

const val ANIMATION_DURATION = 500

@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val snackBarHostState = remember { SnackbarHostState() }

    var selectedTabText by remember {
        mutableStateOf(homeTopTabList[0])
    }

    val sharedViewModel = hiltViewModel<SharedViewModel>()

    NavHost(
        navController = navController,
        startDestination = Screens.Splash,
        modifier = Modifier
            .fillMaxSize()
            .background(WHITE),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
    ){
        composable<Screens.Splash> {
            SplashScreen(
                navController = navController,
            )
        }
        composable<Screens.Login> {
            LoginScreen(
                navController = navController,
                context = context,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.JoinEmail> { backStackEntry ->
            val userEmail = backStackEntry.toRoute<Screens.JoinEmail>().userEmail
            val signupAccessToken = backStackEntry.toRoute<Screens.JoinEmail>().signupAccessToken
            JoinEmailScreen(
                navController = navController,
                userEmail = userEmail,
                signupAccessToken = signupAccessToken,
            )
        }
        composable<Screens.JoinNickName> { backStackEntry ->
            val userEmail = backStackEntry.toRoute<Screens.JoinEmail>().userEmail
            val signupAccessToken = backStackEntry.toRoute<Screens.JoinEmail>().signupAccessToken
            JoinNickNameScreen(
                context = context,
                navController = navController,
                snackBarHostState = snackBarHostState,
                userEmail = userEmail,
                signupAccessToken = signupAccessToken,
            )
        }
        composable<Screens.JoinBirthDay> { backStackEntry ->
            val userEmail = backStackEntry.toRoute<Screens.JoinBirthDay>().userEmail
            val signupAccessToken = backStackEntry.toRoute<Screens.JoinBirthDay>().signupAccessToken
            val userNickName = backStackEntry.toRoute<Screens.JoinBirthDay>().userNickName
            JoinBirthDayScreen(
                navController = navController,
                userEmail = userEmail,
                signupAccessToken = signupAccessToken,
                userNickName = userNickName,
            )
        }
        composable<Screens.JoinGender> { backStackEntry ->
            val userEmail = backStackEntry.toRoute<Screens.JoinGender>().userEmail
            val signupAccessToken = backStackEntry.toRoute<Screens.JoinGender>().signupAccessToken
            val userNickName = backStackEntry.toRoute<Screens.JoinGender>().userNickName
            val userBirthDay = backStackEntry.toRoute<Screens.JoinGender>().userBirthDay
            JoinGenderScreen(
                context = context,
                navController = navController,
                snackBarHostState = snackBarHostState,
                userEmail = userEmail,
                signupAccessToken = signupAccessToken,
                userNickName = userNickName,
                userBirthDay = userBirthDay,
            )
        }
        composable<Screens.JoinComplete> {
            JoinCompleteScreen(navController = navController)
        }
        composable<Screens.DetailProfile> {
            DetailProfileScreen(
                context = context,
                snackBarHostState = snackBarHostState,
                navController = navController
            )
        }
        composable<Screens.DetailCarrier> {
            DetailCarrierScreen(
                context = context,
                snackBarHostState = snackBarHostState,
                navController = navController,
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
        composable<Screens.Home> {
            HomeScreen(
                context = context,
                snackBarHostState = snackBarHostState,
                navController = navController,
                selectedTabText = selectedTabText,
                homeTopTabList = homeTopTabList,
                onTabClick = { selectedText -> selectedTabText = selectedText },
                onGoRecruitment = { selectedTabText = homeTopTabList[2] },
                onRecruitmentItemClick = { partyRecruitmentId, partyId ->
                    navController.navigate(Screens.RecruitmentDetail(partyRecruitmentId = partyRecruitmentId, partyId = partyId))
                },
                sharedViewModel = sharedViewModel
            )
        }
        composable<Screens.State> {
            StateScreen(
                context = context,
                navController = navController,
            )
        }
        composable<Screens.Profile> {
            ProfileScreen(
                context = context,
                navController = navController,
            )
        }
        composable<Screens.RecruitmentDetail> { backStackEntry ->
            val partyId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyId
            val partyRecruitmentId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyRecruitmentId
            RecruitmentDetailScreen(
                context = context,
                navController = navController,
                partyRecruitmentId = partyRecruitmentId,
                onClick = {
                    navController.navigate(Screens.PartyApply(partyId = partyId, partyRecruitmentId = partyRecruitmentId))
                }
            )
        }
        composable<Screens.PartyApply> { backStackEntry ->
            val partyId = backStackEntry.toRoute<Screens.PartyApply>().partyId
            val partyRecruitmentId = backStackEntry.toRoute<Screens.RecruitmentDetail>().partyRecruitmentId
            val partyApplyViewModel = hiltViewModel<PartyApplyViewModel>()
            PartyApplyScreen(
                navController = navController,
                snackBarHostState = snackBarHostState,
                partyApplyViewModel = partyApplyViewModel,
                partyId = partyId,
                partyRecruitmentId = partyRecruitmentId,
            )
        }
        composable<Screens.PartyDetail> { backStackEntry ->
            val partyId = backStackEntry.toRoute<Screens.PartyDetail>().partyId
            val partyViewModel = hiltViewModel<PartyViewModel>()
            PartyDetailScreen(
                context = context,
                navController = navController,
                snackBarHostState = snackBarHostState,
                partyViewModel = partyViewModel,
                partyId = partyId,
            )
        }
        composable<Screens.PartyCreate> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val partyCreateViewModel = hiltViewModel<PartyCreateViewModel>()
            PartyCreateScreen(
                navController = navController,
                snackBarHostState = snackBarHostState,
                homeViewModel = homeViewModel,
                partyCreateViewModel = partyCreateViewModel
            )
        }
        composable<Screens.RecruitmentCreateScreen> { backStackEntry ->
            val partyId = backStackEntry.toRoute<Screens.RecruitmentCreateScreen>().partyId
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val recruitmentCreateViewModel = hiltViewModel<RecruitmentCreateViewModel>()
            RecruitmentCreateScreen(
                snackBarHostState = snackBarHostState,
                navController = navController,
                homeViewModel = homeViewModel,
                recruitmentCreateViewModel = recruitmentCreateViewModel,
                partyId = partyId
            )
        }
        composable<Screens.Search> {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(
                snackBarHostState = snackBarHostState,
                navController = navController,
                searchViewModel = searchViewModel,
            )
        }
    }
}