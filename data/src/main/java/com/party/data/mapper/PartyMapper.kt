package com.party.data.mapper

import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.PersonalRecruitmentPartyResponse
import com.party.domain.model.party.PersonalRecruitmentPositionResponse

object PartyMapper {

    private fun convertToImageUrl(image: String?): String{
        return "https://partyguam.net/$image"
    }

    fun mapperPersonalRecruitmentResponse(personalRecruitmentListEntity: PersonalRecruitmentListEntity): PersonalRecruitmentListResponse{
        return PersonalRecruitmentListResponse(
            total = personalRecruitmentListEntity.total,
            partyRecruitments = personalRecruitmentListEntity.partyRecruitments.map {
                PersonalRecruitmentItemResponse(
                    id = it.id,
                    partyId = it.partyId,
                    positionId = it.positionId,
                    recruitingCount = it.recruitingCount,
                    recruitedCount = it.recruitedCount,
                    content = it.content,
                    createdAt = it.createdAt,
                    party = PersonalRecruitmentPartyResponse(
                        title = it.party.title,
                        image = convertToImageUrl(it.party.image)
                    ),
                    position = PersonalRecruitmentPositionResponse(
                        id = it.position.id,
                        main = it.position.main,
                        sub = it.position.sub
                    )
                )
            }
        )
    }

}