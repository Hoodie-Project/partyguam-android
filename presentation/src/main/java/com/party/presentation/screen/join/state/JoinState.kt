package com.party.presentation.screen.join.state

data class JoinState(
    val isShowCancelJoinDialog: Boolean = false,

    val email: String = "",
    val signupAccessToken: String = "",

    // 사용자가 입력한 닉네임
    val userNickName: String = "",

    // 닉네임 warning
    val warningMessage: String = "",

    // 생년월일
    val birthDay: String = "", // 2023-10-10

    // 성별
    val gender: String = "남자", // 남자, 여자
)
