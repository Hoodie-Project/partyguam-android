package com.party.data.util

import com.party.data.BuildConfig

fun convertToImageUrl(image: String?): String{
    return BuildConfig.IMAGE_URL + image
}