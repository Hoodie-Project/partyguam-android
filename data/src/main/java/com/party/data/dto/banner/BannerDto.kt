package com.party.data.dto.banner

import kotlinx.serialization.Serializable

@Serializable
data class BannerDto(
    val total: Int,
    val banner: List<BannerItemDto>
)

@Serializable
data class BannerItemDto(
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val id: Int,
    val platform: String,
    val title: String,
    val image: String,
    val link: String,
)