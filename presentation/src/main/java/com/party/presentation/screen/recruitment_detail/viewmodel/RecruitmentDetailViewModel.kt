package com.party.presentation.screen.recruitment_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition
import com.party.domain.usecase.party.GetRecruitmentDetailUseCase
import com.party.presentation.screen.recruitment_detail.RecruitmentDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentDetailViewModel @Inject constructor(
    private val getRecruitmentDetailUseCase: GetRecruitmentDetailUseCase,
) : ViewModel() {

    private val _recruitmentDetailState = MutableStateFlow(RecruitmentDetailState())
    val recruitmentDetailState = _recruitmentDetailState.asStateFlow()

    fun getRecruitmentDetail(partyRecruitmentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _recruitmentDetailState.update { it.copy(isLoading = true) }

            when (val result =
                getRecruitmentDetailUseCase(partyRecruitmentId = partyRecruitmentId)) {
                is ServerApiResponse.SuccessResponse<RecruitmentDetail> -> {
                    _recruitmentDetailState.update {
                        it.copy(
                            isLoading = false,
                            recruitmentDetail = result.data ?: RecruitmentDetail(
                                party = RecruitmentDetailParty(
                                    title = "",
                                    image = "",
                                    status = "",
                                    partyType = RecruitmentDetailPartyType(type = "")
                                ),
                                position = RecruitmentDetailPosition(
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

                is ServerApiResponse.ErrorResponse<RecruitmentDetail> -> { _recruitmentDetailState.update { it.copy(isLoading = false) } }
                is ServerApiResponse.ExceptionResponse -> { _recruitmentDetailState.update { it.copy(isLoading = false) } }
            }
        }
    }
}