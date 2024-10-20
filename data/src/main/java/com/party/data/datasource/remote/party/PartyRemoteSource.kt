package com.party.data.datasource.remote.party

import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.skydoves.sandwich.ApiResponse

interface PartyRemoteSource {

    // 홈화면 - 개인 맞춤 추천 모집공고 리스트
    suspend fun getPersonalizedParties(page: Int, size: Int, sort: String, order: String): ApiResponse<PersonalRecruitmentListEntity>
}