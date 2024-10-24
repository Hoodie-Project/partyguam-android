package com.party.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseSyncProgressIndicator
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScaffoldTitle(
    title: String,
) {
    Text(
        text = title,
        fontSize = T2,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
    )
}

@Composable
fun AnnotatedTextComponent(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    textColor: Color = Color.Black,
    textAlign: Alignment = Alignment.CenterStart,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
) {
    Box(
        modifier = modifier,
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
    text: String,
    textColor: Color = Color.Black,
    textAlign: Alignment = Alignment.CenterStart,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = textAlign,
    ){
        Text(
            text = text,
            color = textColor,
            fontWeight = fontWeight,
            fontSize = fontSize,
            textDecoration = textDecoration,
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

    HeightSpacer(heightDp = 40.dp)
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

@Composable
fun NetworkImageLoad(
    modifier: Modifier = Modifier,
    url: String? = null,
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = "image",
        contentScale = ContentScale.Inside,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(fraction = 0.2f)
            )
        },
        error = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "이미지 로딩 실패!")

            }
            /*Image(
                painter = painterResource(id = R.drawable.login_onder_logo),
                contentDescription = "error",
                modifier = Modifier
            )*/
        },
        modifier = modifier
            //.fillMaxSize()
            .aspectRatio(1f)
    )
}