package com.party.data.service

import com.party.common.ServerApiResponse.BaseSuccessResponse
import com.party.data.entity.user.SocialLoginEntity
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserService {

    @POST("api/users/google/app/login")
    suspend fun loginGoogle(
        @Header("Authorization") accessToken: String
    ): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>

    @POST("api/users/kakao/app/login")
    suspend fun loginKakao(
        @Header("Authorization") accessToken: String
    ): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>
}