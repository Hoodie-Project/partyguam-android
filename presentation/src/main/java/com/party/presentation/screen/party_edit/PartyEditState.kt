package com.party.presentation.screen.party_edit

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

data class PartyEditState(
    val isLoadingPartyDetail: Boolean = false,
    val isVisibleToolTip: Boolean = true, // 툴팁

    val networkImage: String = "",
    val image: MultipartBody.Part = MultipartBody.Part.createFormData("image", "", byteArrayOf().toRequestBody()), // 이미지

    val partyStatus: String = "", // 파티 상태

    val inputPartyTitle: String = "", // 파티 제목

    val selectedPartyType: String = "", // 선택된 파티 타입 (미정, 스터디, 포트폴리오, 해커톤, 공모전)
    val isPartyTypeSheetOpen: Boolean = false, // 파티 타입 시트 오픈 여부

    val partyDescription: String = "", // 파티 설명
    val isHelpCardOpen: Boolean = false, // 도움말 카드

    val isPartyModifyLoading: Boolean = false, // 파티 수정 로딩

    val isShowPartyDeleteDialog: Boolean = false, // 파티 삭제 다이얼로그
)
