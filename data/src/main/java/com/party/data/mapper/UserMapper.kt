package com.party.data.mapper

import com.party.data.entity.user.SocialLoginErrorEntity
import com.party.data.entity.user.SocialLoginSuccessEntity
import com.party.data.entity.user.UserSignUpEntity
import com.party.domain.model.user.SocialLoginErrorResponse
import com.party.domain.model.user.SocialLoginResponse
import com.party.domain.model.user.SocialLoginSuccessResponse
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
}