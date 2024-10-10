package com.party.presentation.screen.detail.detail_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.model.user.detail.LocationResponse
import com.party.domain.model.user.detail.SaveInterestLocationResponse
import com.party.domain.usecase.user.detail.GetLocationListUseCase
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCase,
    private val saveInterestLocationUseCase: SaveInterestLocationUseCase,
): ViewModel(){

    private val _getLocationListState = MutableStateFlow<UIState<ServerApiResponse<List<LocationResponse>>>>(UIState.Idle)
    val getLocationListState: StateFlow<UIState<ServerApiResponse<List<LocationResponse>>>> = _getLocationListState

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    private val _saveFail = MutableSharedFlow<String>()
    val saveFail = _saveFail.asSharedFlow()

    fun getLocationList(province: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getLocationListUseCase(province = province)){
                is ServerApiResponse.SuccessResponse<*> -> {
                    _getLocationListState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    _getLocationListState.value = UIState.Error(result)
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _getLocationListState.value = UIState.Exception
                }
            }
        }
    }

    fun saveInterestLocation(accessToken: String, locations: List<InterestLocationRequest>){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = saveInterestLocationUseCase(accessToken = accessToken, locations = locations)){
                is ServerApiResponse.SuccessResponse<*> -> {
                    _saveSuccess.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    _saveFail.emit("저장에 실패했습니다.")
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _saveFail.emit("알 수 없는 오류가 발생했습니다.")
                }
            }
        }
    }
}