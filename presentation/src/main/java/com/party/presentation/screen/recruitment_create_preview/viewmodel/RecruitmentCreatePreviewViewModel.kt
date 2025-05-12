package com.party.presentation.screen.recruitment_create_preview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyType
import com.party.domain.usecase.party.GetPartyDetailUseCase
import com.party.presentation.screen.recruitment_create_preview.RecruitmentCreatePreviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentCreatePreviewViewModel @Inject constructor(
    private val getPartyDetailUseCase: GetPartyDetailUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(RecruitmentCreatePreviewState())
    val state = _state.asStateFlow()

    fun updateInfo(
        main: String,
        sub: String,
        recruitingCount: Int,
        description: String,
    ){
        _state.update {
            it.copy(
                main = main,
                sub = sub,
                recruitmentDescription = description,
                recruitingCount = recruitingCount,
            )
        }
    }

    fun getPartyDetail(partyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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

                    _state.update {
                        it.copy(
                            networkImage = partyDetail.image ?: "",
                            inputPartyTitle = partyDetail.title,
                            selectedPartyType = partyDetail.partyType.type,
                            partyStatus = partyDetail.status,
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }
}