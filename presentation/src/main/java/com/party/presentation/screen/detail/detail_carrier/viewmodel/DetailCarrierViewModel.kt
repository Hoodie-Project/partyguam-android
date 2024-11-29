package com.party.presentation.screen.detail.detail_carrier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.DetailCarrierData.mainSelectedMainPosition
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.domain.usecase.user.detail.SaveCarrierUseCase
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
class DetailCarrierViewModel @Inject constructor(
    private val getPositionsUseCase: GetPositionsUseCase,
    private val saveCarrierUseCase: SaveCarrierUseCase,
): ViewModel(){

    private val _positionsState = MutableStateFlow<UIState<ServerApiResponse<List<PositionList>>>>(UIState.Idle)
    val positionsState: StateFlow<UIState<ServerApiResponse<List<PositionList>>>> = _positionsState

    private val _saveCarrierState = MutableStateFlow<UIState<ServerApiResponse<List<SaveCarrier>>>>(UIState.Idle)
    val saveCarrierState: StateFlow<UIState<ServerApiResponse<List<SaveCarrier>>>> = _saveCarrierState

    private val _saveSuccessState = MutableSharedFlow<Unit>()
    val saveSuccessState = _saveSuccessState.asSharedFlow()

    fun getPositions(){
        viewModelScope.launch(Dispatchers.IO) {
            _positionsState.value = UIState.Loading
            when(val result = getPositionsUseCase(main = mainSelectedMainPosition)){
                is ServerApiResponse.SuccessResponse<List<PositionList>> -> {
                    _positionsState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<List<PositionList>> -> {
                    _positionsState.value = UIState.Idle
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _positionsState.value = UIState.Idle
                }
            }
        }
    }

    fun saveCarrier(career: SaveCarrierList){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = saveCarrierUseCase(career = career)){
                is ServerApiResponse.SuccessResponse -> {
                    _saveSuccessState.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse -> {
                    when(result.statusCode){
                        StatusCode.Conflict.code -> {
                            _saveCarrierState.value = UIState.Error(result)
                        }
                    }
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _positionsState.value = UIState.Idle
                }
            }
        }
    }

    fun convertToIntFromYear(year: String): Int{
        return when(year){
            "신입" -> 0
            "1년" -> 1
            "2년" -> 2
            "3년" -> 3
            "4년" -> 4
            "5년" -> 5
            "6년" -> 6
            "7년" -> 7
            "8년" -> 8
            "9년" -> 9
            "10년" -> 10
            else -> 0
        }
    }
}