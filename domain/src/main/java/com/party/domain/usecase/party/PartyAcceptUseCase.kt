package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class PartyAcceptUseCase @Inject constructor(
    private val partyRepository: PartyRepository
){
    suspend operator fun invoke(partyId: Int, partyApplicationId: Int) = partyRepository.rejectApplicant(partyId, partyApplicationId)
}