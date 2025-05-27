package com.party.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class CheckVersionDto(
    val platform: String,
    val latestVersion: String,
    val minRequiredVersion: String,
    val releaseNotes: String,
    val isForceUpdate: Boolean,
    val downloadUrl: String,
)