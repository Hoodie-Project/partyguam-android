package com.party.presentation.screen.party_apply.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PartyApply
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.usecase.party.PartyRecruitmentApplyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyApplyViewModel @Inject constructor(
    private val partyRecruitmentApplyUseCase: PartyRecruitmentApplyUseCase
): ViewModel(){

    private val _applySuccess = MutableSharedFlow<Unit>()
    val applySuccess = _applySuccess.asSharedFlow()

    private val _partyRecruitmentApplyState = MutableStateFlow<UIState<ServerApiResponse<PartyApply>>>(UIState.Idle)
    val partyRecruitmentApplyState: StateFlow<UIState<ServerApiResponse<PartyApply>>> = _partyRecruitmentApplyState

    fun applyPartyRecruitment(
        partyId: Int,
        partyRecruitmentId: Int,
        partyApplyRequest: PartyApplyRequest,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _partyRecruitmentApplyState.emit(UIState.Loading)
            when(val result = partyRecruitmentApplyUseCase(
                partyId = partyId,
                partyRecruitmentId = partyRecruitmentId,
                partyApplyRequest = partyApplyRequest
            )){
                is ServerApiResponse.SuccessResponse -> {
                    _applySuccess.emit(Unit)
                }

                is ServerApiResponse.ErrorResponse -> {
                    _partyRecruitmentApplyState.emit(UIState.Error(result))
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _partyRecruitmentApplyState.emit(UIState.Exception)
                }
            }
        }
    }
}