package com.party.data.mapper

import com.party.data.entity.user.detail.LocationDto
import com.party.data.entity.user.detail.SaveInterestLocationDto
import com.party.data.entity.user.auth.SocialLoginErrorDto
import com.party.data.entity.user.auth.SocialLoginSuccessDto
import com.party.data.entity.user.auth.UserSignUpDto
import com.party.data.entity.user.detail.PersonalityListDto
import com.party.data.entity.user.detail.PersonalitySaveDto
import com.party.data.entity.user.detail.PositionListDto
import com.party.data.entity.user.detail.SaveCarrierEntity
import com.party.domain.model.user.detail.LocationResponse
import com.party.domain.model.user.SocialLoginErrorResponse
import com.party.domain.model.user.SocialLoginResponse
import com.party.domain.model.user.SocialLoginSuccessResponse
import com.party.domain.model.user.detail.PersonalityListOptionResponse
import com.party.domain.model.user.detail.PersonalityListResponse
import com.party.domain.model.user.detail.PersonalitySaveResponse
import com.party.domain.model.user.detail.PositionListResponse
import com.party.domain.model.user.detail.SaveCarrierResponse
import com.party.domain.model.user.detail.SaveInterestLocationResponse
import com.party.domain.model.user.signup.UserSignUpResponse

object UserMapper {
    fun mapperToSocialLoginResponse(socialLoginEntity: SocialLoginSuccessDto): SocialLoginResponse {
        return SocialLoginSuccessResponse(
            accessToken = socialLoginEntity.accessToken,
            refreshToken = socialLoginEntity.refreshToken
        )
    }

    fun mapperSocialLoginErrorResponse(socialLoginErrorEntity: SocialLoginErrorDto): SocialLoginErrorResponse {
        return SocialLoginErrorResponse(
            message = socialLoginErrorEntity.message,
            signupAccessToken = socialLoginErrorEntity.signupAccessToken
        )
    }

    fun mapperUserSignUpResponse(userSignUpEntity: UserSignUpDto): UserSignUpResponse {
        return UserSignUpResponse(
            accessToken = userSignUpEntity.accessToken
        )
    }

    fun mapperToLocationResponse(locationEntity: LocationDto): LocationResponse {
        return LocationResponse(
            id = locationEntity.id,
            province = locationEntity.province,
            city = locationEntity.city,
        )
    }

    fun mapperToSaveInterestLocationResponse(saveInterestLocationEntity: SaveInterestLocationDto): SaveInterestLocationResponse {
        return SaveInterestLocationResponse(
            id = saveInterestLocationEntity.id,
            userId = saveInterestLocationEntity.userId,
            locationId = saveInterestLocationEntity.locationId,
        )
    }

    fun mapperToPositionListResponse(positionListEntity: PositionListDto): PositionListResponse {
        return PositionListResponse(
            id = positionListEntity.id,
            main = positionListEntity.main,
            sub = positionListEntity.sub,
        )
    }

    fun mapperToSaveCarrierResponse(saveCarrierEntity: SaveCarrierEntity): SaveCarrierResponse {
        return SaveCarrierResponse(
            positionId = saveCarrierEntity.positionId,
            years = saveCarrierEntity.years,
            careerType = saveCarrierEntity.careerType,
        )
    }

    fun mapperToPersonalityListResponse(personalityListEntity: PersonalityListDto): PersonalityListResponse {
        return PersonalityListResponse(
            id = personalityListEntity.id,
            content = personalityListEntity.content,
            responseCount = personalityListEntity.responseCount,
            personalityOption = personalityListEntity.personalityOption.map {
                PersonalityListOptionResponse(
                    id = it.id,
                    personalityQuestionId = it.personalityQuestionId,
                    content = it.content
                )
            }
        )
    }

    fun mapperToPersonalitySaveResponse(personalitySaveEntity: PersonalitySaveDto): PersonalitySaveResponse {
        return PersonalitySaveResponse(
            id = personalitySaveEntity.id,
            userId = personalitySaveEntity.userId,
            personalityOptionId = personalitySaveEntity.personalityOptionId,
        )
    }
}