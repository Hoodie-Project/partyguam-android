package com.party.domain.usecase.party

import com.party.common.ServerApiResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetRecruitmentListUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
){
    suspend operator fun invoke(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<RecruitmentListResponse>{
        return partyRepository.getRecruitmentList(page = page, size = size, sort = sort, order = order)
    }
}