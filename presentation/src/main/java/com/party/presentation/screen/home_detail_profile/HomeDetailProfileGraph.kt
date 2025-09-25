package com.party.presentation.screen.home_detail_profile

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.party.common.Screens
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailChoiceCarrierPositionRoute
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailProfileCareerRoute
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailProfileLocationScreenRoute
import com.party.presentation.screen.home_detail_profile.viewmodel.HomeDetailProfileViewModel

fun NavGraphBuilder.homeDetailProfileGraph(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
){
    navigation<Screens.HomeDetailProfile>(
        startDestination = Screens.HomeDetailProfileLocation
    ){
        composable<Screens.HomeDetailProfileLocation> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)
            HomeDetailProfileLocationScreenRoute(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.HomeDetailProfileCareer> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)
            HomeDetailProfileCareerRoute(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable<Screens.HomeDetailChoiceCarrierPosition>{ backStackEntry ->
            val isMain = backStackEntry.toRoute<Screens.HomeDetailChoiceCarrierPosition>().isMain
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)

            HomeDetailChoiceCarrierPositionRoute(
                viewModel = viewModel,
                navController = navController,
                isMain = isMain
            )
        }
    }
}