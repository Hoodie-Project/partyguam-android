package com.party.presentation.screen.detail.select_tendency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.detail.PersonalityListResponse
import com.party.domain.usecase.datastore.GetAccessTokenUseCase
import com.party.domain.usecase.user.detail.GetPersonalityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectTendencyViewModel @Inject constructor(
    private val getPersonalityUseCase: GetPersonalityUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
): ViewModel(){

    private val _personalityState = MutableStateFlow<UIState<ServerApiResponse<List<PersonalityListResponse>>>>(UIState.Idle)
    val personalityState: MutableStateFlow<UIState<ServerApiResponse<List<PersonalityListResponse>>>> = _personalityState

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> = _accessToken

    fun getPersonalityList(accessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            _personalityState.value = UIState.Loading
            when(val result = getPersonalityUseCase(accessToken = accessToken)){
                is ServerApiResponse.SuccessResponse<List<PersonalityListResponse>> -> {
                    _personalityState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<List<PersonalityListResponse>> -> {
                    _personalityState.value = UIState.Idle
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _personalityState.value = UIState.Idle
                }
            }
        }
    }

    fun getAccessToken() = viewModelScope.launch(Dispatchers.IO) {
        getAccessTokenUseCase().collectLatest { accessToken ->
            _accessToken.emit(accessToken)
        }
    }
}