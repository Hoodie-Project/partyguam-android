package com.party.presentation.screen.info_center

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.party.common.Screens

fun NavGraphBuilder.infoCenterGraph(
    context: Context,
    navController: NavHostController,
) {
    composable<Screens.CustomerInquiries> {
        CustomerInquiriesScreenRoute(
            context = context,
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
}