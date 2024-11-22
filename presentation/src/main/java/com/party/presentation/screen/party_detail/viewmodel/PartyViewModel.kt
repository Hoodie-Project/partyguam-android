package com.party.presentation.screen.party_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.user.PartyAuthority
import com.party.domain.usecase.party.GetPartyAuthorityUseCase
import com.party.domain.usecase.party.GetPartyDetailUseCase
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import com.party.domain.usecase.party.GetPartyUsersUseCase
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
    private val getPartyUsersUseCase: GetPartyUsersUseCase,
    private val getPartyAuthorityUseCase: GetPartyAuthorityUseCase,
) : ViewModel() {

    private val _getPartyDetailState = MutableStateFlow<UIState<ServerApiResponse<PartyDetail>>>(UIState.Idle)
    val getPartyDetailState: StateFlow<UIState<ServerApiResponse<PartyDetail>>> = _getPartyDetailState

    private val _getPartyUsersState = MutableStateFlow<UIState<ServerApiResponse<PartyUsers>>>(UIState.Idle)
    val getPartyUsersState: StateFlow<UIState<ServerApiResponse<PartyUsers>>> = _getPartyUsersState

    private val _getPartyRecruitmentState = MutableStateFlow<UIState<ServerApiResponse<List<PartyRecruitment>>>>(UIState.Idle)
    val getPartyRecruitmentState: StateFlow<UIState<ServerApiResponse<List<PartyRecruitment>>>> = _getPartyRecruitmentState

    private val _getPartyAuthorityState = MutableStateFlow<UIState<ServerApiResponse<PartyAuthority>>>(UIState.Idle)
    val getPartyAuthorityState: StateFlow<UIState<ServerApiResponse<PartyAuthority>>> = _getPartyAuthorityState

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

    fun getPartyUsers(partyId: Int, page: Int, limit: Int, sort: String, order: String){
        viewModelScope.launch(Dispatchers.IO) {
            _getPartyUsersState.emit(UIState.Loading)
            when(val result = getPartyUsersUseCase(partyId = partyId, page = page, limit = limit, sort = sort, order = order)){
                is ServerApiResponse.SuccessResponse -> {
                    _getPartyUsersState.emit(UIState.Success(result))
                }

                is ServerApiResponse.ErrorResponse -> {
                    _getPartyUsersState.emit(UIState.Error(result))
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _getPartyUsersState.emit(UIState.Exception)
                }

            }
        }
    }

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _getPartyRecruitmentState.emit(UIState.Loading)
            when (val result = getPartyRecruitmentUseCase(partyId = partyId, sort = sort, order = order, main = main)) {
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

    fun getPartyAuthority(partyId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _getPartyAuthorityState.emit(UIState.Loading)
            when(val result = getPartyAuthorityUseCase(partyId = partyId)){
                is ServerApiResponse.SuccessResponse -> {
                    _getPartyAuthorityState.emit(UIState.Success(result))
                }

                is ServerApiResponse.ErrorResponse -> {
                    _getPartyAuthorityState.emit(UIState.Error(result))
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _getPartyAuthorityState.emit(UIState.Exception)
                }
            }
        }
    }
}