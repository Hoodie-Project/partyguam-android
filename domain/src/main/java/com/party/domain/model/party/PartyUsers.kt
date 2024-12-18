package com.party.domain.model.party

data class PartyUsers(
    val partyAdmin: List<PartyAdmin>,
    val partyUser: List<PartyUser>
)

data class PartyAdmin(
    val id: Int,
    val authority: String,
    val position: Position,
    val user: User
)

data class PartyUser(
    val id: Int,
    val authority: String,
    val position: Position,
    val user: User
)

data class Position(
    val id: Int,
    val main: String,
    val sub: String,
)

data class User(
    //val id: Int,
    val nickname: String,
    val image: String?,
    val userCareers: List<UserCareer>
)

data class UserCareer(
    val positionId: Int,
    val years: Int,
)




data class PartyMember(
    val authority: String,
    val main: String,
    val sub: String,
    val nickName: String,
    val image: String,
    val userId: Int,
)

fun PartyAdmin.toPartyMember(): PartyMember {
    return PartyMember(
        authority = this.authority,
        main = this.position.main,
        sub = this.position.sub,
        nickName = this.user.nickname,
        image = this.user.image ?: "", // 이미지가 null일 경우 기본값 사용
        userId = this.id
    )
}

fun PartyUser.toPartyMember(): PartyMember {
    return PartyMember(
        authority = this.authority,
        main = this.position.main,
        sub = this.position.sub,
        nickName = this.user.nickname,
        image = this.user.image ?: "",
        userId = this.id
    )
}