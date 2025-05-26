package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class RejectApplicantUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyApplicationId: Int) = partyRepository.rejectApplicant(partyId = partyId, partyApplicationId = partyApplicationId)
}