package com.party.data.dto.user.detail

import com.party.data.DataMapper
import com.party.domain.model.user.detail.SaveInterestLocation
import kotlinx.serialization.Serializable

@Serializable
data class SaveInterestLocationDto(
    val id: Int,
    val userId: Int,
    val locationId: Int,
): DataMapper<SaveInterestLocation>{
    override fun toDomain(): SaveInterestLocation {
        return SaveInterestLocation(
            id = id,
            userId = userId,
            locationId = locationId
        )
    }
}
