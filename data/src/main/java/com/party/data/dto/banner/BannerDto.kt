package com.party.data.dto.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerDto(
    val total: Int,
    val banner: List<BannerItemDto>
)

@Serializable
data class BannerItemDto(
    val id: Int,
    val title: String,
    val image: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
)