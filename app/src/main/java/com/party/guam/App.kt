package com.party.guam

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "7a597dbc1eb7490bc1305519dbda6b9b")
    }
}