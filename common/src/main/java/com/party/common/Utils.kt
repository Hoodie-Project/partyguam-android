package com.party.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseSyncProgressIndicator
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
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
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AnnotatedTextComponent(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    textColor: Color = Color.Black,
    textAlign: Alignment = Alignment.CenterStart,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = textAlign,
    ){
        Text(
            text = annotatedString,
            color = textColor,
            fontWeight = fontWeight,
            fontSize = fontSize,
            textDecoration = textDecoration,
        )
    }
}

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    text: String,
    textColor: Color = Color.Black,
    align: Alignment = Alignment.CenterStart,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLines: Int = Int.MAX_VALUE,
    letterSpacing: TextUnit = 0.sp,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = align,
    ){
        Text(
            text = text,
            color = textColor,
            textAlign = textAlign,
            fontWeight = fontWeight,
            fontSize = fontSize,
            textDecoration = textDecoration,
            overflow = overflow,
            maxLines = maxLines,
            letterSpacing = letterSpacing,
        )
    }
}

@Composable
fun WidthSpacer(
    widthDp: Dp,
) {
    Spacer(modifier = Modifier.width(widthDp))
}

@Composable
fun HeightSpacer(
    heightDp: Dp,
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightDp)
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

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

@Composable
fun ScreenExplainArea(
    mainExplain: String,
    subExplain: String,
) {
    HeightSpacer(heightDp = 32.dp)

    TextComponent(
        text = mainExplain,
        fontWeight = FontWeight.Bold,
        fontSize = T2,
    )

    HeightSpacer(heightDp = 12.dp)

    TextComponent(
        text = subExplain,
        fontSize = T3,
    )

    //HeightSpacer(heightDp = 40.dp)
}

@Composable
fun LoadingProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        BallPulseSyncProgressIndicator(
            modifier = Modifier,
            color = Color.Gray,
            animationDuration = 800,
            animationDelay = 200,
            startDelay = 0,
            ballDiameter = 12.dp,
            ballJumpHeight = 42.dp,
            ballCount = 4
        )
    }
}

fun makeAccessToken(context: Context, token: String): String {
    return "${context.getString(R.string.common5)} $token"
}

fun convertToText(recruitedCount: Int, recruitingCount: Int): String{
    return "$recruitedCount/$recruitingCount"
}

fun convertIsoToCustomDateFormat(isoString: String): String {
    // 입력 형식 (ISO 8601)
    val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())

    // 출력 형식 (yyyy.MM.dd)
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault())

    // ISO 문자열을 ZonedDateTime으로 변환 후, 원하는 형식으로 변환
    val zonedDateTime = ZonedDateTime.parse(isoString, isoFormatter)
    return outputFormatter.format(zonedDateTime)
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

fun convertToIntFromYear(year: String): Int{
    return when(year){
        "신입" -> 0
        "1년" -> 1
        "2년" -> 2
        "3년" -> 3
        "4년" -> 4
        "5년" -> 5
        "6년" -> 6
        "7년" -> 7
        "8년" -> 8
        "9년" -> 9
        "10년" -> 10
        else -> 0
    }
}

fun calculateLetterSpacing(fontSize: TextUnit, percentage: Float = -1f): TextUnit {
    return (fontSize.value * percentage / 100).sp
}