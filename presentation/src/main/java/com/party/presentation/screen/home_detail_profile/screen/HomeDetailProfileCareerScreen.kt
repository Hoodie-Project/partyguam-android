package com.party.presentation.screen.home_detail_profile.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.party.presentation.screen.home_detail_profile.viewmodel.HomeDetailProfileViewModel

@Composable
fun HomeDetailProfileCareerRoute(
    viewModel: HomeDetailProfileViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    HomeDetailProfileCareerScreen()
}

@Composable
private fun HomeDetailProfileCareerScreen(modifier: Modifier = Modifier) {
    
}