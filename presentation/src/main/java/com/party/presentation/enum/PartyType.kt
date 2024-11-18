package com.party.presentation.enum

enum class PartyType(
    val type: String,
    val id: Int,
){
    // 미정
    UNDEFINED("미정", 1),
    STUDY("스터디", 2),
    PORTFOLIO("포트폴리오", 3),
    HACKATHON("해커톤", 4),
    COMPETITION("공모전", 5),
}