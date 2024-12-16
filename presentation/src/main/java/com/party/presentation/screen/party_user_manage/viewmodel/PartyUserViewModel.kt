package com.party.presentation.screen.party_user_manage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.ModifyPartyUserPositionRequest
import com.party.domain.usecase.party.GetPartyMemberInfoUseCase
import com.party.domain.usecase.party.ModifyPartyUserPositionUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.presentation.screen.party_user_manage.PartyUserAction
import com.party.presentation.screen.party_user_manage.PartyUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyUserViewModel @Inject constructor(
    private val getPartyMemberInfoUseCase: GetPartyMemberInfoUseCase,
    private val getPositionsUseCase: GetPositionsUseCase,
    private val modifyPartyUserPositionUseCase: ModifyPartyUserPositionUseCase
): ViewModel(){

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _state = MutableStateFlow(PartyUserState())
    val state = _state.asStateFlow()

    fun getPartyMembers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String,
        main: String?,
        nickname: String?
    ){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val result = getPartyMemberInfoUseCase(
                partyId = partyId,
                page = page,
                limit = limit,
                sort = sort,
                order = order,
                main = main,
                nickname = nickname
            )){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoading = false,
                        partyUserList = result.data?.partyUser ?: emptyList()
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun dismissBackDialog(){
        _state.update { it.copy(isShowModifyDialog = false) }
    }

    // 서브 포지션 조회
    private fun getSubPositionList(
        main: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getPositionsUseCase(main = main)) {
                is ServerApiResponse.SuccessResponse -> { _state.update { it.copy(getSubPositionList = result.data ?: emptyList()) } }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun modifyPartyUserPosition(
        partyId: Int,
        partyUserId: Int,
        modifyPartyUserPositionRequest: ModifyPartyUserPositionRequest,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = modifyPartyUserPositionUseCase(partyId, partyUserId, modifyPartyUserPositionRequest)){
                is ServerApiResponse.SuccessResponse -> {}
                is ServerApiResponse.ErrorResponse -> {
                    _state.update { it.copy(isShowModifyDialog = false) }
                    when(result.statusCode){
                        404 -> _errorFlow.emit("파티유저를 찾을 수 없습니다.")
                        403 -> _errorFlow.emit("파티원 수정권한이 없습니다.")
                        else -> _errorFlow.emit("파티원 수정에 실패했습니다.")
                    }
                }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isShowModifyDialog = false) }
            }
        }
    }

    fun onAction(action: PartyUserAction){
        when(action){
            is PartyUserAction.OnChangeInputText -> _state.update { it.copy(inputText = action.inputText) }
            is PartyUserAction.OnChangePositionBottomSheet -> _state.update { it.copy(isOpenPositionBottomSheet = action.isOpenPositionBottomSheet) }
            is PartyUserAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.selectedMainPosition) }
            is PartyUserAction.OnChangeOrderBy -> _state.update { it.copy(isDesc = action.isDesc) }
            is PartyUserAction.OnManageBottomSheet -> { _state.update { it.copy(manageBottomSheet = action.isOpenManageBottomSheet) } }
            is PartyUserAction.OnSelectedUser ->{
                _state.update { it.copy(
                    selectedMemberAuthority = action.selectedMemberAuthority,
                    selectedMemberId = action.selectedMemberId
                ) }
            }
            is PartyUserAction.OnApply -> {
                _state.update { it.copy(
                    isOpenPositionBottomSheet = false,
                ) }

                getPartyMembers(
                    partyId = action.partyId,
                    page = 1,
                    limit = 50,
                    sort = "createdAt",
                    order = if(_state.value.isDesc) "DESC" else "ASC",
                    main = if (_state.value.selectedMainPosition == "전체") null else _state.value.selectedMainPosition,
                    nickname = if(_state.value.inputText == "") null else _state.value.inputText
                )
            }

            is PartyUserAction.OnMainPositionClick -> { getSubPositionList(action.mainPosition) }
            is PartyUserAction.OnChangeModifyPositionSheet -> _state.update { it.copy(isMainPositionBottomSheetShow = action.isOpenMainPositionSheet) }
            is PartyUserAction.OnChangeSelectedSubPosition -> _state.update { it.copy(selectedSubPosition = action.positionList) }
            is PartyUserAction.OnChangeModifyDialog -> _state.update { it.copy(isShowModifyDialog = action.isShowModifyDialog) }
            is PartyUserAction.OnModifyUserPosition -> {
                modifyPartyUserPosition(
                    partyId = action.partyId,
                    partyUserId = _state.value.selectedMemberId,
                    modifyPartyUserPositionRequest = ModifyPartyUserPositionRequest(
                        positionId = _state.value.selectedSubPosition.id
                    )
                )
            }
        }
    }
}