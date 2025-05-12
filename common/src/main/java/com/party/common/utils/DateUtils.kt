package com.party.common.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun convertIsoToCustomDateFormat(isoString: String): String {
    // 입력 형식 (ISO 8601)
    val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())

    // 출력 형식 (yyyy.MM.dd)
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault())

    // ISO 문자열을 ZonedDateTime으로 변환 후, 원하는 형식으로 변환
    val zonedDateTime = ZonedDateTime.parse(isoString, isoFormatter)
    return outputFormatter.format(zonedDateTime)
}