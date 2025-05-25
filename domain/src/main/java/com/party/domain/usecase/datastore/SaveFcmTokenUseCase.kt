package com.party.domain.usecase.datastore

import com.party.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveFcmTokenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(token: String){
        dataStoreRepository.saveFcmToken(token = token)
    }
}