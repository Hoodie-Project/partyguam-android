package com.party.presentation.screen.main

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.party.common.Screens
import com.party.common.component.homeTopTabList
import com.party.presentation.screen.app.AppState
import com.party.presentation.screen.home.HomeScreenRoute
import com.party.presentation.screen.profile.ProfileScreenRoute
import com.party.presentation.screen.state.StateScreenRoute

fun NavGraphBuilder.mainGraph(
    context: Context,
    navController: NavHostController,
    state: AppState,
    onGotoSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onClickBanner: (String) -> Unit,
    onGotoRecruitmentDetail: (Int, Int) -> Unit,
    onGotoPartyDetail: (Int) -> Unit,
    onGotoDetailProfile: () -> Unit,
    onGoSetting: () -> Unit,
    onGotoProfileEdit: () -> Unit,
    isFirstVersionCheck: Boolean,
    onChangeFirstVersionCheck: (Boolean) -> Unit,
    onTabClick: (String) -> Unit,
    onStartScrollParty: (Boolean) -> Unit,
    onStartScrollRecruitment: (Boolean) -> Unit,
    onStartScroll: (Boolean) -> Unit,
) {
    composable<Screens.Home> {
        HomeScreenRoute(
            context = context,
            state = state,
            homeTopTabList = homeTopTabList,
            isFirstVersionCheck = isFirstVersionCheck,
            onChangeFirstVersionCheck = { onChangeFirstVersionCheck(false)},
            onGotoSearch = onGotoSearch,
            onGotoNotification = onGotoNotification,
            onClickBanner = onClickBanner,
            onGotoRecruitmentDetail = onGotoRecruitmentDetail,
            onGotoPartyDetail = onGotoPartyDetail,
            onGotoDetailProfile = onGotoDetailProfile,
            onTabClick = onTabClick,
            onStartScrollParty = onStartScrollParty,
            onStartScrollRecruitment = onStartScrollRecruitment
        )
    }
    composable<Screens.State> {
        StateScreenRoute(
            onGoToSearch = onGotoSearch,
            onGotoNotification = onGotoNotification,
            onGotoPartyDetail = onGotoPartyDetail,
            onStartScroll = onStartScroll,
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
}