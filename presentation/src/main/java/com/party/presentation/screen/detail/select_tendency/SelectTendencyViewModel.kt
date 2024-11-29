package com.party.presentation.screen.detail.select_tendency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PersonalitySave
import com.party.domain.usecase.datastore.GetAccessTokenUseCase
import com.party.domain.usecase.user.detail.GetPersonalityUseCase
import com.party.domain.usecase.user.detail.SavePersonalityUseCase
import com.skydoves.sandwich.StatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectTendencyViewModel @Inject constructor(
    private val getPersonalityUseCase: GetPersonalityUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val savePersonalityUseCase: SavePersonalityUseCase,
): ViewModel(){

    private val _personalityState = MutableStateFlow<UIState<ServerApiResponse<List<PersonalityList>>>>(UIState.Idle)
    val personalityState: MutableStateFlow<UIState<ServerApiResponse<List<PersonalityList>>>> = _personalityState

    private val _personalitySaveState = MutableStateFlow<UIState<ServerApiResponse<List<PersonalitySave>>>>(UIState.Idle)
    val personalitySaveState: MutableStateFlow<UIState<ServerApiResponse<List<PersonalitySave>>>> = _personalitySaveState

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> = _accessToken

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    private val _saveConflict = MutableSharedFlow<String>()
    val saveConflict = _saveConflict.asSharedFlow()

    fun getPersonalityList(){
        viewModelScope.launch(Dispatchers.IO) {
            _personalityState.value = UIState.Loading
            when(val result = getPersonalityUseCase()){
                is ServerApiResponse.SuccessResponse<List<PersonalityList>> -> {
                    _personalityState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<List<PersonalityList>> -> {
                    _personalityState.value = UIState.Error(result)
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _personalityState.value = UIState.Exception
                }
            }
        }
    }

    fun getAccessToken() = viewModelScope.launch(Dispatchers.IO) {
        getAccessTokenUseCase().collectLatest { accessToken ->
            _accessToken.emit(accessToken)
        }
    }

    fun savePersonality(personalitySaveRequest: PersonalitySaveRequest){
        viewModelScope.launch(Dispatchers.IO) {
            _personalitySaveState.value = UIState.Loading
            when(val result = savePersonalityUseCase(personalitySaveRequest = personalitySaveRequest)){
                is ServerApiResponse.SuccessResponse<List<PersonalitySave>> -> {
                    _saveSuccess.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse<List<PersonalitySave>> -> {
                    _personalitySaveState.value = UIState.Idle
                    when(result.statusCode){
                        StatusCode.Conflict.code -> { _saveConflict.emit(result.message!!) }
                    }
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _personalitySaveState.value = UIState.Idle
                }
            }
        }
    }
}