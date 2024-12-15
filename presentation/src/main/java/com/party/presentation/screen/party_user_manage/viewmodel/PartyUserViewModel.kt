package com.party.presentation.screen.party_user_manage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.party.GetPartyMemberInfoUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.presentation.screen.party_user_manage.PartyUserAction
import com.party.presentation.screen.party_user_manage.PartyUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyUserViewModel @Inject constructor(
    private val getPartyMemberInfoUseCase: GetPartyMemberInfoUseCase,
    private val getPositionsUseCase: GetPositionsUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(PartyUserState())
    val state = _state.asStateFlow()

    fun getPartyMembers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String,
        main: String?
    ){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val result = getPartyMemberInfoUseCase(
                partyId = partyId,
                page = page,
                limit = limit,
                sort = sort,
                order = order,
                main = main
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

    fun onAction(action: PartyUserAction){
        when(action){
            is PartyUserAction.OnChangeInputText -> _state.update { it.copy(inputText = action.inputText) }
            is PartyUserAction.OnChangePositionBottomSheet -> _state.update { it.copy(isOpenPositionBottomSheet = action.isOpenPositionBottomSheet) }
            is PartyUserAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.selectedMainPosition) }
            is PartyUserAction.OnChangeOrderBy -> _state.update { it.copy(isDesc = action.isDesc) }
            is PartyUserAction.OnManageBottomSheet -> {
                _state.update { it.copy(
                    manageBottomSheet = action.isOpenManageBottomSheet,
                    selectedMemberAuthority = action.selectedMemberAuthority
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
                    main = if (_state.value.selectedMainPosition == "전체") null else _state.value.selectedMainPosition
                )
            }

            is PartyUserAction.OnMainPositionClick -> {
                _state.update { it.copy(selectedMainPosition = action.mainPosition) }
                getSubPositionList(action.mainPosition)
            }
            is PartyUserAction.OnChangeModifyPositionSheet -> _state.update { it.copy(isMainPositionBottomSheetShow = action.isOpenMainPositionSheet) }
            is PartyUserAction.OnSubPositionClick -> {

            }
            is PartyUserAction.OnChangeSelectedSubPosition -> _state.update { it.copy(selectedSubPosition = action.positionList) }
            is PartyUserAction.OnChangeModifyDialog -> _state.update { it.copy(isShowModifyDialog = action.isShowModifyDialog) }
        }
    }
}