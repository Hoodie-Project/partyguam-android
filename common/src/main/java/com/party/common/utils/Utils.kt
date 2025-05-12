package com.party.common.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.party.common.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun snackBarMessage(
    snackBarHostState: SnackbarHostState,
    message: String,
    durationTime: Long = 2000L
) {
    CoroutineScope(Dispatchers.Main).launch {
        val job = launch {
            snackBarHostState.showSnackbar(message)
        }
        delay(durationTime)
        job.cancel()
    }
}

fun makeAccessToken(context: Context, token: String): String {
    return "${context.getString(R.string.common5)} $token"
}

fun convertToText(recruitedCount: Int, recruitingCount: Int): String{
    return "$recruitedCount/$recruitingCount"
}

fun uriToFile(context: Context, uri: Uri): File {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri)
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg" // 기본값으로 jpg
    val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}.$extension")

    contentResolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return file
}

fun createMultipartBody(context: Context, uri: Uri): MultipartBody.Part {
    val file = uriToFile(context, uri)
    val mimeType = getMimeType(file) ?: throw IllegalArgumentException("Unsupported file type")
    val requestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestBody)
}

fun getMimeType(file: File): String? {
    val extension = file.extension.lowercase()
    return when (extension) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        else -> null // 허용되지 않는 타입의 경우 null 처리
    }
}

// 나이를 계산하는 함수
fun calculateAgeSafely(birthDateString: String): Int {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(birthDateString, formatter)
        val currentDate = LocalDate.now()
        Period.between(birthDate, currentDate).years
    } catch (e: Exception) {
        println("Invalid date format: $birthDateString")
        0
    }
}

fun calculateLetterSpacing(fontSize: TextUnit, percentage: Float = -1f): TextUnit {
    return (fontSize.value * percentage / 100).sp
}