package com.party.presentation.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

fun initFcm(
    context: Context,
){
    FirebaseApp.initializeApp(context)
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.d("FCM Token 발급 실패", "${task.exception}")
            return@addOnCompleteListener
        }

        val fcmToken = task.result
        Log.i("FCM Token", fcmToken)
        /*CoroutineScope(Dispatchers.IO).launch {
            saveToLocalFcmTokenUseCase(fcmToken)
        }*/
    }
}