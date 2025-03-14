package com.party.presentation.screen.party_edit_recruitment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.party.CompletedPartyRecruitmentUseCase
import com.party.domain.usecase.party.DeleteRecruitmentUseCase
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import com.party.presentation.enum.OrderDescType
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

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyRecruitment = true) }
            when (val result = getPartyRecruitmentUseCase(
                partyId = partyId,
                sort = sort,
                order = order,
                main = main,
                status = if(_state.value.selectedTabText == "전체") null else if(_state.value.selectedTabText == "모집중") "active" else "completed"
            )) {
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
            is PartyRecruitmentEditAction.OnSelectedTabText -> {
                _state.update { it.copy(selectedTabText = action.selectedTabText) }
                getPartyRecruitment(
                    partyId = action.partyId,
                    sort = "createdAt",
                    order = if(_state.value.isDesc) OrderDescType.DESC.type else OrderDescType.ASC.type,
                    main = null
                )
            }
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
            is PartyRecruitmentEditAction.OnDeleteRecruitment -> {
                deleteRecruitment(partyId = action.partyId, partyRecruitmentId = action.partyRecruitmentId)
            }

            is PartyRecruitmentEditAction.OnShowPartyRecruitmentCompletedDialog -> {
                _state.update { it.copy(isShowPartyRecruitmentCompletedDialog = action.isShowPartyRecruitmentDialog) }
            }
            is PartyRecruitmentEditAction.OnPartyRecruitmentCompleted -> {
                completedPartyRecruitment(partyId = action.partyId, partyRecruitmentId = action.partyRecruitmentId)
            }
            is PartyRecruitmentEditAction.OnShowPartyRecruitmentDeleteDialog -> {
                _state.update { it.copy(isShowPartyRecruitmentDeleteDialog = action.isShowPartyRecruitmentDeleteDialog) }
            }
            is PartyRecruitmentEditAction.OnShowBottomSheet -> {
                _state.update {
                    it.copy(
                        isShowBottomSheet = action.isShow,
                        selectedRecruitmentId = action.recruitmentId,
                        selectedStatus = action.status,
                        description = action.description,
                        recruitingCount = action.recruitingCount,
                        main = action.main,
                        sub = action.sub,
                    )
                }
            }
        }
    }
}