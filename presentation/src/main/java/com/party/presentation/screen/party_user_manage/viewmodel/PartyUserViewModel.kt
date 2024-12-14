package com.party.presentation.screen.party_user_manage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.party.GetPartyMemberInfoUseCase
import com.party.presentation.screen.party_user_manage.PartyUserAction
import com.party.presentation.screen.party_user_manage.PartyUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyUserViewModel @Inject constructor(
    private val getPartyMemberInfoUseCase: GetPartyMemberInfoUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(PartyUserState())
    val state = _state.asStateFlow()

    fun getPartyMembers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String,
    ){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val result = getPartyMemberInfoUseCase(
                partyId = partyId,
                page = page,
                limit = limit,
                sort = sort,
                order = order
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

    fun onAction(action: PartyUserAction){
        when(action){
            is PartyUserAction.OnChangeInputText -> _state.update { it.copy(inputText = action.inputText) }
            is PartyUserAction.OnChangePositionBottomSheet -> _state.update { it.copy(isOpenPositionBottomSheet = action.isOpenPositionBottomSheet) }
            is PartyUserAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.selectedMainPosition) }
            is PartyUserAction.OnChangeOrderBy -> _state.update { it.copy(isDesc = action.isDesc) }
            is PartyUserAction.OnManageBottomSheet -> _state.update { it.copy(manageBottomSheet = action.isOpenManageBottomSheet) }
        }
    }
}