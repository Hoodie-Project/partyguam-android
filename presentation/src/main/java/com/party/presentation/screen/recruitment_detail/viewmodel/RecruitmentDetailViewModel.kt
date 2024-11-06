package com.party.presentation.screen.recruitment_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.usecase.party.GetRecruitmentDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentDetailViewModel @Inject constructor(
    private val getRecruitmentDetailUseCase: GetRecruitmentDetailUseCase,
): ViewModel(){

    private val _getRecruitmentDetailState = MutableStateFlow<UIState<ServerApiResponse<RecruitmentDetail>>>(UIState.Idle)
    val getRecruitmentDetailState: StateFlow<UIState<ServerApiResponse<RecruitmentDetail>>> = _getRecruitmentDetailState

    fun getRecruitmentDetail(partyRecruitmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _getRecruitmentDetailState.value = UIState.Loading
            when(val result = getRecruitmentDetailUseCase(partyRecruitmentId = partyRecruitmentId)){
                is ServerApiResponse.SuccessResponse<RecruitmentDetail> -> {
                    _getRecruitmentDetailState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<RecruitmentDetail> -> {
                    _getRecruitmentDetailState.value = UIState.Error()
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _getRecruitmentDetailState.value = UIState.Exception
                }
            }
        }
    }
}