package com.party.presentation.screen.profile_edit_time.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PersonalitySaveRequest2
import com.party.domain.usecase.user.detail.DeleteUserPersonalityUseCase
import com.party.domain.usecase.user.detail.GetPersonalityUseCase
import com.party.domain.usecase.user.detail.SavePersonalityUseCase
import com.party.presentation.enum.PersonalityType
import com.party.presentation.screen.profile_edit_time.ProfileEditTimeAction
import com.party.presentation.screen.profile_edit_time.ProfileEditTimeState
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
class ProfileEditTimeViewModel @Inject constructor(
    private val getPersonalityUseCase: GetPersonalityUseCase,
    private val savePersonalityUseCase: SavePersonalityUseCase,
    private val deleteUserPersonalityUseCase: DeleteUserPersonalityUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ProfileEditTimeState())
    val state = _state.asStateFlow()

    private val _modifySuccess = MutableSharedFlow<Unit>()
    val modifySuccess = _modifySuccess.asSharedFlow()

    private val _twoOverWarning = MutableSharedFlow<Unit>()
    val twoOverWarning = _twoOverWarning.asSharedFlow()

    fun getPersonalityList(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getPersonalityUseCase()){
                is ServerApiResponse.SuccessResponse -> { _state.update { it.copy(personalityList = result.data ?: emptyList()) } }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun emitTwo(){
        viewModelScope.launch(Dispatchers.Main) {
            _twoOverWarning.emit(Unit)
        }
    }

    private fun savePersonality(personalitySaveRequest: PersonalitySaveRequest){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = savePersonalityUseCase(personalitySaveRequest = personalitySaveRequest)){
                is ServerApiResponse.SuccessResponse -> {
                    _modifySuccess.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun deletePersonality(personalitySaveRequest: PersonalitySaveRequest, personalityQuestionId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = deleteUserPersonalityUseCase(personalityQuestionId = personalityQuestionId)){
                is ServerApiResponse.SuccessResponse -> {
                    savePersonality(personalitySaveRequest)
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: ProfileEditTimeAction){
        when(action){
            is ProfileEditTimeAction.OnReset -> { _state.update { it.copy(selectedList = emptyList()) } }
            is ProfileEditTimeAction.OnApply -> {
                val personalitySaveRequest = PersonalitySaveRequest(
                    personality = listOf(
                        PersonalitySaveRequest2(
                            personalityQuestionId = 1,
                            personalityOptionId = _state.value.selectedList.map { it.id }
                        ),
                    )
                )
                deletePersonality(personalitySaveRequest, PersonalityType.TIME.id)
            }
            is ProfileEditTimeAction.OnSelectPersonality -> {
                // 이미 포함되있으면 제거 아니면 추가 / 최대2개 포함되어야 해서 3개가 되면  _two.emit(Unit)
                _state.update { state ->
                    val updatedList = state.selectedList.toMutableList()

                    // 이미 포함된 경우 제거
                    if (updatedList.contains(action.personalityListOption)) {
                        updatedList.remove(action.personalityListOption)
                    } else {
                        // 포함되지 않은 경우 추가
                        if (updatedList.size < 2) {
                            updatedList.add(action.personalityListOption)
                        } else {
                            // 2개를 초과하면 알림 emit
                            emitTwo()
                        }
                    }

                    // 상태 업데이트
                    state.copy(selectedList = updatedList)
                }
            }
        }
    }
}