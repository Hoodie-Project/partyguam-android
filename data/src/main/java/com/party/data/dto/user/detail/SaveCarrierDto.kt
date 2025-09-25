package com.party.data.dto.user.detail

import com.party.data.DataMapper
import com.party.domain.model.user.detail.SaveCarrier
import kotlinx.serialization.Serializable

@Serializable
data class SaveCarrierItemDto(
    val id: Int,
    val positionId: Int,
    val years: Int,
    val careerType: String,
): DataMapper<SaveCarrier>{
    override fun toDomain(): SaveCarrier {
        return SaveCarrier(
            id = id,
            positionId = positionId,
            years = years,
            careerType = careerType
        )
    }
}
