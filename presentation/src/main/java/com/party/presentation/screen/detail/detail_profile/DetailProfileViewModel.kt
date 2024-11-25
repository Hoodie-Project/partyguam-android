package com.party.presentation.screen.detail.detail_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.usecase.datastore.GetAccessTokenUseCase
import com.party.domain.usecase.user.detail.GetLocationListUseCase
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCase
import com.skydoves.sandwich.StatusCode
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
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
): ViewModel(){

    private val _getLocationListState = MutableStateFlow<UIState<ServerApiResponse<List<Location>>>>(UIState.Idle)
    val getLocationListState: StateFlow<UIState<ServerApiResponse<List<Location>>>> = _getLocationListState

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    private val _saveFail = MutableSharedFlow<String>()
    val saveFail = _saveFail.asSharedFlow()

    fun getLocationList(province: String){
        viewModelScope.launch(Dispatchers.IO) {
            _getLocationListState.value = UIState.Loading
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

    fun saveInterestLocation(locations: InterestLocationList){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = saveInterestLocationUseCase(locations = locations)){
                is ServerApiResponse.SuccessResponse<List<SaveInterestLocation>> -> _saveSuccess.emit(Unit)
                is ServerApiResponse.ErrorResponse<*> -> {
                    when(result.statusCode){
                        StatusCode.Conflict.code -> _saveFail.emit(result.message ?: "")
                        StatusCode.InternalServerError.code -> {}
                    }
                }
                is ServerApiResponse.ExceptionResponse -> _saveFail.emit("알 수 없는 오류가 발생했습니다.")
            }
        }
    }
}