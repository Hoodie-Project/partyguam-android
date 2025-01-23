package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class LinkKakaoRequest(
    val oauthAccessToken: String,
)
