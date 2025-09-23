package com.party.data.dto.user.auth

import com.party.data.DataMapper
import com.party.domain.model.user.signup.UserSignUp
import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpDto(
    val accessToken: String,
): DataMapper<UserSignUp>{
    override fun toDomain(): UserSignUp {
        return UserSignUp(
            accessToken = accessToken
        )
    }
}
