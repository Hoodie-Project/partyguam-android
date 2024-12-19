package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class DeleteRecruitmentUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyRecruitmentId: Int) = partyRepository.deleteRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId)
}