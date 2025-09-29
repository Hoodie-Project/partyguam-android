package com.party.presentation.screen.detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.party.common.Screens
import com.party.presentation.screen.detail.screen.DetailChoiceCarrierPositionRoute
import com.party.presentation.screen.detail.screen.DetailProfileCareerRoute
import com.party.presentation.screen.detail.screen.DetailProfileLocationScreenRoute
import com.party.presentation.screen.detail.screen.DetailProfileTraitCompleteRoute
import com.party.presentation.screen.detail.screen.DetailTraitRoute1
import com.party.presentation.screen.detail.screen.DetailTraitRoute2
import com.party.presentation.screen.detail.screen.DetailTraitRoute3
import com.party.presentation.screen.detail.screen.DetailTraitRoute4
import com.party.presentation.screen.detail.viewmodel.DetailProfileViewModel

fun NavGraphBuilder.detailProfileGraph(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
){
    navigation<Screens.DetailProfile>(
        startDestination = Screens.DetailProfileLocation
    ){
        composable<Screens.DetailProfileLocation> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailProfileLocationScreenRoute(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.DetailProfileCareer> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailProfileCareerRoute(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.DetailChoiceCarrierPosition> { backStackEntry ->
            val isMain = backStackEntry.toRoute<Screens.DetailChoiceCarrierPosition>().isMain
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailChoiceCarrierPositionRoute(
                viewModel = viewModel,
                navController = navController,
                isMain = isMain
            )
        }
        composable<Screens.DetailTrait1> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailTraitRoute1(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.DetailTrait2> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailTraitRoute2(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.DetailTrait3> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailTraitRoute3(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.DetailTrait4> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailTraitRoute4(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState
            )
        }
        composable<Screens.DetailTraitComplete> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.DetailProfile>()
            }
            val viewModel: DetailProfileViewModel = hiltViewModel(parentEntry)
            DetailProfileTraitCompleteRoute(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}