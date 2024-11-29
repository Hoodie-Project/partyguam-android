package com.party.presentation.screen.recruitment_create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.RecruitmentCreate
import com.party.domain.model.party.RecruitmentCreateRequest
import com.party.domain.usecase.party.CreateRecruitmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentCreateViewModel @Inject constructor(
    private val createRecruitmentUseCase: CreateRecruitmentUseCase,
): ViewModel(){

    private val _successSaveState = MutableSharedFlow<Unit>()
    val successSaveState = _successSaveState.asSharedFlow()

    private val _createRecruitmentState = MutableStateFlow<UIState<ServerApiResponse<RecruitmentCreate>>>(UIState.Idle)
    val createRecruitmentState: StateFlow<UIState<ServerApiResponse<RecruitmentCreate>>> = _createRecruitmentState

    fun createRecruitment(partyId: Int, recruitmentCreateRequest: RecruitmentCreateRequest){
        viewModelScope.launch(Dispatchers.IO) {
            _createRecruitmentState.emit(UIState.Loading)
            when(val result = createRecruitmentUseCase(partyId = partyId, recruitmentCreateRequest = recruitmentCreateRequest)){
                is ServerApiResponse.SuccessResponse -> {
                    _successSaveState.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse -> {
                    _createRecruitmentState.emit(UIState.Error(result))
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _createRecruitmentState.emit(UIState.Exception)
                }
            }
        }
    }
}