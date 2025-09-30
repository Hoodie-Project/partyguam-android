package com.party.data.util

import com.party.data.BuildConfig

fun convertToImageUrl(image: String?): String{
    return BuildConfig.IMAGE_URL + image
}

fun convertToPartyImage(image: String?): String{
    return if(image != null) BuildConfig.IMAGE_URL + image else BuildConfig.DEFAULT_IMAGE
}