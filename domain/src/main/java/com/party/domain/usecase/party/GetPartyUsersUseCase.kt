package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetPartyUsersUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
) {
    suspend operator fun invoke(partyId: Int, page: Int, limit: Int, sort: String, order: String) = partyRepository.getPartyUsers(partyId, page, limit, sort, order)
}