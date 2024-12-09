package com.party.presentation.screen.party_detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.component.partyDetailTabList
import com.party.common.ui.theme.WHITE
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.screen.party_detail.component.PartyDetailArea
import com.party.presentation.screen.party_detail.component.PartyDetailScaffoldArea
import com.party.presentation.screen.party_detail.viewmodel.PartyViewModel

@Composable
fun PartyDetailRoute(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    partyViewModel: PartyViewModel,
    partyId: Int,
) {
    LaunchedEffect(Unit) {
        partyViewModel.getPartyDetail(partyId = partyId)
        partyViewModel.getPartyUsers(partyId = partyId, page = 1, limit = 50, sort = "createdAt", order = "DESC")
        partyViewModel.getPartyRecruitment(partyId = partyId, sort = "createdAt", order = "DESC", main = null)
        partyViewModel.getPartyAuthority(partyId = partyId)
    }

    val state by partyViewModel.state.collectAsStateWithLifecycle()

    PartyDetailScreen(
        context = context,
        navController = navController,
        snackBarHostState = snackBarHostState,
        partyId = partyId,
        state = state,
        onNavigationBack = { navController.popBackStack() },
        onAddRecruitment = { navController.navigate(Screens.RecruitmentCreateScreen(partyId = partyId)) },
        onAction = { action ->
            when(action){
                is PartyDetailAction.OnTabClick -> { partyViewModel.onAction(action) }
                is PartyDetailAction.OnShowPositionFilter -> { partyViewModel.onAction(action) }
                is PartyDetailAction.OnPositionClick -> { partyViewModel.onAction(action) }
                is PartyDetailAction.OnReset -> { partyViewModel.onAction(action) }
                is PartyDetailAction.OnApply -> { partyViewModel.onAction(action) }
                is PartyDetailAction.OnChangeOrderBy -> { partyViewModel.onAction(action) }
            }
        }
    )
}

@Composable
private fun PartyDetailScreen(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    partyId: Int,
    state: PartyDetailState,
    onNavigationBack: () -> Unit,
    onAddRecruitment: () -> Unit,
    onAction: (PartyDetailAction) -> Unit,
){
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
            )
        },
        topBar = {
            PartyDetailScaffoldArea(
                state = state,
                onNavigationClick = onNavigationBack,
                onSharedClick = {},
                onMoreClick = {},
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
        ) {
            PartyDetailArea(
                state = state,
                partyDetailTabList = partyDetailTabList,
                selectedTabText = state.selectedTabText,
                onTabClick = { tabText -> onAction(PartyDetailAction.OnTabClick(tabText)) },
                onPositionClick = { position -> onAction(PartyDetailAction.OnPositionClick(position)) },
                onShowPositionFilter = { isShow -> onAction(PartyDetailAction.OnShowPositionFilter(isShow)) },
                onReset = { onAction(PartyDetailAction.OnReset) },
                onApply = { onAction(PartyDetailAction.OnApply(partyId = partyId)) },
                onAddRecruitment = onAddRecruitment,
                onChangeOrderBy = { orderBy -> onAction(PartyDetailAction.OnChangeOrderBy(orderBy)) }
            )
        }
    }
}