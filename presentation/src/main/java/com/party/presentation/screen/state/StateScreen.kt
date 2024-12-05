package com.party.presentation.screen.state

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.component.searchTabList
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
fun StateScreen(
    context: Context,
    navController: NavHostController,
    stateViewModel: StateViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        stateViewModel.getMyParty(1, 50, "createdAt", "DESC")
    }

    val myPartyState by stateViewModel.myPartyState.collectAsStateWithLifecycle()

    StateScreenContent(
        context = context,
        navController = navController,
        selectedTabText = myPartyState.selectedTabText,
        myPartyState = myPartyState,
        onAction = { action ->
            when (action) {
                is MyPartyAction.OnOrderByChange -> {
                    stateViewModel.onAction(action)
                }
                is MyPartyAction.OnSelectTab -> {
                    stateViewModel.onAction(action)
                }
            }
        },
        onGoToSearch = { navController.navigate(Screens.Search) }
    )
}

@Composable
fun StateScreenContent(
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
                onGoToAlarm = {}
            )
        },
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            StateTabArea(
                searchTabList = stateTabList,
                selectedTabText = selectedTabText,
                onTabClick = {
                    onAction(MyPartyAction.OnSelectTab(it))
                }
            )

            if (selectedTabText == stateTabList[0]) {
                MyPartyArea(
                    myPartyState = myPartyState,
                    onChangeOrderBy = { onAction(MyPartyAction.OnOrderByChange(it)) }
                )
            } else {
                MyRecruitmentArea()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StateScreenContentPreview() {
    StateScreenContent(
        context = LocalContext.current,
        navController = rememberNavController(),
        selectedTabText = stateTabList[0],
        myPartyState = MyPartyState(),
        onAction = {},
        onGoToSearch = {}
    )
}