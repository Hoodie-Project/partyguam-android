package com.party.presentation.screen.party_apply.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.PartyApply
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.usecase.party.PartyRecruitmentApplyUseCase
import com.party.presentation.screen.party_apply.PartyApplyAction
import com.party.presentation.screen.party_apply.PartyApplyState
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
class PartyApplyViewModel @Inject constructor(
    private val partyRecruitmentApplyUseCase: PartyRecruitmentApplyUseCase
): ViewModel(){

    private val _successState = MutableSharedFlow<Unit>()
    val successState = _successState.asSharedFlow()

    private val _backState = MutableSharedFlow<Unit>()
    val backState = _backState.asSharedFlow()

    private val _applyState = MutableStateFlow(PartyApplyState())
    val applyState = _applyState.asStateFlow()

    private fun applyPartyRecruitment(
        partyId: Int,
        partyRecruitmentId: Int,
        partyApplyRequest: PartyApplyRequest,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _applyState.update { it.copy(isLoading = true) }
            when(val result = partyRecruitmentApplyUseCase(partyId = partyId, partyRecruitmentId = partyRecruitmentId, partyApplyRequest = partyApplyRequest)){
                is ServerApiResponse.SuccessResponse -> {
                    _applyState.update {
                        it.copy(
                            isLoading = false,
                            partyApply = result.data ?: PartyApply(
                                message = "",
                                status = "",
                                createdAt = "2024-06-05T15:30:45.123Z",
                                id = 0,
                            ),
                        )
                    }
                    _successState.emit(Unit)
                }

                is ServerApiResponse.ErrorResponse -> _applyState.update { it.copy(isLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _applyState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun dismissBackDialog(){
        _applyState.update { it.copy(isShowBackDialog = false) }
    }

    private fun showBackDialog(){
        _applyState.update { it.copy(isShowBackDialog = true) }
    }

    fun navigationBack(){
        viewModelScope.launch {
            dismissBackDialog()
            _backState.emit(Unit)
        }
    }

    fun onAction(action: PartyApplyAction){
        when(action){
            is PartyApplyAction.OnShowDialog -> showBackDialog()
            is PartyApplyAction.OnDismissBackDialog -> dismissBackDialog()
            is PartyApplyAction.OnChangeInputText -> { _applyState.update { it.copy(inputApplyReason = action.inputText) } }
            is PartyApplyAction.OnAllDeleteInputText -> { _applyState.update { it.copy(inputApplyReason = "") } }
            is PartyApplyAction.OnApply -> {
                applyPartyRecruitment(
                    partyId = action.partyId,
                    partyRecruitmentId = action.partyRecruitmentId,
                    partyApplyRequest = PartyApplyRequest(message = applyState.value.inputApplyReason)
                )
            }
        }
    }
}