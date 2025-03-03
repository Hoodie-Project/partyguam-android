package com.party.presentation.screen.party_edit_recruitment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.party.CompletedPartyRecruitmentUseCase
import com.party.domain.usecase.party.DeleteRecruitmentUseCase
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditAction
import com.party.presentation.screen.party_edit_recruitment.PartyRecruitmentEditState
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
class PartyRecruitmentEditViewModel @Inject constructor(
    private val getPartyRecruitmentUseCase: GetPartyRecruitmentUseCase,
    private val deleteRecruitmentUseCase: DeleteRecruitmentUseCase,
    private val completedPartyRecruitmentUseCase: CompletedPartyRecruitmentUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(PartyRecruitmentEditState())
    val state = _state.asStateFlow()

    private val _completedSuccess = MutableSharedFlow<Unit>()
    val completedSuccess = _completedSuccess.asSharedFlow()

    // 모집공고 삭제 flow
    private val _deleteRecruitment = MutableSharedFlow<Unit>()
    val deleteRecruitment = _deleteRecruitment

    // 모집공고 마감처리
    private fun completedPartyRecruitment(partyId: Int, partyRecruitmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = completedPartyRecruitmentUseCase(partyId = partyId, partyRecruitmentId = partyRecruitmentId)){
                is ServerApiResponse.SuccessResponse -> _completedSuccess.emit(Unit)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyRecruitment = true) }
            when (val result = getPartyRecruitmentUseCase(partyId = partyId, sort = sort, order = order, main = main, status = status)) {
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

    fun deleteRecruitment(partyId: Int, partyRecruitmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = deleteRecruitmentUseCase(partyId = partyId, partyRecruitmentId = partyRecruitmentId)){
                is ServerApiResponse.SuccessResponse -> { _deleteRecruitment.emit(Unit) }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
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
            is PartyRecruitmentEditAction.OnExpanded -> {
                _state.update {
                    it.copy(
                        partyRecruitment = it.partyRecruitment.mapIndexed { index, item ->
                            if (index == action.index) {
                                item.copy(isOptionsRevealed = true)
                            } else {
                                item.copy(isOptionsRevealed = false)
                            }
                        }
                    )
                }
            }
            is PartyRecruitmentEditAction.OnCollapsed -> {
                _state.update {
                    it.copy(
                        partyRecruitment = it.partyRecruitment.mapIndexed { index, item ->
                            if (index == action.index) {
                                item.copy(isOptionsRevealed = false)
                            } else {
                                item.copy(isOptionsRevealed = false)
                            }
                        }
                    )
                }
            }
            is PartyRecruitmentEditAction.OnShowRecruitmentDeleteDialog -> _state.update { it.copy(isShowRecruitmentDeleteDialog = action.isShowRecruitmentDeleteDialog) }
            is PartyRecruitmentEditAction.OnDeleteRecruitment -> {
                deleteRecruitment(partyId = action.partyId, partyRecruitmentId = action.partyRecruitmentId)
            }

            is PartyRecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog -> {
                _state.update { it.copy(isShowPartyRecruitmentCompletedDialog = action.isShowPartyRecruitmentDialog) }
            }
            is PartyRecruitmentEditAction.OnPartyRecruitmentCompleted -> {
                completedPartyRecruitment(partyId = action.partyId, partyRecruitmentId = action.partyRecruitmentId)
            }
        }
    }
}