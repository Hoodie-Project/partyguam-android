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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.component.partyDetailTabList
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyType
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.screen.party_detail.component.PartyDetailArea
import com.party.presentation.screen.party_detail.component.PartyDetailScaffoldArea
import com.party.presentation.screen.party_detail.component.RightModalDrawer
import com.party.presentation.screen.party_detail.viewmodel.PartyViewModel
import kotlinx.coroutines.launch

@Composable
fun PartyDetailRoute(
    context: Context,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    partyViewModel: PartyViewModel = hiltViewModel(),
    partyId: Int,
) {
    LaunchedEffect(Unit) {
        partyViewModel.getPartyDetail(partyId = partyId)
        partyViewModel.getPartyUsers(partyId = partyId, page = 1, limit = 50, sort = "createdAt", order = "DESC")
        partyViewModel.getPartyRecruitment(partyId = partyId, sort = "createdAt", order = "DESC", main = null, status = "active")
        partyViewModel.getPartyAuthority(partyId = partyId)
    }

    val state by partyViewModel.state.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    RightModalDrawer(
        currentTitle = "",
        drawerState = drawerState,
        content = {
            PartyDetailScreen(
                context = context,
                navController = navController,
                snackBarHostState = snackBarHostState,
                partyId = partyId,
                state = state,
                onNavigationBack = { navController.popBackStack() },
                onAddRecruitment = { navController.navigate(Screens.RecruitmentCreate(partyId = partyId)) },
                onManageClick = { scope.launch { drawerState.open() } },
                onAction = { action ->
                    when(action){
                        is PartyDetailAction.OnTabClick -> { partyViewModel.onAction(action) }
                        is PartyDetailAction.OnShowPositionFilter -> { partyViewModel.onAction(action) }
                        is PartyDetailAction.OnPositionClick -> { partyViewModel.onAction(action) }
                        is PartyDetailAction.OnReset -> { partyViewModel.onAction(action) }
                        is PartyDetailAction.OnApply -> { partyViewModel.onAction(action) }
                        is PartyDetailAction.OnChangeOrderBy -> { partyViewModel.onAction(action) }
                        is PartyDetailAction.OnChangeProgress -> { partyViewModel.onAction(action)}
                    }
                }
            )
        },
        onGotoPartyEdit = {
            scope.launch { drawerState.close() }
            navController.navigate(Screens.PartyEdit(partyId = partyId))
        },
        onGotoPartyUser = {
            scope.launch { drawerState.close() }
            navController.navigate(Screens.PartyUserManage(partyId = partyId))
        },
        onGotoPartyRecruitmentEdit = {
            scope.launch { drawerState.close() }
            navController.navigate(Screens.PartyEditRecruitment(partyId = partyId))
        },
        onGotoManageApplicant = {
            scope.launch { drawerState.close() }
            navController.navigate(Screens.ManageApplicant(partyId = partyId))
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
    onManageClick: () -> Unit,
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
                onManageClick = {
                    onManageClick()
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
                    onChangeOrderBy = { orderBy -> onAction(PartyDetailAction.OnChangeOrderBy(orderBy)) },
                    onChangeProgress = { isProgress -> onAction(PartyDetailAction.OnChangeProgress(isProgress = isProgress, partyId = partyId)) }
                )
            }
        }
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
            partyAuthority = PartyAuthority(id = 1, authority = "master", position = PartyAuthorityPosition(0, "개발자", "안드로이드"))
        ),
        onNavigationBack = {},
        onAddRecruitment = {},
        onManageClick = {},
        onAction = {},
    )
}