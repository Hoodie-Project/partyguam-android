package com.party.domain.usecase.party

import com.party.domain.model.party.RecruitmentCreateRequest
import com.party.domain.repository.PartyRepository
import javax.inject.Inject

class CreateRecruitmentUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(partyId: Int, recruitmentCreateRequest: RecruitmentCreateRequest) = partyRepository.saveRecruitment(partyId = partyId, recruitmentCreateRequest = recruitmentCreateRequest)
}