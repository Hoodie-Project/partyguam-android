package com.party.presentation.screen.recruitment_preview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition
import com.party.domain.usecase.party.GetRecruitmentDetailUseCase
import com.party.presentation.screen.recruitment_preview.RecruitmentPreviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentPreviewViewModel @Inject constructor(
    private val getRecruitmentDetailUseCase: GetRecruitmentDetailUseCase,
): ViewModel(){

    private val _recruitmentPreviewState = MutableStateFlow(RecruitmentPreviewState())
    val recruitmentPreviewState = _recruitmentPreviewState.asStateFlow()

    fun updateInfo(
        description: String,
        recruitingCount: Int,
        main: String,
        sub: String,
    ){
        viewModelScope.launch {
            _recruitmentPreviewState.update {
                it.copy(
                    description = description,
                    recruitingCount = recruitingCount,
                    main = main,
                    sub = sub
                )
            }
        }
    }

    fun getRecruitmentDetail(partyRecruitmentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getRecruitmentDetailUseCase(partyRecruitmentId = partyRecruitmentId)) {
                is ServerApiResponse.SuccessResponse<RecruitmentDetail> -> {
                    _recruitmentPreviewState.update {
                        it.copy(
                            recruitmentDetail = result.data ?: RecruitmentDetail(
                                party = RecruitmentDetailParty(
                                    title = "",
                                    image = "",
                                    status = "",
                                    partyType = RecruitmentDetailPartyType(type = "")
                                ),
                                position = RecruitmentDetailPosition(
                                    id = 0,
                                    main = "",
                                    sub = ""
                                ),
                                content = "",
                                recruitingCount = 0,
                                recruitedCount = 0,
                                applicationCount = 0,
                                createdAt = "2024-06-05T15:30:45.123Z"
                            )
                        )
                    }
                }

                is ServerApiResponse.ErrorResponse<RecruitmentDetail> -> { }
                is ServerApiResponse.ExceptionResponse -> { }
            }
        }
    }
}