package com.party.presentation.screen.profile_edit_tendency.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PersonalitySaveRequest2
import com.party.domain.usecase.user.detail.DeleteUserPersonalityUseCase
import com.party.domain.usecase.user.detail.GetPersonalityUseCase
import com.party.domain.usecase.user.detail.SavePersonalityUseCase
import com.party.presentation.enum.PersonalityType
import com.party.presentation.screen.profile_edit_tendency.ProfileEditTendencyAction
import com.party.presentation.screen.profile_edit_tendency.ProfileEditTendencyState
import com.party.presentation.screen.profile_edit_time.ProfileEditTimeAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditTendencyViewModel @Inject constructor(
    private val getPersonalityUseCase: GetPersonalityUseCase,
    private val savePersonalityUseCase: SavePersonalityUseCase,
    private val deleteUserPersonalityUseCase: DeleteUserPersonalityUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ProfileEditTendencyState())
    val state = _state.asStateFlow()

    private val _modifySuccess = MutableSharedFlow<Unit>()
    val modifySuccess = _modifySuccess.asSharedFlow()

    private val _twoOverWarning = MutableSharedFlow<Unit>()
    val twoOverWarning = _twoOverWarning.asSharedFlow()

    private val _oneOverWarning = MutableSharedFlow<Unit>()
    val oneOverWarning = _oneOverWarning.asSharedFlow()

    fun getPersonalityList(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getPersonalityUseCase()){
                is ServerApiResponse.SuccessResponse -> { _state.update { it.copy(personalityList = result.data ?: emptyList()) } }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun deletePersonality(personalityQuestionId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = deleteUserPersonalityUseCase(personalityQuestionId = personalityQuestionId)){
                is ServerApiResponse.SuccessResponse -> {
                    //savePersonality(personalitySaveRequest = personalitySaveRequest)
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun deleteMultiplePersonalities(
        personalitySaveRequest: PersonalitySaveRequest,
        personalityQuestionIds: List<Int>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // 모든 deletePersonality 호출을 비동기로 실행
            val results = personalityQuestionIds.map { questionId ->
                async {
                    deletePersonality(questionId)
                }
            }

            // 모든 비동기 작업이 끝날 때까지 대기
            results.awaitAll()

            // 모든 작업이 끝난 후 추가 작업 실행
            savePersonality(personalitySaveRequest)
        }
    }

    private fun savePersonality(personalitySaveRequest: PersonalitySaveRequest){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = savePersonalityUseCase(personalitySaveRequest = personalitySaveRequest)){
                is ServerApiResponse.SuccessResponse -> { _modifySuccess.emit(Unit) }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun action(action: ProfileEditTendencyAction){
        when(action){
            is ProfileEditTendencyAction.OnChangeSelectedTab -> _state.update { it.copy(selectedTab = action.selectedTab) }
            is ProfileEditTendencyAction.OnApply -> {
                val personalityRequests = mutableListOf<PersonalitySaveRequest2>()

                // TENDENCY - 데이터가 있을 때만 추가
                if (_state.value.selectedTendencyList.isNotEmpty()) {
                    personalityRequests.add(
                        PersonalitySaveRequest2(
                            personalityQuestionId = PersonalityType.TENDENCY.id,
                            personalityOptionId = _state.value.selectedTendencyList.map { it.id }
                        )
                    )
                }

                // CONFIDENCE - 데이터가 있을 때만 추가
                if (_state.value.selectedConfidenceList.isNotEmpty()) {
                    personalityRequests.add(
                        PersonalitySaveRequest2(
                            personalityQuestionId = PersonalityType.CONFIDENCE.id,
                            personalityOptionId = _state.value.selectedConfidenceList.map { it.id }
                        )
                    )
                }

                // CHALLENGE - 데이터가 있을 때만 추가
                if (_state.value.selectedChallengeList.isNotEmpty()) {
                    personalityRequests.add(
                        PersonalitySaveRequest2(
                            personalityQuestionId = PersonalityType.CHALLENGE.id,
                            personalityOptionId = _state.value.selectedChallengeList.map { it.id }
                        )
                    )
                }

                val personalitySaveRequest = PersonalitySaveRequest(
                    personality = personalityRequests
                )

                deleteMultiplePersonalities(
                    personalitySaveRequest,
                    listOf(
                        PersonalityType.TENDENCY.id,
                        PersonalityType.CONFIDENCE.id,
                        PersonalityType.CHALLENGE.id
                    )
                )
            }

            is ProfileEditTendencyAction.OnSelectTendency -> {
                _state.update { state ->
                    val updatedList = state.selectedTendencyList.toMutableList()

                    // 이미 포함된 경우 제거
                    if (updatedList.contains(action.personalityListOption)) {
                        updatedList.remove(action.personalityListOption)
                    } else {
                        // 포함되지 않은 경우 추가
                        if (updatedList.size < 1) {
                            updatedList.add(action.personalityListOption)
                        } else {
                            // 2개를 초과하면 알림 emit
                            emitOne()
                        }
                    }

                    // 상태 업데이트
                    state.copy(selectedTendencyList = updatedList)
                }
            }
            is ProfileEditTendencyAction.OnSelectConfidence -> {
                _state.update { state ->
                    val updatedList = state.selectedConfidenceList.toMutableList()

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
                    state.copy(selectedConfidenceList = updatedList)
                }
            }
            is ProfileEditTendencyAction.OnSelectChallenge -> {
                _state.update { state ->
                    val updatedList = state.selectedChallengeList.toMutableList()

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
                    state.copy(selectedChallengeList = updatedList)
                }
            }
            is ProfileEditTendencyAction.OnResetFirstArea -> _state.update { it.copy(selectedTendencyList = emptyList()) }
            is ProfileEditTendencyAction.OnResetSecondArea -> _state.update { it.copy(selectedConfidenceList = emptyList()) }
            is ProfileEditTendencyAction.OnResetThirdArea -> _state.update { it.copy(selectedChallengeList = emptyList()) }
        }
    }

    private fun emitTwo(){
        viewModelScope.launch(Dispatchers.Main) {
            _twoOverWarning.emit(Unit)
        }
    }

    private fun emitOne(){
        viewModelScope.launch(Dispatchers.Main) {
            _oneOverWarning.emit(Unit)
        }
    }
}