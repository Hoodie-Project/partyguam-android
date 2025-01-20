package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class CancelRecruitmentUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
) {
    suspend operator fun invoke(partyId: Int, partyApplicationId: Int) = partyRepository.cancelRecruitment(partyId = partyId, partyApplicationId = partyApplicationId)
}