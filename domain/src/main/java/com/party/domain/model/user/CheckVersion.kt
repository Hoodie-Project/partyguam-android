package com.party.domain.model.user

data class CheckVersion(
    val platform: String = "",
    val latestVersion: String = "",
    val minRequiredVersion: String = "",
    val releaseNotes: String = "",
    val isForceUpdate: Boolean = false,
    val downloadUrl: String = "",
)