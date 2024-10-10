package com.party.domain.usecase.user.detail

import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.LocationResponse
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(accessToken: String, province: String): ServerApiResponse<List<LocationResponse>> {
        return userRepository.getLocations(accessToken = accessToken, province = province)
    }
}