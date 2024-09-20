package com.party.data.service

import com.party.common.BaseSuccessResponse
import com.party.data.entity.user.SocialLoginEntity
import com.party.domain.model.member.SocialLoginRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserService {

    @POST("api/users/kakao/app/login")
    suspend fun loginGoogle(@Body socialLoginRequest: SocialLoginRequest): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>

    @POST("api/users/kakao/app/login")
    suspend fun loginKakao(@Body socialLoginRequest: SocialLoginRequest): ApiResponse<BaseSuccessResponse<SocialLoginEntity>>
}