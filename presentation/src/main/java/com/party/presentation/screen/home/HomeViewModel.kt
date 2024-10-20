package com.party.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.usecase.party.GetPersonalRecruitmentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPersonalRecruitmentListUseCase: GetPersonalRecruitmentListUseCase
): ViewModel(){

    private val _getPersonalRecruitmentListState = MutableStateFlow<UIState<ServerApiResponse<PersonalRecruitmentListResponse>>>(UIState.Idle)
    val getPersonalRecruitmentListState: StateFlow<UIState<ServerApiResponse<PersonalRecruitmentListResponse>>> = _getPersonalRecruitmentListState

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
}