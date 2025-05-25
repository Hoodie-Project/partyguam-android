package com.party.domain.usecase.datastore

import com.party.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
){
    operator fun invoke() = dataStoreRepository.getFcmToken()
}