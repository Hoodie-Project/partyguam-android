package com.party.data.mapper

import com.party.data.entity.user.detail.LocationDto
import com.party.data.entity.user.detail.SaveInterestLocationDto
import com.party.data.entity.user.auth.SocialLoginErrorDto
import com.party.data.entity.user.auth.SocialLoginSuccessDto
import com.party.data.entity.user.auth.UserSignUpDto
import com.party.data.entity.user.detail.PersonalityListDto
import com.party.data.entity.user.detail.PersonalitySaveDto
import com.party.data.entity.user.detail.PositionListDto
import com.party.data.entity.user.detail.SaveCarrierItem
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
}