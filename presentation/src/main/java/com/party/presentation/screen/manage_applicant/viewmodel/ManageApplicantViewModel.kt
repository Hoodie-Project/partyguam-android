package com.party.presentation.screen.manage_applicant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.ac
import com.party.common.ServerApiResponse
import com.party.domain.usecase.party.AcceptApplicantUseCase
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import com.party.domain.usecase.party.GetRecruitmentApplicantUseCase
import com.party.domain.usecase.party.RejectApplicantUseCase
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.SortType
import com.party.presentation.screen.manage_applicant.ManageApplicantAction
import com.party.presentation.screen.manage_applicant.ManageApplicantState
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
class ManageApplicantViewModel @Inject constructor(
    private val getPartyRecruitmentUseCase: GetPartyRecruitmentUseCase,
    private val getRecruitmentApplicantUseCase: GetRecruitmentApplicantUseCase,
    private val acceptApplicantUseCase: AcceptApplicantUseCase,
    private val rejectApplicantUseCase: RejectApplicantUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(ManageApplicantState())
    val state = _state.asStateFlow()

    private val _acceptSuccess = MutableSharedFlow<Unit>()
    val acceptSuccess = _acceptSuccess.asSharedFlow()

    private val _rejectSuccess = MutableSharedFlow<Unit>()
    val rejectSuccess = _rejectSuccess.asSharedFlow()

    private val _acceptAndRejectFail = MutableSharedFlow<String>()
    val acceptAndRejectFail = _acceptAndRejectFail.asSharedFlow()

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

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingRecruitment = true) }
            when (val result = getPartyRecruitmentUseCase(partyId = partyId, sort = sort, order = order, main = main, status = status)) {
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

    fun rejectApplicant(
        partyId: Int,
        partyApplicationId: Int,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = rejectApplicantUseCase(partyId = partyId, partyApplicationId = partyApplicationId)){
                is ServerApiResponse.SuccessResponse -> {
                    getRecruitmentApplicant(partyId = partyId, partyRecruitmentId = _state.value.selectedRecruitmentId, page = 1, limit = 50, sort = SortType.CREATED_AT.type, order = OrderDescType.DESC.type)
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun acceptApplicant(
        partyId: Int,
        partyApplicationId: Int,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = acceptApplicantUseCase(partyId = partyId, partyApplicationId = partyApplicationId)){
                is ServerApiResponse.SuccessResponse -> {
                    getRecruitmentApplicant(partyId = partyId, partyRecruitmentId = _state.value.selectedRecruitmentId, page = 1, limit = 50, sort = SortType.CREATED_AT.type, order = OrderDescType.DESC.type)
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: ManageApplicantAction){
        when(action){
            is ManageApplicantAction.OnShowHelpCard -> _state.update { it.copy(isShowHelpIcon = action.isShowHelpCard) }
            is ManageApplicantAction.OnChangeProgress ->{
                _state.update { it.copy(isProgress = action.isProgress) }

                getPartyRecruitment(
                    partyId = action.partyId,
                    sort = SortType.CREATED_AT.type,
                    order = OrderDescType.DESC.type,
                    main = null,
                    status = if(_state.value.isProgress) "active" else "completed"
                )
            }
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
            is ManageApplicantAction.OnSelectRecruitmentId -> _state.update { it.copy(selectedRecruitmentId = action.partyRecruitmentId, selectedRecruitmentMain = action.main, selectedRecruitmentSub = action.sub) }
            is ManageApplicantAction.OnShowAcceptDialog -> _state.update { it.copy(isShowAcceptDialog = action.isShow) }
            is ManageApplicantAction.OnShowRejectDialog -> _state.update { it.copy(isShowRejectDialog = action.isShow) }
            is ManageApplicantAction.OnRejectApplicant -> {
                rejectApplicant(partyId = action.partyId, partyApplicationId = action.partyApplicationId)
            }
            is ManageApplicantAction.OnAcceptApplicant -> {
                acceptApplicant(partyId = action.partyId, partyApplicationId = action.partyApplicationId)
            }
        }
    }
}