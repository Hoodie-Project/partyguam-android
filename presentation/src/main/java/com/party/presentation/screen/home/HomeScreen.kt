package com.party.presentation.screen.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    context: Context,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeTopBar(
            navController = navController,
        )
    }
}