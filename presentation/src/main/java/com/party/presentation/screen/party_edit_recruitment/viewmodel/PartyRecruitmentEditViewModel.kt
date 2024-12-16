package com.party.presentation.screen.party_edit_recruitment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditAction
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyRecruitmentEditViewModel @Inject constructor(
    private val getPartyRecruitmentUseCase: GetPartyRecruitmentUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(PartyRecruitmentEditState())
    val state = _state.asStateFlow()

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyRecruitment = true) }
            when (val result = getPartyRecruitmentUseCase(partyId = partyId, sort = sort, order = order, main = main)) {
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoadingPartyRecruitment = false,
                        partyRecruitment = result.data ?: emptyList()
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> { _state.update { it.copy(isLoadingPartyRecruitment = false) } }
                is ServerApiResponse.ExceptionResponse -> { _state.update { it.copy(isLoadingPartyRecruitment = false) } }
            }
        }
    }

    fun onAction(action: PartyRecruitmentEditAction){
        when(action){
            is PartyRecruitmentEditAction.OnShowHelpCard -> _state.update { it.copy(isShowHelpCard = action.isShowHelpCard) }
            is PartyRecruitmentEditAction.OnChangeOrderBy -> {
                _state.update { currentState ->
                    val sortedList = if (action.isDesc) {
                        currentState.partyRecruitment.sortedByDescending { it.createdAt }
                    } else {
                        currentState.partyRecruitment.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        isDesc = action.isDesc,
                        partyRecruitment = sortedList
                    )
                }
            }
        }
    }
}