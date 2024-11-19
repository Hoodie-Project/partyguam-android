package com.party.data.repository

import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.ServerApiResponse.ExceptionResponse
import com.party.data.datasource.remote.banner.BannerRemoteSource
import com.party.data.mapper.BannerMapper.mapperBanner
import com.party.domain.model.banner.Banner
import com.party.domain.repository.BannerRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val bannerRemoteSource: BannerRemoteSource,
): BannerRepository{
    override suspend fun getBannerList(): ServerApiResponse<Banner> {
        return when(val result = bannerRemoteSource.getBannerList()){
            is ApiResponse.Success -> {
                SuccessResponse(data = mapperBanner(result.data))
            }
            is ApiResponse.Failure.Error -> {
                ErrorResponse()
            }

            is ApiResponse.Failure.Exception -> {
                ExceptionResponse(
                    result.message,
                )
            }
        }
    }
}