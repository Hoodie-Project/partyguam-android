package com.party.presentation.screen.party_edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.component.bottomsheet.list.partyTypeList
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyType
import com.party.domain.usecase.party.GetPartyDetailUseCase
import com.party.domain.usecase.party.PartyModifyUseCase
import com.party.presentation.screen.party_edit.PartyEditAction
import com.party.presentation.screen.party_edit.PartyEditState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class PartyEditViewModel @Inject constructor(
    private val getPartyDetailUseCase: GetPartyDetailUseCase,
    private val partyModifyUseCase: PartyModifyUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(PartyEditState())
    val state = _state.asStateFlow()

    fun getPartyDetail(partyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyDetail = true) }
            when (val result = getPartyDetailUseCase(partyId = partyId)) {
                is ServerApiResponse.SuccessResponse -> {
                    val partyDetail = result.data ?: PartyDetail(
                        id = 0,
                        partyType = PartyType(id = 0, type = ""),
                        title = "",
                        content = "",
                        image = null,
                        status = "",
                        createdAt = "2024-06-05T15:30:45.123Z",
                        updatedAt = "2024-06-05T15:30:45.123Z"
                    )

                    _state.update { it.copy(
                        isLoadingPartyDetail = false,
                        inputPartyTitle = partyDetail.title,
                        selectedPartyType = partyDetail.partyType.type,
                        partyDescription = partyDetail.content,
                        partyStatus = partyDetail.status
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> { _state.update { it.copy(isLoadingPartyDetail = false) } }
                is ServerApiResponse.ExceptionResponse -> { _state.update { it.copy(isLoadingPartyDetail = false) } }
            }
        }
    }

    private fun modifyParty(
        partyId: Int,
        title: RequestBody?,
        content: RequestBody?,
        partyTypeId: RequestBody?,
        positionId: RequestBody?,
        image: MultipartBody.Part?
    ){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = partyModifyUseCase(
                partyId = partyId,
                title = title,
                content = content,
                partyTypeId = partyTypeId,
                positionId = positionId,
                image = image
            )){
                is ServerApiResponse.SuccessResponse -> {
                    getPartyDetail(partyId)
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isPartyModifyLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isPartyModifyLoading = false) }
            }
        }
    }

    fun dismissDeleteDialog(){
        _state.update { it.copy(isShowPartyDeleteDialog = false) }
    }

    fun onAction(action: PartyEditAction){
        when(action){
            is PartyEditAction.OnChangeImage -> _state.update { it.copy(image = action.image) }
            is PartyEditAction.OnIsVisibleToolTip -> _state.update { it.copy(isVisibleToolTip = action.isVisibleToolTip) }
            is PartyEditAction.OnRemovePartyTitle -> _state.update { it.copy(inputPartyTitle = "") }
            is PartyEditAction.OnChangeInputTitle -> _state.update { it.copy(inputPartyTitle = action.title) }
            is PartyEditAction.OnChangePartyTypeSheet -> _state.update { it.copy(isPartyTypeSheetOpen = action.isPartyTypeSheetOpen) }
            is PartyEditAction.OnChangeSelectPartyType -> _state.update { it.copy(selectedPartyType = action.partyType) }
            is PartyEditAction.OnChangeIsShowHelpCard -> _state.update { it.copy(isHelpCardOpen = action.isShowHelpCard) }
            is PartyEditAction.OnChangePartyDescription -> _state.update { it.copy(partyDescription = action.description) }
            is PartyEditAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.position) }
            is PartyEditAction.OnChangeMainPositionBottomSheet -> _state.update { it.copy(isMainPositionBottomSheetShow = action.isMainPositionBottomSheetShow) }
            is PartyEditAction.OnChangeSubPosition -> _state.update { it.copy(selectedSubPosition = action.positionList) }
            is PartyEditAction.OnPartyModify -> {
                modifyParty(
                    partyId = action.partyId,
                    title = createRequestBody(_state.value.inputPartyTitle),
                    content = createRequestBody(_state.value.partyDescription),
                    partyTypeId = createRequestBody(partyTypeList.first { it.first == _state.value.selectedPartyType }.second.toString()),
                    positionId = null,
                    image = _state.value.image
                )
            }
            is PartyEditAction.OnChangeShowPartyDeleteDialog -> _state.update { it.copy(isShowPartyDeleteDialog = action.isShowPartyDeleteDialog) }
        }
    }

    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}