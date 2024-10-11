package com.party.presentation.screen.detail.detail_carrier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.DetailCarrierData.mainSelectedMainPosition
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.detail.PositionListResponse
import com.party.domain.usecase.datastore.GetAccessTokenUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCarrierViewModel @Inject constructor(
    private val getPositionsUseCase: GetPositionsUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
): ViewModel(){

    private val _positionsState = MutableStateFlow<UIState<ServerApiResponse<List<PositionListResponse>>>>(UIState.Idle)
    val positionsState: StateFlow<UIState<ServerApiResponse<List<PositionListResponse>>>> = _positionsState

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> = _accessToken

    fun getPositions(accessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            _positionsState.value = UIState.Loading
            when(val result = getPositionsUseCase(accessToken = accessToken, main = mainSelectedMainPosition)){
                is ServerApiResponse.SuccessResponse<List<PositionListResponse>> -> {
                    _positionsState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<List<PositionListResponse>> -> {
                    _positionsState.value = UIState.Idle
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _positionsState.value = UIState.Idle
                }
            }
        }
    }

    fun getAccessToken() = viewModelScope.launch(Dispatchers.IO) {
        getAccessTokenUseCase().collectLatest { accessToken ->
            _accessToken.emit(accessToken)
        }
    }

    /*private fun convertToIntFromYear(year: String): Int{
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
    }*/
}