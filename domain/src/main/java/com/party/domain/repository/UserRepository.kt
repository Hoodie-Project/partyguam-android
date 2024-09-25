package com.party.domain.repository

import com.party.common.ServerApiResponse

interface UserRepository {

    // 구글 로그인
    suspend fun googleLogin(accessToken: String): ServerApiResponse

    // 카카오 로그인
    suspend fun kakaoLogin(accessToken: String): ServerApiResponse

}