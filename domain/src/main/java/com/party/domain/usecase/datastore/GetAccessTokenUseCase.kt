package com.party.domain.usecase.datastore

import com.party.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    suspend operator fun invoke(): Flow<String> {
        return dataStoreRepository.getAccessToken()
    }
}