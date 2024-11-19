package com.party.domain.model.banner

data class Banner(
    val total: Int,
    val banner: List<BannerItem>
)

data class BannerItem(
    val id: Int,
    val title: String,
    val image: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
)