package com.party.data.dto.user.detail

import com.party.data.DataMapper
import com.party.domain.model.user.detail.PositionList
import kotlinx.serialization.Serializable

@Serializable
data class PositionListDto(
    val id: Int,
    val main: String,
    val sub: String,
): DataMapper<PositionList>{
    override fun toDomain(): PositionList {
        return PositionList(
            id = id,
            main = main,
            sub = sub
        )
    }
}
