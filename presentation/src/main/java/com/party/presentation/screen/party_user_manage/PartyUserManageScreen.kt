package com.party.presentation.screen.party_user_manage

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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.utils.HeightSpacer
import com.party.common.component.bottomsheet.NoButtonAndGotoScreenBottomSheet
import com.party.common.component.bottomsheet.partyMasterManageList
import com.party.common.component.bottomsheet.partyMemberManageList
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.utils.noRippleClickable
import com.party.common.utils.snackBarMessage
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.domain.model.party.PartyMemberInfo
import com.party.domain.model.party.PartyMemberPosition
import com.party.domain.model.party.PartyUserInfo
import com.party.common.Screens
import com.party.presentation.component.bottomsheet.OneSelectMainAndSubPositionBottomSheet
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.PartyAuthorityType
import com.party.presentation.enum.SortType
import com.party.presentation.screen.party_detail.component.RightModalDrawer
import com.party.presentation.screen.party_user_manage.component.PartyUserCountArea
import com.party.presentation.screen.party_user_manage.component.PartyUserFilterArea
import com.party.presentation.screen.party_user_manage.component.PartyUserListArea
import com.party.presentation.screen.party_user_manage.component.PartyUserScaffoldArea
import com.party.presentation.screen.party_user_manage.component.PartyUserSearchArea
import com.party.presentation.screen.party_user_manage.viewmodel.PartyUserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun PartyUserManageScreenRoute(
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    partyId: Int,
    partyUserViewModel: PartyUserViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        partyUserViewModel.getPartyMembers(
            partyId = partyId,
            page = 1,
            limit = 50,
            sort = SortType.CREATED_AT.type,
            order = OrderDescType.DESC.type,
            main = null,
            nickname = null
        )
    }

    LaunchedEffect(Unit) {
        partyUserViewModel.errorFlow.collectLatest { errorMessage ->
            snackBarMessage(snackBarHostState, errorMessage)
        }
    }

    // 포지션 변경 성공
    LaunchedEffect(key1 = Unit) {
        partyUserViewModel.modifySuccessFlow.collectLatest {
            snackBarMessage(snackBarHostState, "포지션이 변경되었어요.")
        }
    }

    // 파티원 내보내기 성공
    LaunchedEffect(key1 = Unit) {
        partyUserViewModel.deleteSuccessFlow.collectLatest {
            snackBarMessage(snackBarHostState, "파티원을 내보냈어요.")
        }
    }

    // 파티장 위임하기 성공
    LaunchedEffect(key1 = Unit) {
        partyUserViewModel.delegateMasterSuccessFlow.collectLatest {
            snackBarMessage(snackBarHostState, "파티장을 위임했어요.")
            navController.popBackStack()
        }
    }

    val partyUserState by partyUserViewModel.state.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    RightModalDrawer(
        currentTitle = "파티원 관리",
        drawerState = drawerState,
        content = {
            PartyUserManageScreen(
                snackBarHostState = snackBarHostState,
                partyUserState = partyUserState,
                partyId = partyId,
                onAction = { action ->
                    partyUserViewModel.onAction(action = action)
                },
                onNavigationBack = { navController.popBackStack() },
                onDismissBackDialog = { partyUserViewModel.dismissBackDialog() },
                onDismissChangeMasterDialog = { partyUserViewModel.dismissChangeMasterDialog() },
                onManageClick = { scope.launch { drawerState.open() } }
            )
        },
        onGotoPartyEdit = {
            scope.launch { drawerState.close() }
            navController.popBackStack()
            navController.navigate(Screens.PartyEdit(partyId = partyId))
        },
        onGotoPartyUser = {
            scope.launch { drawerState.close() }
            navController.navigate(Screens.PartyUserManage(partyId = partyId))
        },
        onGotoPartyRecruitmentEdit = {
            scope.launch { drawerState.close() }
            navController.popBackStack()
            navController.navigate(Screens.PartyEditRecruitment(partyId = partyId))
        },
        onGotoManageApplicant = {
            scope.launch { drawerState.close() }
            navController.popBackStack()
            navController.navigate(Screens.ManageApplicant(partyId = partyId))
        }
    )

}

