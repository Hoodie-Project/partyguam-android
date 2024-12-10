package com.party.presentation.screen.party_create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.component.bottomsheet.list.partyTypeList
import com.party.domain.usecase.party.CreatePartyUseCase
import com.party.presentation.screen.party_create.PartyCreateAction
import com.party.presentation.screen.party_create.PartyCreateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class PartyCreateViewModel @Inject constructor(
    private val createPartyUseCase: CreatePartyUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(PartyCreateState())
    val state= _state.asStateFlow()

    private val _backState = MutableSharedFlow<Unit>()
    val backState = _backState.asSharedFlow()

    private fun createParty(
        title: RequestBody,
        content: RequestBody,
        partyTypeId: RequestBody,
        positionId: RequestBody,
        image: MultipartBody.Part
    ){
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = createPartyUseCase(
                title = title,
                content = content,
                partyTypeId = partyTypeId,
                positionId = positionId,
                image = image
            )) {
                is ServerApiResponse.SuccessResponse -> {
                    val partyId = result.data?.id ?: 0
                    _state.update { it.copy(
                        isCompleteShowDialog = true,
                        partyId = partyId
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun dismissBackDialog(){
        _state.update { it.copy(isBackShowDialog = false) }
    }

    fun dismissCompleteDialog(){
        _state.update { it.copy(isCompleteShowDialog = false) }
    }

    fun navigationBack(){
        viewModelScope.launch {
            dismissBackDialog()
            _backState.emit(Unit)
        }
    }

    fun onAction(action: PartyCreateAction){
        when(action){
            is PartyCreateAction.OnIsShowDialogBack -> _state.update { it.copy(isBackShowDialog = action.isBackShowDialog) }
            is PartyCreateAction.OnIsVisibleToolTip -> _state.update { it.copy(isVisibleToolTip = action.isVisibleToolTip) }
            is PartyCreateAction.OnChangeImage -> _state.update { it.copy(image = action.image) }
            is PartyCreateAction.OnChangeInputTitle -> _state.update { it.copy(inputPartyTitle = action.title) }
            is PartyCreateAction.OnRemovePartyTitle -> _state.update { it.copy(inputPartyTitle = "") }
            is PartyCreateAction.OnChangePartyTypeSheet -> _state.update { it.copy(isPartyTypeSheetOpen = action.isPartyTypeSheetOpen) }
            is PartyCreateAction.OnChangeSelectPartyType -> _state.update { it.copy(selectedPartyType = action.partyType) }
            is PartyCreateAction.OnChangeIsShowHelpCard -> _state.update { it.copy(isHelpCardOpen = action.isShowHelpCard) }
            is PartyCreateAction.OnChangePartyDescription -> _state.update { it.copy(partyDescription = action.description) }
            is PartyCreateAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.position) }
            is PartyCreateAction.OnChangeSubPosition -> _state.update { it.copy(selectedSubPosition = action.positionList) }
            is PartyCreateAction.OnPartyCreate -> {
                createParty(
                    title = createRequestBody(_state.value.inputPartyTitle),
                    content = createRequestBody(_state.value.partyDescription),
                    partyTypeId = createRequestBody(partyTypeList.first { it.first == _state.value.selectedPartyType }.second.toString()),
                    positionId = createRequestBody(_state.value.selectedSubPosition.id.toString()),
                    image = _state.value.image
                )
            }
        }
    }

    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}