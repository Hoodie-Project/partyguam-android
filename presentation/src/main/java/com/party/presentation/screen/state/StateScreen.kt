package com.party.presentation.screen.state

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.component.stateTabList
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.screen.state.component.MyPartyArea
import com.party.presentation.screen.state.component.MyRecruitmentArea
import com.party.presentation.screen.state.component.StateScaffoldArea
import com.party.presentation.screen.state.component.StateTabArea
import com.party.presentation.screen.state.viewmodel.StateViewModel

@Composable
fun StateScreenRoute(
    context: Context,
    navController: NavHostController,
    stateViewModel: StateViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        stateViewModel.getMyParty(1, 50, "createdAt", "DESC")
        stateViewModel.getMyRecruitment(1, 50, "createdAt", "DESC")
    }

    val myPartyState by stateViewModel.myPartyState.collectAsStateWithLifecycle()

    StateScreen(
        context = context,
        navController = navController,
        selectedTabText = myPartyState.selectedTabText,
        myPartyState = myPartyState,
        onAction = { action ->
            when (action) {
                is MyPartyAction.OnSelectTab -> stateViewModel.onAction(action)
                is MyPartyAction.OnShowHelpCard -> stateViewModel.onAction(action)
                is MyPartyAction.OnOrderByChange -> stateViewModel.onAction(action)
                is MyPartyAction.OnRecruitmentOrderByChange -> stateViewModel.onAction(action)
            }
        },
        onGoToSearch = { navController.navigate(Screens.Search) },
    )
}

@Composable
private fun StateScreen(
    context: Context,
    navController: NavHostController,
    selectedTabText: String,
    myPartyState: MyPartyState,
    onAction: (MyPartyAction) -> Unit,
    onGoToSearch: () -> Unit,
) {
    Scaffold(
        topBar = {
            StateScaffoldArea(
                onGoToSearch = onGoToSearch,
                onGoToAlarm = {},
            )
        },
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(WHITE)
                    .padding(it)
                    .padding(horizontal = MEDIUM_PADDING_SIZE),
        ) {
            StateTabArea(
                searchTabList = stateTabList,
                selectedTabText = selectedTabText,
                onTabClick = { selectedTabText -> onAction(MyPartyAction.OnSelectTab(selectedTabText)) },
            )

            if (selectedTabText == stateTabList[0]) {
                MyPartyArea(
                    myPartyState = myPartyState,
                    onChangeOrderBy = { orderByDesc -> onAction(MyPartyAction.OnOrderByChange(orderByDesc)) },
                )
            } else {
                MyRecruitmentArea(
                    myPartyState = myPartyState,
                    onShowHelpCard = { iShow -> onAction(MyPartyAction.OnShowHelpCard(isShowHelpCard = iShow)) },
                    onChangeOrderBy = { orderByDesc -> onAction(MyPartyAction.OnRecruitmentOrderByChange(orderByDesc)) },
                    onRefusal = { },
                    onAccept = { },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StateScreenContentPreview() {
    StateScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        selectedTabText = stateTabList[0],
        myPartyState = MyPartyState(),
        onAction = {},
        onGoToSearch = {},
    )
}
