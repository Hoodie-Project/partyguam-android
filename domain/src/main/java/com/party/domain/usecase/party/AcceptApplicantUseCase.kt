package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class AcceptApplicantUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, partyApplicationId: Int) = partyRepository.acceptApplicant(partyId = partyId, partyApplicationId = partyApplicationId)
}