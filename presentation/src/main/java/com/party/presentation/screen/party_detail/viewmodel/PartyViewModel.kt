package com.party.presentation.screen.party_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.usecase.party.GetPartyDetailUseCase
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyViewModel @Inject constructor(
    private val getPartyDetailUseCase: GetPartyDetailUseCase,
    private val getPartyRecruitmentUseCase: GetPartyRecruitmentUseCase,
) : ViewModel() {

    private val _getPartyDetailState = MutableStateFlow<UIState<ServerApiResponse<PartyDetail>>>(UIState.Idle)
    val getPartyDetailState: StateFlow<UIState<ServerApiResponse<PartyDetail>>> = _getPartyDetailState

    private val _getPartyRecruitmentState = MutableStateFlow<UIState<ServerApiResponse<List<PartyRecruitment>>>>(UIState.Idle)
    val getPartyRecruitmentState: StateFlow<UIState<ServerApiResponse<List<PartyRecruitment>>>> = _getPartyRecruitmentState

    fun getPartyDetail(partyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _getPartyDetailState.emit(UIState.Loading)
            when (val result = getPartyDetailUseCase(partyId = partyId)) {
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

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _getPartyRecruitmentState.emit(UIState.Loading)
            when (val result =
                getPartyRecruitmentUseCase(partyId = partyId, sort = sort, order = order, main = main)) {
                is ServerApiResponse.SuccessResponse -> {
                    _getPartyRecruitmentState.emit(UIState.Success(result))
                }

                is ServerApiResponse.ErrorResponse -> {
                    _getPartyRecruitmentState.emit(UIState.Error(result))
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _getPartyRecruitmentState.emit(UIState.Exception)

                }
            }
        }
    }
}