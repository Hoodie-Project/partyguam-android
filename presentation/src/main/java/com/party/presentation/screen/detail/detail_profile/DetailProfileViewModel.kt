package com.party.presentation.screen.detail.detail_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.LocationResponse
import com.party.domain.usecase.user.GetLocationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCase,
): ViewModel(){

    private val _getLocationListState = MutableStateFlow<UIState<ServerApiResponse<List<LocationResponse>>>>(UIState.Idle)
    val getLocationListState: StateFlow<UIState<ServerApiResponse<List<LocationResponse>>>> = _getLocationListState

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
}