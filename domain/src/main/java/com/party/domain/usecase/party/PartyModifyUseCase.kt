package com.party.domain.usecase.party

import com.party.domain.repository.PartyRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PartyModifyUseCase @Inject constructor(
    private val partyRepository: PartyRepository
) {
    suspend operator fun invoke(
        partyId: Int,
        title: RequestBody?,
        content: RequestBody?,
        partyTypeId: RequestBody?,
        positionId: RequestBody?,
        image: MultipartBody.Part?
    ) = partyRepository.modifyParty(
        partyId = partyId,
        title = title,
        content = content,
        partyTypeId = partyTypeId,
        positionId = positionId,
        image = image
    )
}