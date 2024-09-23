package com.party.data.mapper

import com.party.data.entity.user.SocialLoginEntity
import com.party.data.entity.user.SocialLoginErrorEntity
import com.party.domain.model.member.SocialLoginErrorResponse
import com.party.domain.model.member.SocialLoginResponse

object UserMapper {
    fun mapperToSocialLoginResponse(socialLoginEntity: SocialLoginEntity): SocialLoginResponse {
        return SocialLoginResponse(
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
}