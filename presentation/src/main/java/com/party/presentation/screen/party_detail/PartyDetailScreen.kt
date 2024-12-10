package com.party.presentation.screen.party_detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.component.partyDetailTabList
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyType
import com.party.domain.model.user.PartyAuthority
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.screen.party_detail.component.CustomRightDrawer
import com.party.presentation.screen.party_detail.component.PartyDetailArea
import com.party.presentation.screen.party_detail.component.PartyDetailScaffoldArea
import com.party.presentation.screen.party_detail.viewmodel.PartyViewModel
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)


    Box(modifier = Modifier.fillMaxSize()) {
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
                    onManageClick = {
                        scope.launch { drawerState.open() }
                    },
                    onMoreClick = {},
                )
            },
        ) {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WHITE)
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

        // 커스텀 오른쪽 Drawer
        CustomRightDrawer(
            drawerState = drawerState,
            onGotoPartyEdit = { navController.navigate(Screens.PartyEdit(partyId = partyId)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailScreenPreview() {
    PartyDetailScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        snackBarHostState = SnackbarHostState(),
        partyId = 1,
        state = PartyDetailState(
            partyDetail = PartyDetail(
                id = 0,
                partyType = PartyType(id = 1308, type = "포트폴리오"),
                title = "테스트 제목입니다.",
                content = "같이 파티할 사람 구합니다.",
                image = null,
                status = "active",
                createdAt = "2024-06-05T15:30:45.123Z",
                updatedAt = "2024-06-05T15:30:45.123Z"
            ),
            partyAuthority = PartyAuthority(userId = 1, authority = "master")
        ),
        onNavigationBack = {},
        onAddRecruitment = {},
        onAction = {},
    )
}