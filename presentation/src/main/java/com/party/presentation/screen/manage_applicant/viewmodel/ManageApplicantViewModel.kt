package com.party.presentation.screen.manage_applicant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import com.party.domain.usecase.party.GetRecruitmentApplicantUseCase
import com.party.presentation.screen.manage_applicant.ManageApplicantAction
import com.party.presentation.screen.manage_applicant.ManageApplicantState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageApplicantViewModel @Inject constructor(
    private val getPartyRecruitmentUseCase: GetPartyRecruitmentUseCase,
    private val getRecruitmentApplicantUseCase: GetRecruitmentApplicantUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(ManageApplicantState())
    val state = _state.asStateFlow()

    fun getRecruitmentApplicant(partyId: Int, partyRecruitmentId: Int, page: Int, limit: Int, sort: String, order: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingRecruitmentApplicant = true) }
            when (val result = getRecruitmentApplicantUseCase(partyId = partyId, partyRecruitmentId = partyRecruitmentId, page = page, limit = limit, sort = sort, order = order)) {
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoadingRecruitmentApplicant = false,
                        recruitmentApplicantList = result.data?.partyApplicationUser ?: emptyList()
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> { _state.update { it.copy(isLoadingRecruitmentApplicant = false) } }
                is ServerApiResponse.ExceptionResponse -> { _state.update { it.copy(isLoadingRecruitmentApplicant = false) } }
            }
        }
    }

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingRecruitment = true) }
            when (val result = getPartyRecruitmentUseCase(partyId = partyId, sort = sort, order = order, main = main)) {
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoadingRecruitment = false,
                        partyRecruitment = result.data ?: emptyList()
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> { _state.update { it.copy(isLoadingRecruitment = false) } }
                is ServerApiResponse.ExceptionResponse -> { _state.update { it.copy(isLoadingRecruitment = false) } }
            }
        }
    }

    fun onAction(action: ManageApplicantAction){
        when(action){
            is ManageApplicantAction.OnShowHelpCard -> _state.update { it.copy(isShowHelpIcon = action.isShowHelpCard) }
            is ManageApplicantAction.OnChangeOrderBy -> {
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
            is ManageApplicantAction.OnShowRecruitment -> _state.update { it.copy(isShowRecruitmentList = action.isShow) }
            is ManageApplicantAction.OnSelectRecruitmentTab -> _state.update { it.copy(selectedRecruitmentStatus = action.selectedRecruitmentTabText) }
            is ManageApplicantAction.OnChangeApplicantOrderBy -> {
                _state.update { it.copy(isShowApplicantCreatedDt = action.isDesc) }
            }
            is ManageApplicantAction.OnSelectRecruitmentId -> _state.update { it.copy(selectedRecruitmentId = action.partyRecruitmentId) }
        }
    }
}