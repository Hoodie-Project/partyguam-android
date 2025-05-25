package com.party.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Constants {

    // DataStore
    const val ACCESS_TOKEN_PREFERENCES = "guam_preferences"
    const val ACCESS_TOKEN_KEY = "access_token"
    const val FIRST_LAUNCH_FLAG_KEY = "first_launch"
    const val FCM_TOKEN_KEY = "fcm_token"

    // Room
    const val KEYWORD_ENTITY = "keyword_entity"
    const val GUAM_DATABASE_NAME = "guam_database"
}

object DetailCarrierData {

    var mainSelectedCarrier by mutableStateOf("")
    var mainSelectedMainPosition by mutableStateOf("")
    var mainSelectedDetailPosition by mutableStateOf("")
    var mainSelectedDetailPositionId by mutableIntStateOf(0)

    var subSelectedCarrier by mutableStateOf("")
    var subSelectedMainPosition by mutableStateOf("")
    var subSelectedDetailPosition by mutableStateOf("")
    var subSelectedDetailPositionId by mutableIntStateOf(0)
}