package com.party.data.service

import com.party.data.dto.user.detail.LocationDto
import com.party.data.dto.user.detail.SaveInterestLocationDto
import com.party.data.dto.user.detail.PersonalityListDto
import com.party.data.dto.user.detail.PersonalitySaveDto
import com.party.data.dto.user.detail.PositionListDto
import com.party.data.dto.user.detail.SaveCarrierDto
import com.party.data.dto.user.party.MyPartyDto
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.SaveCarrierList
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    // 특정 지역의 지역 리스트 조회
    @GET("api/locations")
    suspend fun getLocations(
        @Query("province") province: String,
    ): ApiResponse<List<LocationDto>>

    // 관심지역 저장
    @POST("api/users/me/locations")
    suspend fun saveInterestLocation(
        @Body locations: InterestLocationList,
    ): ApiResponse<List<SaveInterestLocationDto>>

    // 특정 직군의 포지션 리스트 조회
    @GET("api/positions")
    suspend fun getPositions(
        @Query("main") main: String,
    ): ApiResponse<List<PositionListDto>>

    // 유저 경력 저장
    @POST("api/users/me/careers")
    suspend fun saveCareer(
        @Body career: SaveCarrierList,
    ): ApiResponse<SaveCarrierDto>

    // 성향 질문 리스트 전체 조회
    @GET("api/personalities")
    suspend fun getPersonalities(): ApiResponse<List<PersonalityListDto>>

    // 성향 질문 리스트 전체 저장
    @POST("api/users/me/personalities")
    suspend fun savePersonalities(
        @Body personalitySaveRequest: PersonalitySaveRequest,
    ): ApiResponse<List<PersonalitySaveDto>>

    // 내 파티 리스트 조회
    @GET("api/users/me/parties")
    suspend fun getMyParties(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("order") order: String,
    ): ApiResponse<MyPartyDto>
}