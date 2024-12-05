package com.party.data.datasource.remote.banner

import com.party.data.dto.banner.BannerDto
import com.party.data.service.NoTokenService
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class BannerRemoteSourceImpl @Inject constructor(
    private val noTokenService: NoTokenService
): BannerRemoteSource {
    override suspend fun getBannerList(): ApiResponse<BannerDto> {
        return noTokenService.getBannerList()
    }
}