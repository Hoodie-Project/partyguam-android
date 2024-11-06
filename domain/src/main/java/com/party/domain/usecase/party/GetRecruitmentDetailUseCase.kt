package com.party.domain.usecase.party

import com.party.common.ServerApiResponse
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetRecruitmentDetailUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
){
    suspend operator fun invoke(partyRecruitmentId: Int): ServerApiResponse<RecruitmentDetail>{
        return partyRepository.getRecruitmentDetail(partyRecruitmentId = partyRecruitmentId)
    }
}