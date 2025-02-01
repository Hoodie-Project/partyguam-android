package com.party.data.service

import com.party.data.dto.banner.BannerDto
import com.party.data.dto.user.auth.SocialLoginSuccessDto
import com.party.data.dto.user.auth.UserSignUpDto
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface NoTokenService {

    @POST("api/users/google/app/login")
    suspend fun loginGoogle(
        @Body accessTokenRequest: AccessTokenRequest,
    ): ApiResponse<SocialLoginSuccessDto>

    @POST("api/users/kakao/app/login")
    suspend fun loginKakao(
        @Header("Authorization") accessToken: String
    ): ApiResponse<SocialLoginSuccessDto>

    // 유저 닉네임 중복체크
    @GET("api/users/check-nickname")
    suspend fun checkNickName(
        @Header("Authorization") signupAccessToken: String,
        @Query("nickname") nickname: String,
    ): ApiResponse<String>

    // 유저 회원가입
    @POST("api/users")
    suspend fun userSignUp(
        @Header("Authorization") signupAccessToken: String,
        @Body userSignUpRequest: UserSignUpRequest,
    ): ApiResponse<UserSignUpDto>

    // 홈 - 배너 리스트 조회
    @GET("api/banner/app")
    suspend fun getBannerList(): ApiResponse<BannerDto>
}