package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetRecruitmentApplicantUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
) {
    suspend operator fun invoke(partyId: Int, partyRecruitmentId: Int, page: Int, limit: Int, sort: String, order: String)
    = partyRepository.getRecruitmentApplicants(partyId = partyId, partyRecruitmentId = partyRecruitmentId, page = page, limit = limit,  sort = sort, order = order)
}