package com.party.presentation.screen.manage_applicant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.manage_applicant.component.ManageApplicantScaffoldArea
import com.party.presentation.screen.manage_applicant.viewmodel.ManageApplicantViewModel

@Composable
fun ManageApplicantScreenRoute(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    partyId: Int,
    manageApplicantViewModel: ManageApplicantViewModel = hiltViewModel(),
) {

    val manageApplicantState by manageApplicantViewModel.state.collectAsStateWithLifecycle()

    ManageApplicantScreen(
        snackBarHostState = snackBarHostState,
        manageApplicantState = manageApplicantState,
        onNavigationClick = { navController.popBackStack() }
    )
}

@Composable
private fun ManageApplicantScreen(
    snackBarHostState: SnackbarHostState,
    manageApplicantState: ManageApplicantState,
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            ManageApplicantScaffoldArea(
                isShowHelpIcon = manageApplicantState.isShowHelpIcon,
                onShowHelpCard = {},
                onNavigationClick = onNavigationClick,
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageApplicantScreenPreview() {
    ManageApplicantScreen(
        snackBarHostState = SnackbarHostState(),
        manageApplicantState = ManageApplicantState(),
        onNavigationClick = {}
    )
}