package com.party.presentation.screen.join

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.party.common.Screens
import com.party.presentation.screen.join.screen.JoinBirthDayScreenRoute
import com.party.presentation.screen.join.screen.JoinCompleteScreenRoute
import com.party.presentation.screen.join.screen.JoinEmailScreenRoute
import com.party.presentation.screen.join.screen.JoinGenderScreenRoute
import com.party.presentation.screen.join.screen.JoinNickNameScreenRoute
import com.party.presentation.screen.join.viewmodel.JoinViewModel

fun NavGraphBuilder.joinGraph(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
){
    navigation<Screens.Join>(
        startDestination = Screens.JoinEmail
    ){
        composable<Screens.JoinEmail> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Screens.Join>()
            }
            val args = parentEntry.toRoute<Screens.Join>()

            val joinViewModel: JoinViewModel = hiltViewModel(viewModelStoreOwner = parentEntry)
            joinViewModel.setEmailAndSignupAccessToken(
                email = args.email,
                signupAccessToken = args.signupAccessToken
            )

            JoinEmailScreenRoute(
                navController = navController,
                joinViewModel = joinViewModel,

                )
        }
        composable<Screens.JoinNickname> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.Join>()
            }

            val joinViewModel: JoinViewModel = hiltViewModel(viewModelStoreOwner = parentEntry)

            JoinNickNameScreenRoute(
                navController = navController,
                snackBarHostState = snackBarHostState,
                joinViewModel = joinViewModel
            )
        }

        composable<Screens.JoinBirthDay> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.Join>()
            }

            val joinViewModel: JoinViewModel = hiltViewModel(viewModelStoreOwner = parentEntry)

            JoinBirthDayScreenRoute(
                navController = navController,
                snackBarHostState = snackBarHostState,
                joinViewModel = joinViewModel
            )
        }

        composable<Screens.JoinGender> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<Screens.Join>()
            }

            val joinViewModel: JoinViewModel = hiltViewModel(viewModelStoreOwner = parentEntry)

            JoinGenderScreenRoute(
                navController = navController,
                snackBarHostState = snackBarHostState,
                joinViewModel = joinViewModel
            )
        }

        composable<Screens.JoinComplete> {
            JoinCompleteScreenRoute(
                navController = navController,
                snackBarHostState = snackBarHostState,
            )
        }
    }
}