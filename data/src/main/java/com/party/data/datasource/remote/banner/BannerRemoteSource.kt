package com.party.data.datasource.remote.banner

import com.party.data.dto.banner.BannerDto
import com.skydoves.sandwich.ApiResponse

interface BannerRemoteSource {

    // 배너 리스트 조회
    suspend fun getBannerList(): ApiResponse<BannerDto>
}