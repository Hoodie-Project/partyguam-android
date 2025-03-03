package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class CompletedPartyRecruitmentUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyRecruitmentId: Int) = partyRepository.completePartyRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId)
}