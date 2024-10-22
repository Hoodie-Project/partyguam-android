package com.party.data.mapper

import com.party.data.entity.party.PartyListEntity
import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.data.entity.party.RecruitmentListEntity
import com.party.domain.model.party.PartyItemResponse
import com.party.domain.model.party.PartyListResponse
import com.party.domain.model.party.PartyTypeItemResponse
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.PersonalRecruitmentPartyResponse
import com.party.domain.model.party.PersonalRecruitmentPartyTypeResponse
import com.party.domain.model.party.PersonalRecruitmentPositionResponse
import com.party.domain.model.party.RecruitmentItemResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.domain.model.party.RecruitmentPartyResponse
import com.party.domain.model.party.RecruitmentPartyTypeResponse
import com.party.domain.model.party.RecruitmentPositionResponse

object PartyMapper {

    private fun convertToImageUrl(image: String?): String{
        return "https://partyguam.net/dev/api/$image"
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
                        image = convertToImageUrl(it.party.image),
                        partyType = PersonalRecruitmentPartyTypeResponse(
                            id = it.party.partyType.id,
                            type = it.party.partyType.type
                        )
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

    fun mapperRecruitmentResponse(recruitmentListEntity: RecruitmentListEntity): RecruitmentListResponse{
        return RecruitmentListResponse(
            total = recruitmentListEntity.total,
            partyRecruitments = recruitmentListEntity.partyRecruitments.map {
                RecruitmentItemResponse(
                    id = it.id,
                    partyId = it.partyId,
                    positionId = it.positionId,
                    recruitingCount = it.recruitingCount,
                    recruitedCount = it.recruitedCount,
                    content = it.content,
                    createdAt = it.createdAt,
                    party = RecruitmentPartyResponse(
                        title = it.party.title,
                        image = convertToImageUrl(it.party.image),
                        partyType = RecruitmentPartyTypeResponse(
                            id = it.party.partyType.id,
                            type = it.party.partyType.type
                        )
                    ),
                    position = RecruitmentPositionResponse(
                        id = it.position.id,
                        main = it.position.main,
                        sub = it.position.sub
                    )
                )
            }
        )
    }

    fun mapperPartyResponse(partyListEntity: PartyListEntity): PartyListResponse {
        return PartyListResponse(
            total = partyListEntity.total,
            parties = partyListEntity.parties.map {
                PartyItemResponse(
                    id = it.id,
                    partyType = PartyTypeItemResponse(
                        id = it.partyType.id,
                        type = it.partyType.type
                    ),
                    tag = it.tag,
                    title = it.title,
                    content = it.content,
                    image = convertToImageUrl(it.image),
                    status = it.status,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    recruitmentCount = it.recruitmentCount,
                )
            }
        )
    }
}