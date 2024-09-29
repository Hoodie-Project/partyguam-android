package com.party.data.service

import com.party.common.ServerApiResponse.SuccessResponse
import com.party.data.entity.user.CheckNickNameEntity
import com.party.data.entity.user.SocialLoginEntity
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface UserService {

    @POST("api/users/google/app/login")
    suspend fun loginGoogle(
        @Header("Authorization") accessToken: String
    ): ApiResponse<SuccessResponse<SocialLoginEntity>>

    @POST("api/users/kakao/app/login")
    suspend fun loginKakao(
        @Header("Authorization") accessToken: String
    ): ApiResponse<SuccessResponse<SocialLoginEntity>>

    // 유저 닉네임 중복체크
    @GET("api/users/check-nickname")
    suspend fun checkNickName(
        @Header("signupAccessToken") signupAccessToken: String,
        @Query("nickname") nickname: String
    ): ApiResponse<SuccessResponse<CheckNickNameEntity>>
}