package com.party.common.component.permission

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.NotificationManagerCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CheckAlarmPermission(
    context: Context,
    onGrant: () -> Unit,
    onDeny: () -> Unit,
    onDenied: () -> Unit,
) {
    val alarmPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    val requestLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            onGrant()
        } else {
            onDeny()
        }
    }

    LaunchedEffect(alarmPermissionState) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            // 안드로이드 13 미만은 권한 체크 필요 없음 → 자동 허용 처리
            val isNotificationAllowed = NotificationManagerCompat.from(context).areNotificationsEnabled()

            if (isNotificationAllowed) {
                onGrant()
            } else {
                onDeny()
            }
        } else {
            // 안드로이드 13 이상에서는 권한 체크
            if (!alarmPermissionState.status.isGranted) {
                if (alarmPermissionState.status.shouldShowRationale) {
                    // 이전에 거부한 경우
                    Log.d("Permission","이전에 권한을 거부하셨어요")
                    onDenied()
                } else {
                    // 권한을 요청
                    requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 이미 권한이 허용된 경우
                onGrant()
            }
        }
    }
}