package com.party.guam

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.party.guam.firebase.FirebaseAnalyticsHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        val hashKey = Utility.getKeyHash(this)
        println("hashKey $hashKey")

        FirebaseAnalyticsHelper.init()
    }
}