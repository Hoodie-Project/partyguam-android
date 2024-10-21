package com.party.data.datasource.remote.party

import com.party.data.entity.party.PersonalRecruitmentListEntity
import com.party.data.entity.party.RecruitmentListEntity
import com.skydoves.sandwich.ApiResponse

interface PartyRemoteSource {

    // 홈화면 - 개인 맞춤 추천 모집공고 리스트
    suspend fun getPersonalizedParties(page: Int, size: Int, sort: String, order: String): ApiResponse<PersonalRecruitmentListEntity>

    // 홈화면 - 모집공고 리스트 조회
    suspend fun getRecruitmentList(page: Int, size: Int, sort: String, order: String): ApiResponse<RecruitmentListEntity>
}