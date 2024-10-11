package com.party.domain.usecase.user.detail

import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.repository.UserRepository
import javax.inject.Inject

class SaveCarrierUseCase @Inject constructor(
    private val userRepository: UserRepository,
){
    suspend operator fun invoke(accessToken: String, career: SaveCarrierList) = userRepository.saveCarrier(accessToken = accessToken, career = career)
}