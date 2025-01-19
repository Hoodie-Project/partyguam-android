package com.party.data.mapper

import com.party.data.dto.party.ApprovalAndRejectionDto
import com.party.data.dto.party.PartyAdminDto
import com.party.data.dto.party.PartyApplicationUserDto
import com.party.data.dto.party.PartyApplyDto
import com.party.data.dto.party.PartyAuthorityDto
import com.party.data.dto.party.PartyCreateDto
import com.party.data.dto.party.PartyDetailDto
import com.party.data.dto.party.PartyListDto
import com.party.data.dto.party.PartyMemberInfoDto
import com.party.data.dto.party.PartyMemberPositionDto
import com.party.data.dto.party.PartyModifyDto
import com.party.data.dto.party.PartyRecruitmentDto
import com.party.data.dto.party.PartyTypeDto
import com.party.data.dto.party.PartyUserDto
import com.party.data.dto.party.PartyUserInfoDto
import com.party.data.dto.party.PartyUsersDto
import com.party.data.dto.party.PersonalRecruitmentListDto
import com.party.data.dto.party.PositionDto
import com.party.data.dto.party.RecruitmentApplicantDto
import com.party.data.dto.party.RecruitmentCreateDto
import com.party.data.dto.party.RecruitmentDetailDto
import com.party.data.dto.party.RecruitmentListDto
import com.party.data.dto.party.UserCareerDto
import com.party.data.dto.party.UserDto
import com.party.data.util.convertToImageUrl
import com.party.domain.model.party.ApplicantUser
import com.party.domain.model.party.ApprovalAndRejection
import com.party.domain.model.party.PartyAdmin
import com.party.domain.model.party.PartyApplicationUser
import com.party.domain.model.party.PartyApply
import com.party.domain.model.party.PartyCreate
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyItem
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.PartyMemberInfo
import com.party.domain.model.party.PartyMemberPosition
import com.party.domain.model.party.PartyModify
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyType
import com.party.domain.model.party.PartyTypeItem
import com.party.domain.model.party.PartyUser
import com.party.domain.model.party.PartyUserInfo
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.party.PersonalRecruitmentItem
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.model.party.PersonalRecruitmentParty
import com.party.domain.model.party.PersonalRecruitmentPartyType
import com.party.domain.model.party.PersonalRecruitmentPosition
import com.party.domain.model.party.Position
import com.party.domain.model.party.Position1
import com.party.domain.model.party.RecruitmentApplicant
import com.party.domain.model.party.RecruitmentCreate
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition
import com.party.domain.model.party.RecruitmentItem
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.party.RecruitmentParty
import com.party.domain.model.party.RecruitmentPartyType
import com.party.domain.model.party.RecruitmentPosition
import com.party.domain.model.party.User
import com.party.domain.model.party.UserCareer
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition

object PartyMapper {
    fun mapperPersonalRecruitmentResponse(personalRecruitmentListDto: PersonalRecruitmentListDto): PersonalRecruitmentList{
        return PersonalRecruitmentList(
            total = personalRecruitmentListDto.total,
            partyRecruitments = personalRecruitmentListDto.partyRecruitments.map {
                PersonalRecruitmentItem(
                    id = it.id,
                    //partyId = it.partyId,
                    //positionId = it.positionId,
                    recruitingCount = it.recruitingCount,
                    recruitedCount = it.recruitedCount,
                    content = it.content,
                    createdAt = it.createdAt,
                    party = PersonalRecruitmentParty(
                        id = it.party.id,
                        title = it.party.title,
                        image = convertToImageUrl(it.party.image),
                        partyType = PersonalRecruitmentPartyType(
                            id = it.party.partyType.id,
                            type = it.party.partyType.type
                        )
                    ),
                    position = PersonalRecruitmentPosition(
                        id = it.position.id,
                        main = it.position.main,
                        sub = it.position.sub
                    )
                )
            }
        )
    }

    fun mapperRecruitmentDetailResponse(recruitmentListDto: RecruitmentListDto): RecruitmentList{
        return RecruitmentList(
            total = recruitmentListDto.total,
            partyRecruitments = recruitmentListDto.partyRecruitments.map {
                RecruitmentItem(
                    id = it.id,
                    recruitingCount = it.recruitingCount,
                    recruitedCount = it.recruitedCount,
                    content = it.content,
                    createdAt = it.createdAt,
                    party = RecruitmentParty(
                        id = it.party.id,
                        title = it.party.title,
                        image = convertToImageUrl(it.party.image),
                        partyType = RecruitmentPartyType(
                            id = it.party.partyType.id,
                            type = it.party.partyType.type
                        )
                    ),
                    position = RecruitmentPosition(
                        id = it.position.id,
                        main = it.position.main,
                        sub = it.position.sub
                    )
                )
            }
        )
    }

