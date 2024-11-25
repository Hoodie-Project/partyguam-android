package com.party.data.mapper

import com.party.data.entity.party.PartyAdminDto
import com.party.data.entity.party.PartyAuthorityDto
import com.party.data.entity.party.PartyCreateDto
import com.party.data.entity.party.PartyDetailDto
import com.party.data.entity.party.PartyListEntity
import com.party.data.entity.party.PartyRecruitmentDto
import com.party.data.entity.party.PartyTypeDto
import com.party.data.entity.party.PartyUserDto
import com.party.data.entity.party.PartyUsersDto
import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.data.entity.party.PositionDto
import com.party.data.entity.party.RecruitmentDetailDto
import com.party.data.entity.party.RecruitmentListEntity
import com.party.data.entity.party.UserCareerDto
import com.party.data.entity.party.UserDto
import com.party.data.util.convertToImageUrl
import com.party.domain.model.party.PartyAdmin
import com.party.domain.model.party.PartyCreate
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyItemResponse
import com.party.domain.model.party.PartyListResponse
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyType
import com.party.domain.model.party.PartyTypeItemResponse
import com.party.domain.model.party.PartyUser
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.party.PersonalRecruitmentItemResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.PersonalRecruitmentPartyResponse
import com.party.domain.model.party.PersonalRecruitmentPartyTypeResponse
import com.party.domain.model.party.PersonalRecruitmentPositionResponse
import com.party.domain.model.party.Position
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition
import com.party.domain.model.party.RecruitmentItemResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.domain.model.party.RecruitmentPartyResponse
import com.party.domain.model.party.RecruitmentPartyTypeResponse
import com.party.domain.model.party.RecruitmentPositionResponse
import com.party.domain.model.party.User
import com.party.domain.model.party.UserCareer
import com.party.domain.model.user.PartyAuthority

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
        )
    }

    private fun mapperPartyType(partyTypeDto: PartyTypeDto): PartyType{
        return PartyType(
            id = partyTypeDto.id,
            type = partyTypeDto.type
        )
    }

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

    fun mapperPartyUsers(partyUsersDto: PartyUsersDto): PartyUsers{
        return PartyUsers(
            partyAdmin = partyUsersDto.partyAdmin.map {
                mapperPartyAdmin(it)
            },
            partyUser = partyUsersDto.partyUser.map {
                mapperPartyUser(it)
            }
        )
    }

    fun mapperPartyAuthority(partyAuthorityDto: PartyAuthorityDto): PartyAuthority{
        return PartyAuthority(
            authority = partyAuthorityDto.authority,
            userId = partyAuthorityDto.userId,
        )
    }

    private fun mapperPartyAdmin(partyAdminDto: PartyAdminDto): PartyAdmin{
        return PartyAdmin(
            authority = partyAdminDto.authority,
            position = mapperPosition(partyAdminDto.position),
            user = mapperToUser(partyAdminDto.user)
        )
    }

    private fun mapperPartyUser(partyUserDto: PartyUserDto): PartyUser{
        return PartyUser(
            authority = partyUserDto.authority,
            position = mapperPosition(partyUserDto.position),
            user = mapperToUser(partyUserDto.user)
        )
    }

    private fun mapperPosition(positionDto: PositionDto): Position{
        return Position(
            id = positionDto.id,
            main = positionDto.main,
            sub = positionDto.sub
        )
    }

    private fun mapperToUser(userDto: UserDto): User {
        return User(
            id = userDto.id,
            nickname = userDto.nickname,
            image = convertToImageUrl(userDto.image),
            userCareers = userDto.userCareers.map {
                mapperToUserCareer(it)
            }
        )
    }

    private fun mapperToUserCareer(userCareerDto: UserCareerDto): UserCareer {
        return UserCareer(
            positionId = userCareerDto.positionId,
            years = userCareerDto.years
        )
    }

    fun mapperToPartyCreate(partyCreateDto: PartyCreateDto): PartyCreate{
        return PartyCreate(
            id = partyCreateDto.id,
            partyTypeId = partyCreateDto.partyTypeId,
            title = partyCreateDto.title,
            content = partyCreateDto.content,
            image = convertToImageUrl(partyCreateDto.image),
            status = partyCreateDto.status,
            createdAt = partyCreateDto.createdAt,
            updatedAt = partyCreateDto.updatedAt,
        )
    }
}