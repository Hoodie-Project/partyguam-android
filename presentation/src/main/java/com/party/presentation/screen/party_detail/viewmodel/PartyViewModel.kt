package com.party.presentation.screen.party_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PartyDetail
import com.party.domain.usecase.party.GetPartyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyViewModel @Inject constructor(
    private val getPartyDetailUseCase: GetPartyDetailUseCase,
): ViewModel(){

    private val _getPartyDetailState = MutableStateFlow<UIState<ServerApiResponse<PartyDetail>>>(UIState.Idle)
    val getPartyDetailState: StateFlow<UIState<ServerApiResponse<PartyDetail>>> = _getPartyDetailState

    fun getPartyDetail(partyId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _getPartyDetailState.emit(UIState.Loading)
            when(val result = getPartyDetailUseCase(partyId = partyId)){
                is ServerApiResponse.SuccessResponse -> {
                    _getPartyDetailState.emit(UIState.Success(result))
                }
                is ServerApiResponse.ErrorResponse -> {
                    _getPartyDetailState.emit(UIState.Error(result))
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _getPartyDetailState.emit(UIState.Exception)
                }
            }
        }
    }
}