package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class CheckUserApplicationStatusUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyRecruitmentId: Int) = partyRepository.checkUserApplicationStatus(partyId = partyId, partyRecruitmentId = partyRecruitmentId)
}