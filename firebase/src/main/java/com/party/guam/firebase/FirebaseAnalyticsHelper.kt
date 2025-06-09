package com.party.guam.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object FirebaseAnalyticsHelper {
    private var firebaseAnalytics: FirebaseAnalytics? = null

    fun init(){
        firebaseAnalytics = Firebase.analytics
    }

    fun logEvent(name: String) {
        val bundle = Bundle().apply {
            putString(name, SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date()))
        }
        firebaseAnalytics?.logEvent(name, bundle)
    }
}