@Composable
private fun PartyUserManageScreen(
    snackBarHostState: SnackbarHostState,
    partyUserState: PartyUserState,
    partyId: Int,
    onAction: (PartyUserAction) -> Unit,
    onNavigationBack: () -> Unit,
    onDismissBackDialog: () -> Unit,
    onDismissChangeMasterDialog: () -> Unit,
    onManageClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .blur(
                radiusX = if (partyUserState.isShowModifyDialog || partyUserState.isShowChangeMaster) 10.dp else 0.dp,
                radiusY = if (partyUserState.isShowModifyDialog || partyUserState.isShowChangeMaster) 10.dp else 0.dp,
            ),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyUserScaffoldArea(
                onNavigationBack = onNavigationBack,
                onManageClick = onManageClick
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
            // 파티원
            HeightSpacer(heightDp = 16.dp)
            PartyUserCountArea(
                searchedListSize = partyUserState.filteredPartyUserList.size,
                userListSize = partyUserState.partyUserList.size
            )
            HeightSpacer(18.dp)

            // 검색
            PartyUserSearchArea(
                inputText = partyUserState.inputText,
                placeHolder = "닉네임을 검색해 보세요.",
                onValueChange = { inputText ->
                    onAction(PartyUserAction.OnChangeInputText(inputText))
                },
                onRemoveAll = { onAction(PartyUserAction.OnChangeInputText("")) },
                searchAction = { inputText ->
                    onAction(PartyUserAction.OnSearch(inputText))
                }
            )

            // 필터
            HeightSpacer(heightDp = 16.dp)
            PartyUserFilterArea(
                partyUserState = partyUserState,
                isPartyTypeFilterClick = { onAction(PartyUserAction.OnChangePositionBottomSheet(true)) },
                onChangeOrderBy = { isDesc -> onAction(PartyUserAction.OnChangeOrderBy(isDesc)) },
                onShowPositionFilter = { isShow -> onAction(PartyUserAction.OnChangePositionBottomSheet(isShow)) },
                onReset = { onAction(PartyUserAction.OnChangeMainPosition("")) },
                onApply = { selectedMainPosition -> onAction(PartyUserAction.OnApply(partyId = partyId, selectedMainPosition = selectedMainPosition)) }
            )

            PartyUserListArea(
                partyUserState = partyUserState,
                onClick = { selectedMemberAuthority, selectedMemberId ->
                    onAction(PartyUserAction.OnManageBottomSheet(true))
                    onAction(PartyUserAction.OnSelectedUser(selectedMemberAuthority, selectedMemberId))
                }
            )
        }
    }

    if(partyUserState.isShowModifyDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.2f))
                .noRippleClickable { onDismissBackDialog() }
        ) {
            TwoButtonDialog(
                dialogTitle = "포지션 변경",
                description = "해당 포지션으로 변경하시나요?",
                cancelButtonText = "닫기",
                confirmButtonText = "변경하기",
                onCancel = onDismissBackDialog,
                onConfirm = { onAction(PartyUserAction.OnModifyUserPosition(partyId)) }
            )
        }
    }

    if(partyUserState.manageBottomSheet){
        NoButtonAndGotoScreenBottomSheet(
            bottomSheetTitle = "파티원 관리",
            contentList = if(partyUserState.selectedMemberAuthority == PartyAuthorityType.MASTER.authority) partyMasterManageList else partyMemberManageList,
            onBottomSheetClose = {
                onAction(PartyUserAction.OnManageBottomSheet(false)) },
            onClick = { text ->
                when(text){
                    "포지션 변경" -> {
                        onAction(PartyUserAction.OnManageBottomSheet(false))
                        onAction(PartyUserAction.OnChangeModifyPositionSheet(true))
                    }
                    "내보내기" -> {
                        onAction(PartyUserAction.OnDeletePartyMember(partyId = partyId))
                    }
                    "파티장 위임" -> {
                        onAction(PartyUserAction.OnManageBottomSheet(false))
                        onAction(PartyUserAction.OnChangeMasterDialog(true))
                    }
                }
            }
        )
    }

    if(partyUserState.isMainPositionBottomSheetShow){
        OneSelectMainAndSubPositionBottomSheet(
            subPositionList = partyUserState.getSubPositionList,
            bottomSheetTitle = "모집 포지션",
            selectedMainPosition = partyUserState.selectedMainPosition,
            selectedSubPosition = partyUserState.selectedSubPosition,
            buttonText = "변경하기",
            onModelClose = { onAction(PartyUserAction.OnChangeModifyPositionSheet(false)) },
            onApply = { _, selectedSubPosition->
                onAction(PartyUserAction.OnChangeSelectedSubPosition(selectedSubPosition))
                onAction(PartyUserAction.OnChangeModifyDialog(true))
            },
            onClickMainPosition = { onAction(PartyUserAction.OnMainPositionClick(it)) }
        )
    }

    if(partyUserState.isShowChangeMaster){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.2f))
                .noRippleClickable { onDismissChangeMasterDialog() }
        ) {
            TwoButtonDialog(
                dialogTitle = "파티장 위임",
                description = "위임 후에는 되돌릴 수 없어요.\n정말 이 파티원에게 위임하시나요?",
                cancelButtonText = "닫기",
                confirmButtonText = "위임하기",
                onCancel = onDismissChangeMasterDialog,
                onConfirm = { onAction(PartyUserAction.OnChangeMaster(partyId)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyUserManageScreenPreview() {
    PartyUserManageScreen(
        snackBarHostState = SnackbarHostState(),
        partyUserState = PartyUserState(
            partyUserList = listOf(
                PartyMemberInfo(
                    id = 4865,
                    createdAt = "2024-06-05T15:30:45.123Z",
                    status = "Joseh",
                    authority = "master",
                    user = PartyUserInfo(
                        nickname = "닉네임입니다.",
                        image = null
                    ),
                    position = PartyMemberPosition(
                        main = "개발자",
                        sub = "안드로이드"
                    )
                ),
                PartyMemberInfo(
                    id = 4865,
                    createdAt = "2024-06-05T15:30:45.123Z",
                    status = "Joseh",
                    authority = "member",
                    user = PartyUserInfo(
                        nickname = "닉네임입니다.",
                        image = null
                    ),
                    position = PartyMemberPosition(
                        main = "개발자",
                        sub = "안드로이드"
                    )
                ),
            )
        ),
        partyId = 1,
        onAction = {},
        onNavigationBack = {},
        onDismissBackDialog = {},
        onManageClick = {},
        onDismissChangeMasterDialog = {}
    )
}