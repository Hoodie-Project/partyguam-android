package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class GetPartyMemberInfoUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
) {
    suspend operator fun invoke(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String,
        main: String?,
    ) = partyRepository.getPartyMembers(partyId = partyId, page = page, limit = limit, sort = sort, order = order, main = main)
}