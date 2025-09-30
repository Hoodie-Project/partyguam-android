package com.party.presentation.screen.state.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.usecase.party.ApprovalPartyUseCase
import com.party.domain.usecase.party.CancelRecruitmentUseCase
import com.party.domain.usecase.party.RejectionPartyUseCase
import com.party.domain.usecase.user.party.GetMyPartyUseCase
import com.party.domain.usecase.user.recruitment.GetMyRecruitmentUseCase
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.SortType
import com.party.presentation.screen.state.MyPartyAction
import com.party.presentation.screen.state.MyPartyState
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
class StateViewModel @Inject constructor(
    private val getMyPartyUseCase: GetMyPartyUseCase,
    private val getMyRecruitmentUseCase: GetMyRecruitmentUseCase,
    private val cancelRecruitmentUseCase: CancelRecruitmentUseCase,
    private val approvalPartyUseCase: ApprovalPartyUseCase,
    private val rejectionPartyUseCase: RejectionPartyUseCase,
): ViewModel(){

    private val _myPartyState = MutableStateFlow(MyPartyState())
    val myPartyState = _myPartyState.asStateFlow()

    private val _successCancel = MutableSharedFlow<Unit>()
    val successCancel = _successCancel.asSharedFlow()

    private fun cancelRecruitment(partyId: Int, partyApplicationId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = cancelRecruitmentUseCase(partyId = partyId, partyApplicationId = partyApplicationId)){
                is ServerApiResponse.SuccessResponse -> _successCancel.emit(Unit)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun getMyParty(page: Int, limit: Int, sort: String, order: String, status: String?){
        viewModelScope.launch(Dispatchers.IO) {
            _myPartyState.update { it.copy(isMyPartyLoading = true) }

            when(val result = getMyPartyUseCase(page, limit, sort, order, status)){
                is ServerApiResponse.SuccessResponse -> {
                    _myPartyState.update {
                        it.copy(
                            isMyPartyLoading = false,
                            myPartyList = result.data ?: MyParty(total = 0, partyUsers = emptyList())
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> _myPartyState.update { it.copy(isMyPartyLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _myPartyState.update { it.copy(isMyPartyLoading = false) }
            }
        }
    }

    fun getMyRecruitment(page: Int, limit: Int, sort: String, order: String){
        viewModelScope.launch(Dispatchers.IO) {
            _myPartyState.update { it.copy(isMyRecruitmentLoading = true) }

            when(val result = getMyRecruitmentUseCase(page, limit, sort, order)){
                is ServerApiResponse.SuccessResponse -> {
                    _myPartyState.update {
                        it.copy(
                            isMyRecruitmentLoading = false,
                            myRecruitmentList = result.data ?: MyRecruitment(total = 0, partyApplications = emptyList())
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> _myPartyState.update { it.copy(isMyRecruitmentLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _myPartyState.update { it.copy(isMyRecruitmentLoading = false) }
            }
        }
    }

    private fun approvalParty(partyId: Int, partyApplicationId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = approvalPartyUseCase(partyId = partyId, partyApplicationId = partyApplicationId)){
                is ServerApiResponse.SuccessResponse -> getMyRecruitment(1, 50, SortType.CREATED_AT.type, OrderDescType.DESC.type)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun rejectionParty(partyId: Int, partyApplicationId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = rejectionPartyUseCase(partyId = partyId, partyApplicationId = partyApplicationId)){
                is ServerApiResponse.SuccessResponse -> getMyRecruitment(1, 50, SortType.CREATED_AT.type, OrderDescType.DESC.type)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: MyPartyAction){
        when(action){
            is MyPartyAction.OnSelectTab -> _myPartyState.update { it.copy(selectedTabText = action.selectedTabText) }
            is MyPartyAction.OnOrderByChange -> {
                _myPartyState.update { currentState ->
                    val sortedList = if (action.orderByDesc) {
                        currentState.myPartyList.partyUsers.sortedByDescending { it.createdAt }
                    } else {
                        currentState.myPartyList.partyUsers.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        orderByDesc = action.orderByDesc,
                        myPartyList = currentState.myPartyList.copy(partyUsers = sortedList)
                    )
                }
            }
            is MyPartyAction.OnSelectRecruitmentTab -> { _myPartyState.update { it.copy(selectedRecruitmentStatus = action.selectedRecruitmentTabText) } }
            is MyPartyAction.OnRecruitmentOrderByChange -> {
                _myPartyState.update { currentState ->
                    val sortedList = if (action.orderByRecruitmentDateDesc) {
                        currentState.myRecruitmentList.partyApplications.sortedByDescending { it.createdAt }
                    } else {
                        currentState.myRecruitmentList.partyApplications.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        orderByRecruitmentDateDesc = action.orderByRecruitmentDateDesc,
                        myRecruitmentList = currentState.myRecruitmentList.copy(partyApplications = sortedList)
                    )
                }
            }
            is MyPartyAction.OnShowHelpCard -> _myPartyState.update { it.copy(isShowHelpCard = action.isShowHelpCard) }
            is MyPartyAction.OnSelectStatus -> _myPartyState.update { currentState -> currentState.copy(selectedStatus = action.selectedStatus) }
            is MyPartyAction.OnCancelRecruitment -> cancelRecruitment(partyId = action.partyId, partyApplicationId = action.partyApplicationId)
            is MyPartyAction.OnApprovalParty -> approvalParty(partyId = action.partyId, partyApplicationId = action.partyApplicationId)
            is MyPartyAction.OnRejectionParty -> rejectionParty(partyId = action.partyId, partyApplicationId = action.partyApplicationId)
        }
    }
}

object StateEvent {
    private val _scrollToUp = MutableSharedFlow<Unit>(replay = 1)
    val scrollToUp = _scrollToUp.asSharedFlow()

    fun scrollToUp(){
        _scrollToUp.tryEmit(Unit)
    }
}