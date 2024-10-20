package com.party.data.mapper

import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.PersonalRecruitmentPartyResponse
import com.party.domain.model.party.PersonalRecruitmentPositionResponse

object PartyMapper {

    fun mapperPersonalRecruitmentResponse(personalRecruitmentListEntity: PersonalRecruitmentListEntity): PersonalRecruitmentListResponse{
        return PersonalRecruitmentListResponse(
            parties = personalRecruitmentListEntity.parties.map {
                PersonalRecruitmentItemResponse(
                    partyRecruitmentId = it.partyRecruitmentId,
                    main = it.main,
                    sub = it.sub,
                    content = it.content,
                    recruitingCount = it.recruitingCount,
                    recruitedCount = it.recruitedCount,
                    applicationCount = it.applicationCount,
                    createdAt = it.createdAt,
                    party = PersonalRecruitmentPartyResponse(
                        title = it.party.title,
                        image = it.party.image
                    ),
                    position = PersonalRecruitmentPositionResponse(
                        id = it.position.id,
                        main = it.position.main,
                        sub = it.position.sub
                    )
                )
            },
            total = personalRecruitmentListEntity.total
        )
    }
}