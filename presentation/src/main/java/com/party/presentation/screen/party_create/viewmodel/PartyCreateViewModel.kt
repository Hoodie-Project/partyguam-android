package com.party.presentation.screen.party_create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.PartyCreate
import com.party.domain.usecase.party.CreatePartyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PartyCreateViewModel @Inject constructor(
    private val createPartyUseCase: CreatePartyUseCase,
): ViewModel() {

    private val _createPartyState = MutableStateFlow<UIState<ServerApiResponse<PartyCreate>>>(UIState.Idle)
    val createPartyState: StateFlow<UIState<ServerApiResponse<PartyCreate>>> = _createPartyState

    private val _createSuccess = MutableSharedFlow<Unit>()
    val createSuccess = _createSuccess.asSharedFlow()

    fun createParty(
        title: RequestBody,
        content: RequestBody,
        partyTypeId: RequestBody,
        positionId: RequestBody,
        image: MultipartBody.Part
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _createPartyState.emit(UIState.Loading)
            when (val result = createPartyUseCase(
                title = title,
                content = content,
                partyTypeId = partyTypeId,
                positionId = positionId,
                image = image
            )) {
                is ServerApiResponse.SuccessResponse -> {
                    _createSuccess.emit(Unit)
                }

                is ServerApiResponse.ErrorResponse -> {
                }

                is ServerApiResponse.ExceptionResponse -> {
                }
            }
        }
    }
}