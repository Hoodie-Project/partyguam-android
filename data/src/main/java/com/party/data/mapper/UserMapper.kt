package com.party.data.mapper

import com.party.data.dto.user.detail.LocationDto
import com.party.data.dto.user.detail.SaveInterestLocationDto
import com.party.data.dto.user.auth.SocialLoginErrorDto
import com.party.data.dto.user.auth.SocialLoginSuccessDto
import com.party.data.dto.user.auth.UserSignUpDto
import com.party.data.dto.user.detail.PersonalityListDto
import com.party.data.dto.user.detail.PersonalitySaveDto
import com.party.data.dto.user.detail.PositionListDto
import com.party.data.dto.user.detail.SaveCarrierItem
import com.party.data.dto.user.party.MyPartyDto
import com.party.data.dto.user.party.PartyUserDto
import com.party.data.dto.user.recruitment.MyRecruitmentDto
import com.party.data.dto.user.recruitment.PartyApplicationDto
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.SocialLoginError
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.SocialLoginSuccess
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalitySave
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.party.Party
import com.party.domain.model.user.party.PartyType
import com.party.domain.model.user.party.PartyUser
import com.party.domain.model.user.party.Position
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.recruitment.PartyApplication
import com.party.domain.model.user.recruitment.PartyRecruitment
import com.party.domain.model.user.signup.UserSignUp

object UserMapper {
    fun mapperToSocialLoginResponse(socialLoginEntity: SocialLoginSuccessDto): SocialLogin {
        return SocialLoginSuccess(
            accessToken = socialLoginEntity.accessToken,
            refreshToken = socialLoginEntity.refreshToken
        )
    }

    fun mapperSocialLoginErrorResponse(socialLoginErrorEntity: SocialLoginErrorDto): SocialLoginError {
        return SocialLoginError(
            message = socialLoginErrorEntity.message,
            signupAccessToken = socialLoginErrorEntity.signupAccessToken
        )
    }

    fun mapperUserSignUpResponse(userSignUpEntity: UserSignUpDto): UserSignUp {
        return UserSignUp(
            accessToken = userSignUpEntity.accessToken
        )
    }

    fun mapperToLocationResponse(locationEntity: LocationDto): Location {
        return Location(
            id = locationEntity.id,
            province = locationEntity.province,
            city = locationEntity.city,
        )
    }

    fun mapperToSaveInterestLocationResponse(saveInterestLocationEntity: SaveInterestLocationDto): SaveInterestLocation {
        return SaveInterestLocation(
            id = saveInterestLocationEntity.id,
            userId = saveInterestLocationEntity.userId,
            locationId = saveInterestLocationEntity.locationId,
        )
    }

    fun mapperToPositionListResponse(positionListEntity: PositionListDto): PositionList {
        return PositionList(
            id = positionListEntity.id,
            main = positionListEntity.main,
            sub = positionListEntity.sub,
        )
    }

    fun mapperToSaveCarrierResponse(saveCarrierEntity: SaveCarrierItem): SaveCarrier {
        return SaveCarrier(
            positionId = saveCarrierEntity.positionId,
            years = saveCarrierEntity.years,
            careerType = saveCarrierEntity.careerType,
        )
    }

    fun mapperToPersonalityListResponse(personalityListEntity: PersonalityListDto): PersonalityList {
        return PersonalityList(
            id = personalityListEntity.id,
            content = personalityListEntity.content,
            responseCount = personalityListEntity.responseCount,
            personalityOptions = personalityListEntity.personalityOptions.map {
                PersonalityListOption(
                    id = it.id,
                    personalityQuestionId = it.personalityQuestionId,
                    content = it.content
                )
            }
        )
    }

    fun mapperToPersonalitySaveResponse(personalitySaveEntity: PersonalitySaveDto): PersonalitySave {
        return PersonalitySave(
            id = personalitySaveEntity.id,
            userId = personalitySaveEntity.userId,
            personalityOptionId = personalitySaveEntity.personalityOptionId,
        )
    }

    // 내 파티 리스트 조회
    fun mapperToMyParty(myPartyDto: MyPartyDto): MyParty{
        return MyParty(
            total = myPartyDto.total,
            partyUsers = myPartyDto.partyUsers.map { mapperToPartyUser(it) }
        )
    }

    private fun mapperToPartyUser(partyUserDto: PartyUserDto): PartyUser{
        return PartyUser(
            id = partyUserDto.id,
            createdAt = partyUserDto.createdAt,
            position = Position(
                main = partyUserDto.position.main,
                sub = partyUserDto.position.sub
            ),
            party = Party(
                id = partyUserDto.party.id,
                title = partyUserDto.party.title,
                image = partyUserDto.party.image,
                partyType = PartyType(
                    type = partyUserDto.party.partyType.type
                )
            )
        )
    }

    // 내 지원목록 리스트 조회
    fun mapperToMyRecruitment(myRecruitmentDto: MyRecruitmentDto): MyRecruitment {
        return MyRecruitment(
            total = myRecruitmentDto.total,
            partyApplications = myRecruitmentDto.partyApplications.map { mapperToPartyApplication(it) }
        )
    }

    private fun mapperToPartyApplication(partyApplicationDto: PartyApplicationDto): PartyApplication {
        return PartyApplication(
            id = partyApplicationDto.id,
            message = partyApplicationDto.message,
            status = partyApplicationDto.status,
            createdAt = partyApplicationDto.createdAt,
            partyRecruitment = PartyRecruitment(
                position = com.party.domain.model.user.recruitment.Position(
                    main = partyApplicationDto.partyRecruitment.position.main,
                    sub = partyApplicationDto.partyRecruitment.position.sub
                ),
                party = com.party.domain.model.user.recruitment.Party(
                    id = partyApplicationDto.partyRecruitment.party.id,
                    title = partyApplicationDto.partyRecruitment.party.title,
                    image = partyApplicationDto.partyRecruitment.party.image,
                    partyType = com.party.domain.model.user.recruitment.PartyType(
                        type = partyApplicationDto.partyRecruitment.party.partyType.type
                    )
                )
            )
        )
    }
}