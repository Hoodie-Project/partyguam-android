package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.banner.Banner

interface BannerRepository {

    // 배너 리스트 조회
    suspend fun getBannerList(): ServerApiResponse<Banner>
}