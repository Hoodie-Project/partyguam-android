package com.party.data.service

import com.party.data.entity.user.LocationEntity
import com.party.data.entity.user.SaveInterestLocationEntity
import com.party.data.entity.user.auth.SocialLoginSuccessEntity
import com.party.data.entity.user.auth.UserSignUpEntity
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
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
    ): ApiResponse<SocialLoginSuccessEntity>

    @POST("api/users/kakao/app/login")
    suspend fun loginKakao(
        @Header("Authorization") accessToken: String
    ): ApiResponse<SocialLoginSuccessEntity>

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
    ): ApiResponse<UserSignUpEntity>

    // 특정 지역의 지역 리스트 조회
    @GET("api/locations")
    suspend fun getLocations(
        @Header("Authorization") accessToken: String,
        @Query("province") province: String,
    ): ApiResponse<List<LocationEntity>>

    // 관심지역 저장
    @POST("api/users/me/locations")
    suspend fun saveInterestLocation(
        @Header("Authorization") accessToken: String,
        @Body locations: InterestLocationList,
    ): ApiResponse<List<SaveInterestLocationEntity>>
}