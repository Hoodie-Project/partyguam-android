package com.party.presentation.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.party.common.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val CHANNEL_ID = "party_guam_android_channel" //  채널 아이디를 디폴트 채널아이디와 동일하게 설정하면 해드업이 나타나지 않음. - 다른 채널아이디로 설정해야함.
const val CHANNEL_NAME = "Party Guam Notifications"
const val TITLE = "title"
const val BODY = "body"

class FirebaseService: FirebaseMessagingService() {

    override fun handleIntent(intent: Intent?) {
        Log.d("FirebaseService", "handleIntent() 호출됨 - 백그라운드 메시지 수신")

        intent?.extras?.let { extras ->
            // "gcm.notification.body"가 존재하는 경우만 수정
            if (extras.containsKey("gcm.notification.body")) {
                val mutableExtras = Bundle(extras).apply { remove("gcm.notification.body") }
                intent.replaceExtras(mutableExtras)
            }

            // 데이터가 있을 경우에만 처리
            if (extras.isEmpty) {
                Log.i("FirebaseService", "handleIntent() 종료 - 데이터 없음")
                return
            }

            val remoteMessage = RemoteMessage(intent.extras!!)
            onMessageReceived(remoteMessage)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // FCM 수신 데이터
        val data : MutableMap<String, String> = message.data
        if (data.containsKey("token")) {
            return
        }

        val title = data[TITLE] ?: ""
        val body = data[BODY] ?: ""

        Log.i("FirebaseService", title)
        Log.i("FirebaseService", body)

        // 비동기적으로 알림을 생성
        CoroutineScope(Dispatchers.IO).launch {
            sendNotification(title, body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("FCM Refresh Token", token)
    }

    private suspend fun sendNotification(
        title: String,
        body: String,
    ){

        // notificationBuilder 설정
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // 중요도 높게 설정
            .setDefaults(NotificationCompat.DEFAULT_ALL) // 기본 효과(소리, 진동 등) 추가
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // 잠금화면에서도 표시
            .setAutoCancel(true) // 알람 클릭 시 삭제
            //.setContentIntent(pendingIntent) // 알림 클릭 시 실행될 Intent 지정


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                enableVibration(true)
            }

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify((System.currentTimeMillis()).toInt(), notificationBuilder.build()) // id 값이 달라야 다른 알람으로 인식해서 여러개의 알람이 뜬다. (id 값이 같으면 덮어씌워진다.)
        }
    }
}