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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.Screens
import com.party.common.component.floating.NavigateUpFloatingButton
import com.party.common.component.floating.PartyCreateFloatingButton
import com.party.common.component.stateTabList
import com.party.common.utils.noRippleClickable
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.party.Party
import com.party.domain.model.user.party.PartyType
import com.party.domain.model.user.party.PartyUser
import com.party.domain.model.user.party.Position
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.recruitment.PartyApplication
import com.party.domain.model.user.recruitment.PartyRecruitment
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.SortType
import com.party.presentation.enum.StatusType
import com.party.presentation.screen.state.component.MyPartyArea
import com.party.presentation.screen.state.component.MyRecruitmentArea
import com.party.presentation.screen.state.component.StateFloatingArea
import com.party.presentation.screen.state.component.StateScaffoldArea
import com.party.presentation.screen.state.component.StateTabArea
import com.party.presentation.screen.state.viewmodel.StateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StateScreenRoute(
    stateViewModel: StateViewModel = hiltViewModel(),
    onGoToSearch: () -> Unit,
    onGotoNotification: () -> Unit,
    onGoPartyCreate: () -> Unit,
    onGotoPartyDetail: (Int) -> Unit
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

    // listState 를 내파티 영역, 내 모집공고 영역 동시에 적용
    val listState = rememberLazyListState()
    val isFabVisible = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    myPartyState.isMyPartyScroll = isFabVisible.value

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

    LaunchedEffect(key1 = Unit) {
        stateViewModel.scrollToUp.collectLatest {
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
        onGoPartyCreate = onGoPartyCreate,
        onNavigateUp = { stateViewModel.scrollToTopFun()},
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
    onGoPartyCreate: () -> Unit,
    onNavigateUp: () -> Unit,
    onMyPartyCardClick: (Int) -> Unit,
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

        // 우측 하단 floating 버튼 영역
        StateFloatingArea(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp, end = 20.dp)
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
            onGoPartyCreate = {
                onAction(MyPartyAction.OnExpandedFloating(!myPartyState.isExpandedFloating))
                onGoPartyCreate()
            }
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
        onGotoNotification = {},
        onGoPartyCreate = {},
        onNavigateUp = {},
        onMyPartyCardClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun StateScreenContentPreview2() {
    StateScreen(
        listState = LazyListState(),
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
                            id = 0,
                            status = "active",
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
        onGotoNotification = {},
        onGoPartyCreate = {},
        onNavigateUp = {},
        onMyPartyCardClick = {}
    )
}