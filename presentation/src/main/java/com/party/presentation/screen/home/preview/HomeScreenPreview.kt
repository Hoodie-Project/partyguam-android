package com.party.presentation.screen.home.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.party.presentation.screen.home.HomeScreen

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        context = LocalContext.current,
        navController = rememberNavController()
    )
}
