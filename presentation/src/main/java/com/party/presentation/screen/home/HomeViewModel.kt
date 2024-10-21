package com.party.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PartyListResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.domain.usecase.party.GetPartyListUseCase
import com.party.domain.usecase.party.GetPersonalRecruitmentListUseCase
import com.party.domain.usecase.party.GetRecruitmentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPersonalRecruitmentListUseCase: GetPersonalRecruitmentListUseCase,
    private val getRecruitmentListUseCase: GetRecruitmentListUseCase,
    private val getPartyListUseCase: GetPartyListUseCase,
): ViewModel(){

    private val _getPersonalRecruitmentListState = MutableStateFlow<UIState<ServerApiResponse<PersonalRecruitmentListResponse>>>(UIState.Idle)
    val getPersonalRecruitmentListState: StateFlow<UIState<ServerApiResponse<PersonalRecruitmentListResponse>>> = _getPersonalRecruitmentListState

    private val _getRecruitmentListState = MutableStateFlow<UIState<ServerApiResponse<RecruitmentListResponse>>>(UIState.Idle)
    val getRecruitmentListState: StateFlow<UIState<ServerApiResponse<RecruitmentListResponse>>> = _getRecruitmentListState

    private val _getPartyListState = MutableStateFlow<UIState<ServerApiResponse<PartyListResponse>>>(UIState.Idle)
    val getPartyListState: StateFlow<UIState<ServerApiResponse<PartyListResponse>>> = _getPartyListState

    fun getPersonalRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _getPersonalRecruitmentListState.value = UIState.Loading
            when(val result = getPersonalRecruitmentListUseCase(page = page, size = size, sort = sort, order = order)){
                is ServerApiResponse.SuccessResponse<PersonalRecruitmentListResponse> -> {
                    _getPersonalRecruitmentListState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<PersonalRecruitmentListResponse> -> {
                    _getPersonalRecruitmentListState.value = UIState.Idle
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _getPersonalRecruitmentListState.value = UIState.Idle
                }
            }
        }
    }

    fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _getRecruitmentListState.value = UIState.Loading
            when(val result = getRecruitmentListUseCase(page = page, size = size, sort = sort, order = order)){
                is ServerApiResponse.SuccessResponse<RecruitmentListResponse> -> {
                    _getRecruitmentListState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<RecruitmentListResponse> -> {
                    _getRecruitmentListState.value = UIState.Idle
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _getRecruitmentListState.value = UIState.Idle
                }
            }
        }
    }

    fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _getRecruitmentListState.value = UIState.Loading
            when(val result = getPartyListUseCase(page = page, size = size, sort = sort, order = order)){
                is ServerApiResponse.SuccessResponse<PartyListResponse> -> {
                    _getPartyListState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<PartyListResponse> -> {
                    _getPartyListState.value = UIState.Idle
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _getPartyListState.value = UIState.Idle
                }
            }
        }
    }
}