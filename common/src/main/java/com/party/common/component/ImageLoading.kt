package com.party.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.LARGE_CORNER_SIZE

@Composable
fun ImageLoading(
    modifier: Modifier,
    imageUrl: String? = null,
    borderColor: Color = GRAY100,
    borderWidth: Dp = 1.dp,
    roundedCornerShape: Dp = LARGE_CORNER_SIZE,
) {
    Card(
        modifier = modifier,
        border = BorderStroke(borderWidth, borderColor),
        shape = RoundedCornerShape(roundedCornerShape),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            NetworkImageLoad(
                modifier = Modifier
                    .fillMaxSize(),
                url = imageUrl,
            )
        }
    }
}

@Composable
fun NetworkImageLoad(
    modifier: Modifier = Modifier,
    url: String? = null,
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = "image",
        contentScale = ContentScale.FillBounds,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
            )
        },
        error = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "이미지 로딩 실패!")
            }
        },
        modifier = modifier
            .fillMaxSize()
    )
}