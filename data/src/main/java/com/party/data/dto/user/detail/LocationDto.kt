package com.party.data.dto.user.detail

import com.party.data.DataMapper
import com.party.domain.model.user.detail.Location
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val province: String,
    val city: String,
): DataMapper<Location>{
    override fun toDomain(): Location {
        return Location(
            id = id,
            province = province,
            city = city
        )
    }
}
