package com.party.data.mapper

import com.party.data.entity.user.LocationEntity
import com.party.data.entity.user.SaveInterestLocationEntity
import com.party.data.entity.user.auth.SocialLoginErrorEntity
import com.party.data.entity.user.auth.SocialLoginSuccessEntity
import com.party.data.entity.user.auth.UserSignUpEntity
import com.party.domain.model.user.detail.LocationResponse
import com.party.domain.model.user.SocialLoginErrorResponse
import com.party.domain.model.user.SocialLoginResponse
import com.party.domain.model.user.SocialLoginSuccessResponse
import com.party.domain.model.user.detail.SaveInterestLocationResponse
import com.party.domain.model.user.signup.UserSignUpResponse

object UserMapper {
    fun mapperToSocialLoginResponse(socialLoginEntity: SocialLoginSuccessEntity): SocialLoginResponse {
        return SocialLoginSuccessResponse(
            accessToken = socialLoginEntity.accessToken,
            refreshToken = socialLoginEntity.refreshToken
        )
    }

    fun mapperSocialLoginErrorResponse(socialLoginErrorEntity: SocialLoginErrorEntity): SocialLoginErrorResponse {
        return SocialLoginErrorResponse(
            message = socialLoginErrorEntity.message,
            signupAccessToken = socialLoginErrorEntity.signupAccessToken
        )
    }

    fun mapperUserSignUpResponse(userSignUpEntity: UserSignUpEntity): UserSignUpResponse {
        return UserSignUpResponse(
            accessToken = userSignUpEntity.accessToken
        )
    }

    fun mapperToLocationResponse(locationEntity: LocationEntity): LocationResponse {
        return LocationResponse(
            id = locationEntity.id,
            province = locationEntity.province,
            city = locationEntity.city,
        )
    }

    fun mapperToSaveInterestLocationResponse(saveInterestLocationEntity: SaveInterestLocationEntity): SaveInterestLocationResponse {
        return SaveInterestLocationResponse(
            id = saveInterestLocationEntity.id,
            userId = saveInterestLocationEntity.userId,
            locationId = saveInterestLocationEntity.locationId,
        )
    }
}