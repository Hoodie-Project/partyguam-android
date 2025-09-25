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
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailProfileTraitCompleteRoute
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailTraitRoute1
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailTraitRoute2
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailTraitRoute3
import com.party.presentation.screen.home_detail_profile.screen.HomeDetailTraitRoute4
import com.party.presentation.screen.home_detail_profile.viewmodel.HomeDetailProfileViewModel

fun NavGraphBuilder.homeDetailProfileGraph(
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

        composable<Screens.Trait1> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)
            HomeDetailTraitRoute1(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }

        composable<Screens.Trait2> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)
            HomeDetailTraitRoute2(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.Trait3> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)
            HomeDetailTraitRoute3(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.Trait4> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)
            HomeDetailTraitRoute4(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.TraitComplete> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.HomeDetailProfile>()
            }
            val viewModel: HomeDetailProfileViewModel = hiltViewModel(parentEntry)
            HomeDetailProfileTraitCompleteRoute(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}