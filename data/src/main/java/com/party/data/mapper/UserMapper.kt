package com.party.data.mapper

import com.party.data.entity.user.SocialLoginEntity
import com.party.domain.model.member.SocialLoginResponse

object UserMapper {
    fun mapperToSocialLoginResponse(socialLoginEntity: SocialLoginEntity): SocialLoginResponse {
        return SocialLoginResponse(
            accessToken = socialLoginEntity.accessToken,
        )
    }
}