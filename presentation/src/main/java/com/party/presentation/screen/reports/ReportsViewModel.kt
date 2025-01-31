package com.party.presentation.screen.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.ReportsRequest
import com.party.domain.usecase.user.ReportsUseCase
import com.party.presentation.enum.ReportsType
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
class ReportsViewModel @Inject constructor(
    private val reportsUseCase: ReportsUseCase
): ViewModel() {

    private val _reportsState = MutableStateFlow(ReportsState())
    val reportsState = _reportsState.asStateFlow()

    private val _successReports = MutableSharedFlow<Unit>()
    val successReports = _successReports.asSharedFlow()

    private fun reports(typeId: Int, content: String){
        val reportsRequest = ReportsRequest(
            type = ReportsType.USER.type,
            typeId = typeId,
            content = content
        )
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = reportsUseCase(reportsRequest = reportsRequest)){
                is ServerApiResponse.SuccessResponse -> _successReports.emit(Unit)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: ReportsAction){
        when(action){
            is ReportsAction.OnChangeInputText -> { _reportsState.update { it.copy(inputReason = action.inputText) }}
            is ReportsAction.OnAllDeleteInputText -> { _reportsState.update { it.copy(inputReason = "") }}
            is ReportsAction.OnReports -> {
                reports(
                    typeId = action.typeId,
                    content = _reportsState.value.inputReason
                )
            }
        }
    }
}