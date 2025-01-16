package com.party.domain.model.banner

data class Banner(
    val total: Int,
    val banner: List<BannerItem>
)

data class BannerItem(
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val id: Int,
    val platform: String,
    val title: String,
    val image: String,
    val link: String,
)