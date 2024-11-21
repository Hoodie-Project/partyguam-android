package com.party.data.mapper

import com.party.data.entity.party.PartyDetailDto
import com.party.data.entity.party.PartyListEntity
import com.party.data.entity.party.PartyRecruitmentDto
import com.party.data.entity.party.PartyTypeDto
import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.data.entity.party.RecruitmentDetailDto
import com.party.data.entity.party.RecruitmentListEntity
import com.party.data.util.convertToImageUrl
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyItemResponse
import com.party.domain.model.party.PartyListResponse
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyType
import com.party.domain.model.party.PartyTypeItemResponse
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.PersonalRecruitmentPartyResponse
import com.party.domain.model.party.PersonalRecruitmentPartyTypeResponse
import com.party.domain.model.party.PersonalRecruitmentPositionResponse
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition
import com.party.domain.model.party.RecruitmentItemResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.domain.model.party.RecruitmentPartyResponse
import com.party.domain.model.party.RecruitmentPartyTypeResponse
import com.party.domain.model.party.RecruitmentPositionResponse

object PartyMapper {
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

    fun mapperRecruitmentDetailResponse(recruitmentListEntity: RecruitmentListEntity): RecruitmentListResponse{
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

    fun mapperRecruitmentDetailResponse(recruitmentDetailEntity: RecruitmentDetailDto): RecruitmentDetail{
        return RecruitmentDetail(
            party = RecruitmentDetailParty(
                title = recruitmentDetailEntity.party.title,
                image = convertToImageUrl(recruitmentDetailEntity.party.image),
                tag = recruitmentDetailEntity.party.tag,
                partyType = RecruitmentDetailPartyType(
                    type = recruitmentDetailEntity.party.partyType.type
                )
            ),
            position = RecruitmentDetailPosition(
                main = recruitmentDetailEntity.position.main,
                sub = recruitmentDetailEntity.position.sub
            ),
            content = recruitmentDetailEntity.content,
            recruitingCount = recruitmentDetailEntity.recruitingCount,
            recruitedCount = recruitmentDetailEntity.recruitedCount,
            applicationCount = recruitmentDetailEntity.applicationCount,
            createdAt = recruitmentDetailEntity.createdAt,
        )
    }

    fun mapperPartyDetail(partyDetailDto: PartyDetailDto): PartyDetail {
        return PartyDetail(
            id = partyDetailDto.id,
            partyType = mapperPartyType(partyDetailDto.partyType),
            tag = partyDetailDto.tag,
            title = partyDetailDto.title,
            content = partyDetailDto.content,
            image = convertToImageUrl(partyDetailDto.image),
            status = partyDetailDto.status,
            createdAt = partyDetailDto.createdAt,
            updatedAt = partyDetailDto.updatedAt,
            //userId = partyDetailDto.userId,
            //partyId = partyDetailDto.partyId,
            //positionId = partyDetailDto.positionId,
            //authority = partyDetailDto.authority,
            //myInfo = mapperMyInfo(partyDetailDto.myInfo)
        )
    }

    private fun mapperPartyType(partyTypeDto: PartyTypeDto): PartyType{
        return PartyType(
            id = partyTypeDto.id,
            type = partyTypeDto.type
        )
    }

    /*private fun mapperMyInfo(myInfoDto: MyInfoDto): MyInfo{
        return MyInfo(
            status = myInfoDto.status,
            createdAt = myInfoDto.createdAt,
            updatedAt = myInfoDto.updatedAt,
            id = myInfoDto.id,
            userId = myInfoDto.userId,
            partyId = myInfoDto.partyId,
            positionId = myInfoDto.positionId,
            authority = myInfoDto.authority
        )
    }*/

    fun mapperPartyRecruitment(partyRecruitmentDto: PartyRecruitmentDto): PartyRecruitment {
        return PartyRecruitment(
            partyRecruitmentId = partyRecruitmentDto.partyRecruitmentId,
            main = partyRecruitmentDto.main,
            sub = partyRecruitmentDto.sub,
            content = partyRecruitmentDto.content,
            recruitingCount = partyRecruitmentDto.recruitingCount,
            recruitedCount = partyRecruitmentDto.recruitedCount,
            applicationCount = partyRecruitmentDto.applicationCount,
            createdAt = partyRecruitmentDto.createdAt,
        )
    }
}