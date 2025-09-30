package com.party.presentation.screen.state

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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.party.common.component.stateTabList
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.SortType
import com.party.presentation.enum.StatusType
import com.party.presentation.screen.state.component.MyPartyArea
import com.party.presentation.screen.state.component.MyRecruitmentArea
import com.party.presentation.screen.state.component.StateScaffoldArea
import com.party.presentation.screen.state.component.StateTabArea
import com.party.presentation.screen.state.viewmodel.StateEvent
import com.party.presentation.screen.state.viewmodel.StateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StateScreenRoute(
    stateViewModel: StateViewModel = hiltViewModel(),
    onGoToSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onGotoPartyDetail: (Int) -> Unit,
    onStartScroll: (Boolean) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        stateViewModel.getMyRecruitment(1, 50, SortType.CREATED_AT.type, OrderDescType.DESC.type)
    }

    LaunchedEffect(key1 = Unit) {
        stateViewModel.successCancel.collectLatest {
            stateViewModel.getMyRecruitment(1, 50, SortType.CREATED_AT.type, OrderDescType.DESC.type)
        }
    }

    val myPartyState by stateViewModel.myPartyState.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()
    val isFabVisible by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    onStartScroll(isFabVisible)

    // 전체/진행중/종료 탭 선택 이벤트
    LaunchedEffect(key1 = myPartyState.selectedStatus) {
        val status = when(myPartyState.selectedStatus){
            "전체" -> null
            StatusType.ACTIVE.toDisplayText() -> StatusType.ACTIVE.type
            StatusType.ARCHIVED.toDisplayText() -> StatusType.ARCHIVED.type
            else -> null
        }
        stateViewModel.getMyParty(1, 50, SortType.CREATED_AT.type, OrderDescType.DESC.type, status = status)
    }

    LaunchedEffect(Unit) {
        StateEvent.scrollToUp.collectLatest {
            listState.animateScrollToItem(0)
        }
    }

    StateScreen(
        selectedTabText = myPartyState.selectedTabText,
        myPartyState = myPartyState,
        listState = listState,
        onAction = { action ->
            stateViewModel.onAction(action = action)
        },
        onGoToSearch = onGoToSearch,
        onGotoNotification = onGotoNotification,
        onMyPartyCardClick = onGotoPartyDetail
    )
}

@Composable
private fun StateScreen(
    selectedTabText: String,
    myPartyState: MyPartyState,
    listState: LazyListState,
    onAction: (MyPartyAction) -> Unit,
    onGoToSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onMyPartyCardClick: (Int) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                StateScaffoldArea(
                    onGoToSearch = onGoToSearch,
                    onGoToAlarm = onGotoNotification,
                )
            },
        ) {
            Column(
                modifier = Modifier
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
                        onClick = onMyPartyCardClick,
                    )
                } else {
                    MyRecruitmentArea(
                        listState = listState,
                        myPartyState = myPartyState,
                        onSelectRecruitmentTab = { selectedRecruitmentTab -> onAction(MyPartyAction.OnSelectRecruitmentTab(selectedRecruitmentTab)) },
                        onShowHelpCard = { iShow -> onAction(MyPartyAction.OnShowHelpCard(isShowHelpCard = iShow)) },
                        onChangeOrderBy = { orderByDesc -> onAction(MyPartyAction.OnRecruitmentOrderByChange(orderByDesc)) },
                        onRefusal = { partyApplicationId, partyId -> onAction(MyPartyAction.OnRejectionParty(partyId = partyId, partyApplicationId = partyApplicationId)) },
                        onAccept = { partyApplicationId, partyId -> onAction(MyPartyAction.OnApprovalParty(partyId = partyId, partyApplicationId = partyApplicationId)) },
                        onCancel = { partyId, partyApplicationId -> onAction(MyPartyAction.OnCancelRecruitment(partyId = partyId, partyApplicationId = partyApplicationId))}
                    )
                }
            }
        }
    }
}