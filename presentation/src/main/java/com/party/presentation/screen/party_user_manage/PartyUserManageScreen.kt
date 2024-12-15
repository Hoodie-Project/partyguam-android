package com.party.presentation.screen.party_user_manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.component.bottomsheet.NoButtonAndGotoScreenBottomSheet
import com.party.common.component.bottomsheet.partyMasterManageList
import com.party.common.component.bottomsheet.partyMemberManageList
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyMemberInfo
import com.party.domain.model.party.PartyMemberPosition
import com.party.domain.model.party.PartyUserInfo
import com.party.presentation.component.bottomsheet.OneSelectMainAndSubPositionBottomSheet
import com.party.presentation.enum.PartyAuthorityType
import com.party.presentation.screen.party_user_manage.component.PartyUserCountArea
import com.party.presentation.screen.party_user_manage.component.PartyUserFilterArea
import com.party.presentation.screen.party_user_manage.component.PartyUserListArea
import com.party.presentation.screen.party_user_manage.component.PartyUserScaffoldArea
import com.party.presentation.screen.party_user_manage.component.PartyUserSearchArea
import com.party.presentation.screen.party_user_manage.viewmodel.PartyUserViewModel

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
            sort = "createdAt",
            order = "DESC",
            main = null
        )
    }
    val partyUserState by partyUserViewModel.state.collectAsStateWithLifecycle()

    PartyUserManageScreen(
        snackBarHostState = snackBarHostState,
        partyUserState = partyUserState,
        partyId = partyId,
        onAction = { action ->
            when(action){
                is PartyUserAction.OnChangeInputText -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnChangePositionBottomSheet -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnChangeMainPosition -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnChangeOrderBy -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnManageBottomSheet -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnApply -> { partyUserViewModel.onAction(action)}
                is PartyUserAction.OnChangeModifyPositionSheet -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnMainPositionClick -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnSubPositionClick -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnChangeSelectedSubPosition -> { partyUserViewModel.onAction(action) }
                is PartyUserAction.OnChangeModifyDialog -> { partyUserViewModel.onAction(action) }
            }
        },
        onNavigationBack = { navController.popBackStack() }
    )

    if(partyUserState.isShowModifyDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.2f))
                .noRippleClickable { partyUserViewModel.dismissBackDialog() }
        ) {
            TwoButtonDialog(
                dialogTitle = "포지션 변경",
                description = "해당 포지션으로 변경하시나요?",
                cancelButtonText = "닫기",
                confirmButtonText = "변경하기",
                onCancel = { partyUserViewModel.dismissBackDialog() },
                onConfirm = { }
            )
        }
    }
}

@Composable
private fun PartyUserManageScreen(
    snackBarHostState: SnackbarHostState,
    partyUserState: PartyUserState,
    partyId: Int,
    onAction: (PartyUserAction) -> Unit,
    onNavigationBack: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyUserScaffoldArea(
                onNavigationBack = onNavigationBack
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
            PartyUserCountArea()
            HeightSpacer(18.dp)

            // 검색
            PartyUserSearchArea(
                inputText = partyUserState.inputText,
                placeHolder = "닉네임을 검색해 보세요.",
                onValueChange = { inputText ->
                    onAction(PartyUserAction.OnChangeInputText(inputText))
                }
            )

            // 필터
            HeightSpacer(heightDp = 16.dp)
            PartyUserFilterArea(
                partyUserState = partyUserState,
                isPartyTypeFilterClick = { onAction(PartyUserAction.OnChangePositionBottomSheet(true)) },
                onChangeOrderBy = { isDesc -> onAction(PartyUserAction.OnChangeOrderBy(isDesc)) },
                onShowPositionFilter = { isShow -> onAction(PartyUserAction.OnChangePositionBottomSheet(isShow)) },
                onPositionClick = { selectPosition -> onAction(PartyUserAction.OnChangeMainPosition(selectPosition)) },
                onReset = { onAction(PartyUserAction.OnChangeMainPosition("")) },
                onApply = { onAction(PartyUserAction.OnApply(partyId = partyId)) }
            )

            PartyUserListArea(
                partyUserState = partyUserState,
                onClick = { selectedMemberAuthority ->
                    onAction(PartyUserAction.OnManageBottomSheet(true, selectedMemberAuthority))
                }
            )
        }
    }



    if(partyUserState.manageBottomSheet){
        NoButtonAndGotoScreenBottomSheet(
            bottomSheetTitle = "파티원 관리",
            contentList = if(partyUserState.selectedMemberAuthority == PartyAuthorityType.MASTER.authority) partyMasterManageList else partyMemberManageList,
            onBottomSheetClose = { onAction(PartyUserAction.OnManageBottomSheet(false, "")) },
            onClick = { text ->
                when(text){
                    "포지션 변경" -> {
                        onAction(PartyUserAction.OnManageBottomSheet(false, ""))
                        onAction(PartyUserAction.OnChangeModifyPositionSheet(true))
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
            onApply = { mainPosition, selectedSubPosition->
                onAction(PartyUserAction.OnChangeMainPosition(mainPosition))
                onAction(PartyUserAction.OnChangeSelectedSubPosition(selectedSubPosition))
                onAction(PartyUserAction.OnChangeModifyDialog(true))
            },
            onClickMainPosition = {
                onAction(PartyUserAction.OnMainPositionClick(it))
            }
        )
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
                    createdAt = "2024-06-05T15:30:45.123Z",
                    updatedAt = "2024-06-05T15:30:45.123Z",
                    status = "Joseh",
                    authority = "master",
                    user = PartyUserInfo(
                        id = 4865,
                        nickname = "닉네임입니다.",
                        image = null
                    ),
                    position = PartyMemberPosition(
                        main = "개발자",
                        sub = "안드로이드"
                    )
                ),
                PartyMemberInfo(
                    createdAt = "2024-06-05T15:30:45.123Z",
                    updatedAt = "2024-06-05T15:30:45.123Z",
                    status = "Joseh",
                    authority = "member",
                    user = PartyUserInfo(
                        id = 4865,
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
        onNavigationBack = {}
    )
}