    fun mapperPartyResponse(partyListDto: PartyListDto): PartyList {
        return PartyList(
            total = partyListDto.total,
            parties = partyListDto.parties.map {
                PartyItem(
                    id = it.id,
                    partyType = PartyTypeItem(
                        id = it.partyType.id,
                        type = it.partyType.type
                    ),
                    //tag = it.tag,
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

    fun mapperRecruitmentDetailResponse(recruitmentDetailDto: RecruitmentDetailDto): RecruitmentDetail{
        return RecruitmentDetail(
            party = RecruitmentDetailParty(
                title = recruitmentDetailDto.party.title,
                image = convertToImageUrl(recruitmentDetailDto.party.image),
                status = recruitmentDetailDto.party.status,
                partyType = RecruitmentDetailPartyType(
                    type = recruitmentDetailDto.party.partyType.type
                )
            ),
            position = RecruitmentDetailPosition(
                id = recruitmentDetailDto.position.id,
                main = recruitmentDetailDto.position.main,
                sub = recruitmentDetailDto.position.sub
            ),
            content = recruitmentDetailDto.content,
            recruitingCount = recruitmentDetailDto.recruitingCount,
            recruitedCount = recruitmentDetailDto.recruitedCount,
            applicationCount = recruitmentDetailDto.applicationCount,
            createdAt = recruitmentDetailDto.createdAt,
        )
    }

    fun mapperPartyDetail(partyDetailDto: PartyDetailDto): PartyDetail {
        return PartyDetail(
            id = partyDetailDto.id,
            partyType = mapperPartyType(partyDetailDto.partyType),
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
            id = partyRecruitmentDto.id,
            status = partyRecruitmentDto.status,
            position = Position1(
                main = partyRecruitmentDto.position.main,
                sub = partyRecruitmentDto.position.sub
            ),
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
            id = partyAuthorityDto.id,
            authority = partyAuthorityDto.authority,
            position = PartyAuthorityPosition(
                id = partyAuthorityDto.position.id,
                main = partyAuthorityDto.position.main,
                sub = partyAuthorityDto.position.sub,
            )
        )
    }

    private fun mapperPartyAdmin(partyAdminDto: PartyAdminDto): PartyAdmin{
        return PartyAdmin(
            id = partyAdminDto.id,
            authority = partyAdminDto.authority,
            position = mapperPosition(partyAdminDto.position),
            user = mapperToUser(partyAdminDto.user)
        )
    }

    private fun mapperPartyUser(partyUserDto: PartyUserDto): PartyUser{
        return PartyUser(
            id = partyUserDto.id,
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

    fun mapperToPartyApply(partyApplyDto: PartyApplyDto): PartyApply{
        return PartyApply(
            id = partyApplyDto.id,
            message = partyApplyDto.message,
            status = partyApplyDto.status,
            createdAt = partyApplyDto.createdAt,
        )
    }

    fun mapperToRecruitmentCreate(recruitmentCreateDto: RecruitmentCreateDto): RecruitmentCreate {
        return RecruitmentCreate(
            id = recruitmentCreateDto.id,
            content = recruitmentCreateDto.content,
            recruitingCount = recruitmentCreateDto.recruitingCount,
            recruitedCount = recruitmentCreateDto.recruitedCount,
            createdAt = recruitmentCreateDto.createdAt,
        )
    }

    fun mapperPartyModify(partyModifyDto: PartyModifyDto): PartyModify{
        return PartyModify(
            id = partyModifyDto.id,
            partyTypeId = partyModifyDto.partyTypeId,
            title = partyModifyDto.title,
            content = partyModifyDto.content,
            image = convertToImageUrl(partyModifyDto.image),
            status = partyModifyDto.status,
            updatedAt = partyModifyDto.updatedAt,
        )
    }

    fun mapperPartyMemberInfo(partyMemberInfoDto: PartyMemberInfoDto): PartyMemberInfo {
        return PartyMemberInfo(
            id = partyMemberInfoDto.id,
            createdAt = partyMemberInfoDto.createdAt,
            status = partyMemberInfoDto.status,
            authority = partyMemberInfoDto.authority,
            user = mapperPartyUserInfo(partyMemberInfoDto.user),
            position = mapperPartyMemberPosition(partyMemberInfoDto.position)
        )
    }

    private fun mapperPartyUserInfo(partyUserInfoDto: PartyUserInfoDto): PartyUserInfo {
        return PartyUserInfo(
            nickname = partyUserInfoDto.nickname,
            image = convertToImageUrl(partyUserInfoDto.image)
        )
    }

    private fun mapperPartyMemberPosition(partyMemberPositionDto: PartyMemberPositionDto): PartyMemberPosition {
        return PartyMemberPosition(
            main = partyMemberPositionDto.main,
            sub = partyMemberPositionDto.sub
        )
    }

    fun mapperToRecruitmentApplicant(recruitmentApplicantDto: RecruitmentApplicantDto): RecruitmentApplicant{
        return RecruitmentApplicant(
            total = recruitmentApplicantDto.total,
            partyApplicationUser = recruitmentApplicantDto.partyApplicationUser.map {
                mapperToPartyApplicationUser(it)
            }
        )
    }

    private fun mapperToPartyApplicationUser(partyApplicationUserDto: PartyApplicationUserDto): PartyApplicationUser{
        return PartyApplicationUser(
            id = partyApplicationUserDto.id,
            message = partyApplicationUserDto.message,
            status = partyApplicationUserDto.status,
            createdAt = partyApplicationUserDto.createdAt,
            user = ApplicantUser(
                id = partyApplicationUserDto.user.id,
                nickname = partyApplicationUserDto.user.nickname,
                image = convertToImageUrl(partyApplicationUserDto.user.image)
            )
        )
    }

    fun mapperApprovalAndRejection(approvalAndRejectionDto: ApprovalAndRejectionDto): ApprovalAndRejection{
        return ApprovalAndRejection(
            message = approvalAndRejectionDto.message,
        )
    }
}