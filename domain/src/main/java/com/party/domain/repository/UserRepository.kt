package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.user.CheckNickNameResponse
import com.party.domain.model.user.SocialLoginResponse
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.model.user.signup.UserSignUpResponse

interface UserRepository {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ServerApiResponse<SocialLoginResponse>

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ServerApiResponse<SocialLoginResponse>

    // 유저 닉네임 중복체크
    suspend fun checkNickName(signupAccessToken: String, nickname: String): ServerApiResponse<String>

    // 유저 회원가입
    suspend fun userSignUp(signupAccessToken: String, userSignUpRequest: UserSignUpRequest): ServerApiResponse<UserSignUpResponse>

}