package com.party.domain.usecase.banner

import com.party.common.ServerApiResponse
import com.party.domain.model.banner.Banner
import com.party.domain.repository.BannerRepository
import javax.inject.Inject

class GetBannerListUseCase @Inject constructor(
    private val bannerRepository: BannerRepository,
) {
    suspend operator fun invoke(): ServerApiResponse<Banner>{
        return bannerRepository.getBannerList()
    }
}