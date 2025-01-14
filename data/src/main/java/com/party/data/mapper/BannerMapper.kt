package com.party.data.mapper

import com.party.data.dto.banner.BannerDto
import com.party.data.dto.banner.BannerItemDto
import com.party.data.util.convertToImageUrl
import com.party.domain.model.banner.Banner
import com.party.domain.model.banner.BannerItem

object BannerMapper {
    fun mapperBanner(bannerDto: BannerDto): Banner{
        return Banner(
            total = bannerDto.total,
            banner = bannerDto.banner.map {
                mapperBannerItem(it)
            }
        )
    }

    private fun mapperBannerItem(bannerItemDto: BannerItemDto): BannerItem {
        return BannerItem(
            id = bannerItemDto.id,
            image = convertToImageUrl(bannerItemDto.image),
            title = bannerItemDto.title,
            status = bannerItemDto.status,
            createdAt = bannerItemDto.createdAt,
            updatedAt = bannerItemDto.updatedAt,

        )
    }
}