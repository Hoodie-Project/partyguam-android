package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyUsersDto(
    val partyAdmin: List<PartyAdminDto>,
    val partyUser: List<PartyUserDto> = emptyList()
)

@Serializable
data class PartyAdminDto(
    val id: Int,
    val authority: String,
    val position: PositionDto,
    val user: UserDto
)

@Serializable
data class PartyUserDto(
    val id: Int,
    val authority: String,
    val position: PositionDto,
    val user: UserDto
)

@Serializable
data class PositionDto(
    val id: Int,
    val main: String,
    val sub: String,
)

@Serializable
data class UserDto(
    //val id: Int,
    val nickname: String,
    val image: String?,
    val userCareers: List<UserCareerDto> = emptyList()
)

@Serializable
data class UserCareerDto(
    val positionId: Int,
    val years: Int,
)