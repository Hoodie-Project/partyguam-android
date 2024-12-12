package com.party.presentation.screen.state

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.component.floating.NavigateUpFloatingButton
import com.party.common.component.floating.PartyCreateFloatingButton
import com.party.common.component.stateTabList
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.party.Party
import com.party.domain.model.user.party.PartyType
import com.party.domain.model.user.party.PartyUser
import com.party.domain.model.user.party.Position
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.recruitment.PartyApplication
import com.party.domain.model.user.recruitment.PartyRecruitment
import com.party.navigation.BottomNavigationBar
import com.party.navigation.Screens
import com.party.presentation.screen.state.component.MyPartyArea
import com.party.presentation.screen.state.component.MyRecruitmentArea
import com.party.presentation.screen.state.component.StateFloatingArea
import com.party.presentation.screen.state.component.StateScaffoldArea
import com.party.presentation.screen.state.component.StateTabArea
import com.party.presentation.screen.state.viewmodel.StateViewModel
import kotlinx.coroutines.flow.collectLatest

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

    // listState 를 내파티 영역, 내 모집공고 영역 동시에 적용
    val listState = rememberLazyListState()
    val isFabVisible = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    myPartyState.isMyPartyScroll = isFabVisible.value

    LaunchedEffect(Unit) {
        stateViewModel.scrollToUp.collectLatest {
            listState.animateScrollToItem(0)
        }
    }

    StateScreen(
        context = context,
        navController = navController,
        selectedTabText = myPartyState.selectedTabText,
        myPartyState = myPartyState,
        listState = listState,
        onAction = { action ->
            when (action) {
                is MyPartyAction.OnSelectTab -> stateViewModel.onAction(action)
                is MyPartyAction.OnShowHelpCard -> stateViewModel.onAction(action)
                is MyPartyAction.OnSelectRecruitmentTab -> stateViewModel.onAction(action)
                is MyPartyAction.OnSelectStatus -> stateViewModel.onAction(action)
                is MyPartyAction.OnOrderByChange -> stateViewModel.onAction(action)
                is MyPartyAction.OnRecruitmentOrderByChange -> stateViewModel.onAction(action)
                is MyPartyAction.OnExpandedFloating -> stateViewModel.onAction(action)
            }
        },
        onGoToSearch = { navController.navigate(Screens.Search) },
        onGoPartyCreate = { navController.navigate(Screens.PartyCreate) },
        onNavigateUp = { stateViewModel.scrollToTopFun()},
    )
}

@Composable
private fun StateScreen(
    context: Context,
    navController: NavHostController,
    selectedTabText: String,
    myPartyState: MyPartyState,
    listState: LazyListState,
    onAction: (MyPartyAction) -> Unit,
    onGoToSearch: () -> Unit,
    onGoPartyCreate: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .blur(
                    radiusX = if (myPartyState.isExpandedFloating) 10.dp else 0.dp,
                    radiusY = if (myPartyState.isExpandedFloating) 10.dp else 0.dp,
                ),
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
                        listState = listState,
                        myPartyState = myPartyState,
                        onSelectStatus = { status -> onAction(MyPartyAction.OnSelectStatus(status)) },
                        onChangeOrderBy = { orderByDesc -> onAction(MyPartyAction.OnOrderByChange(orderByDesc)) },
                    )
                } else {
                    MyRecruitmentArea(
                        listState = listState,
                        myPartyState = myPartyState,
                        onSelectRecruitmentTab = { selectedRecruitmentTab -> onAction(MyPartyAction.OnSelectRecruitmentTab(selectedRecruitmentTab)) },
                        onShowHelpCard = { iShow -> onAction(MyPartyAction.OnShowHelpCard(isShowHelpCard = iShow)) },
                        onChangeOrderBy = { orderByDesc -> onAction(MyPartyAction.OnRecruitmentOrderByChange(orderByDesc)) },
                        onRefusal = { },
                        onAccept = { },
                    )
                }
            }
        }

        // 우측 하단 floating 버튼 영역
        StateFloatingArea(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 98.dp, end = 20.dp)
                .zIndex(1f),
            isExpandedFloatingButton = myPartyState.isExpandedFloating,
            partyCreateFloating = {
                PartyCreateFloatingButton(
                    isExpandedFloatingButton = myPartyState.isExpandedFloating,
                    onClick = {
                        onAction(MyPartyAction.OnExpandedFloating(!myPartyState.isExpandedFloating))
                    }
                )
            },
            navigateUpFloating = {
                NavigateUpFloatingButton(
                    isShowNavigateUpFloatingButton = myPartyState.isMyPartyScroll,
                    isExpandedFloatingButton = myPartyState.isExpandedFloating,
                    onClick = onNavigateUp
                )
            },
            onGoPartyCreate = onGoPartyCreate
        )

        // 파티 생성하기 floating 누르면 검정 화면
        if(myPartyState.isExpandedFloating){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.7f))
                    .noRippleClickable { onAction(MyPartyAction.OnExpandedFloating(false)) }
                    .zIndex(0f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StateScreenContentPreview() {
    StateScreen(
        listState = LazyListState(),
        context = LocalContext.current,
        navController = rememberNavController(),
        selectedTabText = stateTabList[0],
        myPartyState = MyPartyState(
            isExpandedFloating = false,
            isMyPartyScroll = true,
            isMyPartyLoading = false,
            myPartyList = MyParty(
                total = 0,
                partyUsers = listOf(
                    PartyUser(
                        id = 6131,
                        createdAt = "2024-06-05T15:30:45.123Z",
                        position = Position(
                            main = "개발자",
                            sub = "안드로이드"
                        ),
                        party = Party(
                            id = 7198,
                            title = "파티구합니다",
                            image = "",
                            status = "active",
                            partyType = PartyType(type = "포트폴리오")
                        )
                    )
                )
            )
        ),
        onAction = {},
        onGoToSearch = {},
        onGoPartyCreate = {},
        onNavigateUp = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun StateScreenContentPreview2() {
    StateScreen(
        listState = LazyListState(),
        context = LocalContext.current,
        navController = rememberNavController(),
        selectedTabText = stateTabList[1],
        myPartyState = MyPartyState(
            isExpandedFloating = false,
            isMyPartyScroll = true,
            isMyRecruitmentLoading = false,
            myPartyList = MyParty(
                total = 0,
                partyUsers = listOf(
                    PartyUser(
                        id = 6131,
                        createdAt = "2024-06-05T15:30:45.123Z",
                        position = Position(
                            main = "개발자",
                            sub = "안드로이드"
                        ),
                        party = Party(
                            id = 7198,
                            title = "파티구합니다",
                            image = "",
                            status = "active",
                            partyType = PartyType(type = "포트폴리오")
                        )
                    )
                )
            ),
            myRecruitmentList = MyRecruitment(
                total = 0,
                partyApplications = listOf(
                    PartyApplication(
                        id = 9023,
                        message = "nonumy",
                        status = "ancillae",
                        createdAt = "2024-06-05T15:30:45.123Z",
                        partyRecruitment = PartyRecruitment(
                            position = com.party.domain.model.user.recruitment.Position(
                                main = "vitae",
                                sub = "tristique"
                            ), party = com.party.domain.model.user.recruitment.Party(
                                id = 5641,
                                title = "fuisset",
                                image = "consequat",
                                partyType = com.party.domain.model.user.recruitment.PartyType(type = "deseruisse")
                            )
                        )
                    )
                )
            )
        ),
        onAction = {},
        onGoToSearch = {},
        onGoPartyCreate = {},
        onNavigateUp = {},
    )
}